package com.lighthawkwings.sound.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import com.lighthawkwings.Manager;

/**
 * @author eduardo.machado
 *
 */
public interface MidiSoundManager extends Manager {
	public static final int LOOP_CONTINUOUSLY = Sequencer.LOOP_CONTINUOUSLY;

	/**
	 * @param music
	 * @throws InvalidMidiDataException
	 */
	public void playSequence(Sequence music) throws InvalidMidiDataException;

	/**
	 * @param music
	 * @param count
	 * @throws InvalidMidiDataException
	 */
	public void playSequence(Sequence music, int count) throws InvalidMidiDataException;

	/**
	 * 
	 */
	public void stopSequence();

	/**
	 * @return
	 */
	public boolean isSequencePlaying();

	/**
	 * 
	 */
	public void resumeSequence();
}
