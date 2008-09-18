package com.lighthawkwings.sound.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import com.lighthawkwings.AbstractManager;

/**
 * @author eduardo.machado
 *
 */
public class DefaultMidiSoundManager extends AbstractManager implements MidiSoundManager {
	protected Sequencer sequencer;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.AbstractManager#finish()
	 */
	public void finish() {
		super.finish();
		if (sequencer != null) {
			if (sequencer.isOpen()) {
				stopSequence();
			}
			sequencer.close();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.AbstractManager#init()
	 */
	public void init() {
		try {
			super.init();
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
		} catch (MidiUnavailableException e) {
			logger.error("Error initializing", e);
			e.printStackTrace();
			sequencer = null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.sound.midi.MidiSoundManager#playSequence(javax.sound.midi.Sequence)
	 */
	public void playSequence(Sequence music) throws InvalidMidiDataException {
		playSequence(music, 1);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.sound.midi.MidiSoundManager#playSequence(javax.sound.midi.Sequence, int)
	 */
	public void playSequence(Sequence music, int count) throws InvalidMidiDataException {
		if (sequencer != null && music != null) {
			sequencer.setLoopCount(count);
			sequencer.setSequence(music);
			sequencer.start();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.sound.midi.MidiSoundManager#resumeSequence()
	 */
	public void resumeSequence() {
		if (sequencer != null) {
			sequencer.start();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.sound.midi.MidiSoundManager#stopSequence()
	 */
	public void stopSequence() {
		if (sequencer != null) {
			sequencer.stop();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.sound.midi.MidiSoundManager#isSequencePlaying()
	 */
	public boolean isSequencePlaying() {
		if (sequencer != null) {
			return sequencer.isRunning();
		} else {
			return false;
		}
	}
}