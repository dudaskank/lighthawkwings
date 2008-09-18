package com.lighthawkwings.sound.sampled;

import com.lighthawkwings.Manager;


public interface SampledSoundManager extends Manager {

	/**
	 * Play this Streamed. Return a PlayingStreamed object that represent the
	 * noise being played. The noise starts almost immediatelly, as long as
	 * there are available threads in the pool.
	 * 
	 * @param streamed The noise to play.
	 * @return A PlayingStreamed that represents the noise being played.
	 * @throws IllegalArgumentException If the format of the given noise does
	 *             not match the noise manager supported format.
	 */
	public abstract PlayingStreamed play(Streamed streamed);

	/**
	 * Play this Streamed. Return a PlayingStreamed object that represent the
	 * noise being played.
	 * 
	 * @param streamed The noise to play.
	 * @param loop If true, automatically adds a loop filter to the stream and
	 *            so, play the sound endlessly.
	 * @return A PlayingStreamed that represents the noise being played.
	 * @throws IllegalArgumentException If the format of the given noise does
	 *             not match the noise manager supported format.
	 */
	public abstract PlayingStreamed play(Streamed streamed, boolean loop);

}