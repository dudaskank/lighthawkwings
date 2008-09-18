package com.lighthawkwings.sound.sampled;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.apache.log4j.Logger;

import com.lighthawkwings.event.ManagerEvent;
import com.lighthawkwings.event.ManagerListener;
import com.lighthawkwings.sound.sampled.events.PlayingStreamedEvent;
import com.lighthawkwings.sound.sampled.events.PlayingStreamedListener;

/**
 * Represents a playing Streamed. The playing noise can be paused or stopped. Once stopped, it cannot be
 * reactivated again.
 *
 * @author Vinícius
 */
public class PlayingStreamed implements Runnable, ManagerListener {
	private volatile boolean isPaused = false;

	private volatile boolean isFinished = false;

	private Streamed stream;

	private Collection<PlayingStreamedListener> listeners;

	Logger logger = Logger.getLogger(getClass());

	/**
	 * Create a new playing noise. The noise will only be played when the run() method is called. Normally, it's
	 * thread pool responsibility to play the noise.
	 *
	 * @param noise
	 *            The noise that will play.
	 */
	PlayingStreamed(Streamed stream) {
		isPaused = false;
		isFinished = false;
		this.stream = stream;
		listeners = new ArrayList<PlayingStreamedListener>();
	}

	/**
	 * Pauses the noise, so it can be resumed later.
	 *
	 * @param pause
	 *            True to pause the noise, false to resume.
	 */
	public void setPaused(boolean pause) {
		isPaused = pause;
	}

	/**
	 * Stop the noise. The noise may take a while to stop.
	 */
	public void stop() {
		isFinished = true;
		/* gera o evento para os listeners */
		PlayingStreamedEvent event = new PlayingStreamedEvent(this);
		for (PlayingStreamedListener listener : listeners) {
			listener.stopPlaying(event);
		}
	}

	/**
	 * Indicate if this noise is in paused state.
	 *
	 * @return True if the noise is paused.
	 */
	public boolean isPaused() {
		return isPaused;
	}

	/**
	 * Indicate if this playsound already stopped. Finished PlayingSounds cannot be activated, so they should be
	 * discarded.
	 *
	 * @return True if the noise is stopped, false if not.
	 */
	public boolean isFinished() {
		return isFinished;
	}

	/**
	 * Create a new dataline with the given buffer size. The format that will be used is the same as the noise
	 * encapsulated by this class.
	 *
	 * @param bufferSize
	 *            Size of the buffer.
	 * @return The new source data line.
	 * @throws LineUnavailableException
	 *             If there's no more lines available for this noise.
	 */
	private SourceDataLine createDataLine(int bufferSize) throws LineUnavailableException {
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, stream.getFormat());
		SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
		line.open(stream.getFormat(), bufferSize);
		return line;
	}

	/**
	 * Create a new buffer. The buffer size will vary according to the noise frame size and rate.
	 *
	 * @return The noise buffer.
	 */
	private byte[] createStreamedBuffer() {
		int bufferSize = stream.getFormat().getFrameSize() * Math.round(stream.getFormat().getSampleRate() / 10);

		return new byte[bufferSize];
	}

	/**
	 * Plays the noise.
	 */
	public void run() {
		if (isFinished)
			throw new IllegalStateException("Sound already played.");

		SourceDataLine line = null;
		InputStream input = null;

		try {
			byte[] buffer = createStreamedBuffer();
			line = createDataLine(buffer.length);
			input = stream.newInputStream();

			line.start();
			int numBytesRead = 0;

			/* gera o evento para os listeners */
			PlayingStreamedEvent event = new PlayingStreamedEvent(this);
			for (PlayingStreamedListener listener : listeners) {
				listener.startPlaying(event);
			}

			while (numBytesRead != -1 && !isFinished()) {
				if (isPaused()) {
					Thread.yield();
					continue;
				}

				numBytesRead = input.read(buffer, 0, buffer.length);
				if (numBytesRead != -1)
					line.write(buffer, 0, numBytesRead);
			}
		} catch (Exception e) {
			logger.error("Error", e);
			// Stops playing immediatelly
		} finally {
			stop();
			if (line != null) {
				line.drain();
				line.close();
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("IO Error", e);
				}
			}
		}
	}

	/**
	 * Return the Streamed that is being played by this PlayingStreamed.
	 *
	 * @return The Streamed that is being played.
	 */
	public Streamed getStream() {
		return stream;
	}

	public void addPlayingStreamedListener(PlayingStreamedListener listener) {
		listeners.add(listener);
	}

	public void removePlayingStreamedListener(PlayingStreamedListener listener) {
		listeners.remove(listener);
	}

	public void managerFinish(ManagerEvent event) {
		if (event.getSource() instanceof SampledSoundManager) {
			stop();
		}
	}

	public void managerInit(ManagerEvent event) {
	}
}
