package com.lighthawkwings.sound.sampled;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import jgf.sound.filter.LoopFilter;

import com.lighthawkwings.AbstractManager;

/**
 * Manages the game sounds. <br>
 * TODO Adicionar um mecanismo de listeners para os sons.<br>
 * TODO Adicionar um tempo máximo para que o som seja tocado.
 *
 * @author Vinícius
 */
public class DefaultSampledSoundManager extends AbstractManager implements SampledSoundManager {

	private ExecutorService soundPool;

	/**
	 * Create a new SoundManager
	 */
	public DefaultSampledSoundManager() {
		// this.soundPool = createThreadPool(new SoundManagerThreadFactory());
	}

	/**
	 * Retrieve the ExecutorService more adequate for this system using the given AudioFormat.
	 *
	 * @return The more adequate ExecutorService.
	 */
	private ExecutorService createThreadPool(ThreadFactory threadFactory, AudioFormat format) {
		int max = getMaxSimultaneousSounds(format);

		if (max == AudioSystem.NOT_SPECIFIED)
			return Executors.newCachedThreadPool(threadFactory);

		if (max == 1)
			return Executors.newSingleThreadExecutor(threadFactory);

		return Executors.newFixedThreadPool(max, threadFactory);
	}

	/**
	 * Return the maximum number of simultaneous sounds in this format supported by this audio format in the
	 * default mixer.
	 *
	 * @return The maximum number of simultaneous sounds supported, or AudioSystem.NOT_SPECIFIED if there's no
	 *         known upper limit.
	 */
	private int getMaxSimultaneousSounds(AudioFormat format) {
		return AudioSystem.getMixer(null).getMaxLines(new DataLine.Info(SourceDataLine.class, format));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.sound.sampled.SampledSoundManager#play(com.lighthawkwings.sound.sampled.Streamed)
	 */
	public PlayingStreamed play(Streamed streamed) {
		return play(streamed, false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.sound.sampled.SampledSoundManager#play(com.lighthawkwings.sound.sampled.Streamed,
	 *      boolean)
	 */
	public PlayingStreamed play(Streamed streamed, boolean loop) {
		if (streamed != null) {
			if (soundPool == null) {
				this.soundPool = createThreadPool(new SoundManagerThreadFactory(), streamed.getFormat());
			}
			if (soundPool.isShutdown())
				throw new IllegalStateException("Manager already closed!");

			PlayingStreamed playing;

			playing = new PlayingStreamed(loop ? new LoopFilter(streamed) : streamed);

			soundPool.execute(playing);
			addManagerListener(playing);
			return playing;
		} else {
			return null;
		}
	}

	/**
	 * Finish this manager. The manager will interrupt all sound threads and
	 * will no longer accept any more sounds.
	 */
	@Override
	public void finish() {
		if (soundPool != null) {
			soundPool.shutdownNow();
		}
		super.finish();
	}

	/**
	 * Creates sound threads for the manager.
	 *
	 * @author Vinícius
	 */
	private static class SoundManagerThreadFactory implements ThreadFactory {
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setName("Sound Thread - " + Long.toString(t.getId()));
			t.setDaemon(true);
			return t;
		}
	}
}
