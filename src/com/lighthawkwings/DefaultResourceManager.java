/*
 * Created on Mar 31, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.lighthawkwings;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.lighthawkwings.sound.sampled.Sound;

/**
 * <p>
 * Implementação padrão do gerenciador de recursos.
 * </p>
 *
 * @author Eduardo Oliveira
 */
public class DefaultResourceManager extends AbstractManager implements ResourceManager {
	protected Game game;

	protected MediaTracker mt;

	protected Map<String, Object> resources;

	public DefaultResourceManager(Game game) {
		this.game = game;
		this.resources = new HashMap<String, Object>();
		this.mt = new MediaTracker(game.getScreenManager().getScreen());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.ResourceManager#loadImage(java.lang.String)
	 */
	public Image loadImage(String fileName) {
		Image image;
		if (resources.containsKey(fileName)) {
			try {
				image = (Image) resources.get(fileName);
			} catch (Exception e) {
				logger.error("Error getting image", e);
				image = null;
			}
		} else {
			image = Toolkit.getDefaultToolkit().createImage(getClass().getResource(fileName));
			mt.addImage(image, 0);
			try {
				mt.waitForAll();
			} catch (InterruptedException e) {
				logger.error("Error loading image", e);
			}
			if (mt.isErrorAny()) {
				mt.removeImage(image);
				image = null;
			} else {
				mt.removeImage(image);
			}
			resources.put(fileName, image);
		}
		return image;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.ResourceManager#unloadResource(java.lang.Object)
	 */
	public void unloadResource(Object name) {
		resources.remove(name);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.ResourceManager#unloadAllResources()
	 */
	public void unloadAllResources() {
		resources.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.ResourceManager#loadSequence(java.lang.String)
	 */
	public Sequence loadSequence(String fileName) {
		Sequence sequence = null;
		if (game.getMidiSoundManager() != null) {
			if (resources.containsKey(fileName)) {
				try {
					sequence = (Sequence) resources.get(fileName);
				} catch (Exception e) {
					logger.error("Error getting sequence", e);
					sequence = null;
				}
			} else {
				try {
					URL url = getClass().getResource(fileName);
					if (url != null) {
						sequence = MidiSystem.getSequence(url);
					}
				} catch (InvalidMidiDataException e) {
					logger.error("Error loading sequence", e);
				} catch (IOException e) {
					logger.error("Error loading sequence", e);
				}
				resources.put(fileName, sequence);
			}
		}
		return sequence;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.ResourceManager#loadSample(java.lang.String)
	 */
	public Sound loadSound(String fileName) {
		Sound sound = null;
		if (game.getSampledSoundManager() != null) {
			if (resources.containsKey(fileName)) {
				try {
					sound = (Sound) resources.get(fileName);
				} catch (Exception e) {
					logger.error("Error getting sound", e);
				}
			} else {
				try {
					AudioInputStream stream = AudioSystem.getAudioInputStream(getClass().getResource(fileName));
					sound = new Sound(stream);
				} catch (UnsupportedAudioFileException e) {
					logger.error("Error loading sound", e);
				} catch (IOException e) {
					logger.error("Error loading sound", e);
				}
				resources.put(fileName, sound);
			}
		}
		return sound;
	}

	@Override
	public void finish() {
		unloadAllResources();
		super.finish();
	}
}
