package com.lighthawkwings.sound.sampled.events;

import java.util.EventObject;

import com.lighthawkwings.sound.sampled.PlayingStreamed;

public class PlayingStreamedEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	public PlayingStreamedEvent(PlayingStreamed source) {
		super(source);
	}
}
