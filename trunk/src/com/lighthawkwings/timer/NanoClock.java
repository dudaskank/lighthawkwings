package com.lighthawkwings.timer;

import java.text.DateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * <p>
 * Class implementing the game clock routines.
 * </p>
 *
 * <p>
 * This implementation uses <code>System.nanoTime</code>.
 * </p>
 *
 * @author Eduardo Oliveira
 */
public class NanoClock implements Clock {
	long currentTime;
	long nextTickMs;
	long tickCount;
	long delay;
	boolean running;

	// for debug
	long start[] = new long[2], stop[] = new long[2];
	protected Logger logger = Logger.getLogger(getClass());

	/**
	 * Create a new clock.
	 *
	 * @param fps
	 *            Desired number of frames per second
	 */
	public NanoClock(float fps) {
		delay = (long) (1000 / fps);
		tickCount = 0;
		running = false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.timer.Clock#getTickCount()
	 */
	public synchronized long getTickCount() {
		return tickCount;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.timer.Clock#getCurrentTime()
	 */
	public synchronized long getCurrentTime() {
		return currentTime;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.timer.Clock#start()
	 */
	public synchronized void start() {
		DateFormat df = DateFormat.getTimeInstance(DateFormat.LONG);
		logger.debug(df.format(new Date()));
		start[0] = System.currentTimeMillis();
		start[1] = getNow();
		currentTime = getNow();
		nextTickMs = currentTime + delay;
		running = true;
		logger.debug("Starting clock.");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.timer.Clock#stop()
	 */
	public synchronized void stop() {
		running = false;
		DateFormat df = DateFormat.getTimeInstance(DateFormat.LONG);
		logger.debug(df.format(new Date()));
		stop[0] = System.currentTimeMillis();
		stop[1] = getNow();
		logger.debug("Stopping clock.");
		for (int i = 0; i < start.length; i++) {
			logger.debug("Diff[" + i + "]: " + (stop[i] - start[i]));
		}
		logger.debug("tickCount * delay = " + (tickCount * delay));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.timer.Clock#update()
	 */
	public synchronized void update() {
		if (running) {
			long now;
			now = getNow();
			if (now >= nextTickMs) {
				while (currentTime < nextTickMs) {
					currentTime += delay;
					tickCount++;
				}
				while (nextTickMs <= now) {
					nextTickMs += delay;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.timer.Clock#updateAndSleep()
	 */
	public synchronized void updateAndSleep() {
		update();
		if (running) {
			long now;
			now = getNow();
			if (now < nextTickMs) {
				try {
					Thread.sleep(nextTickMs - now);
				} catch (Exception e) {
					logger.error("Error in Clock.update(): ", e);
				}
			}
		}
	}

	private long getNow() {
		return System.nanoTime() / 1000000L;
		// return System.currentTimeMillis();
	}
}