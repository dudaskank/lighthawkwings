package com.lighthawkwings.timer;

/**
 * <p>
 * Interface for the clock.
 * </p>
 *
 * @author Eduardo Oliveira
 */
public interface Clock {
	/**
	 * @return Tick count from the clock.
	 */
	public long getTickCount();

	/**
	 * @return Current time in the game clock, in ms.
	 */
	public long getCurrentTime();

	/**
	 * Initialize the internal clock.
	 */
	public void start();

	/**
	 * Stop the internal clock.
	 */
	public void stop();

	/**
	 * Update the clock.
	 */
	public void update();

	/**
	 * Update the clock and make the thread sleep for the next iteration.
	 */
	public void updateAndSleep();
}