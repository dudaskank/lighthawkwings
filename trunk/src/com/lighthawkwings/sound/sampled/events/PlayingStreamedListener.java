package com.lighthawkwings.sound.sampled.events;

import java.util.EventListener;

public interface PlayingStreamedListener extends EventListener {
	public void startPlaying(PlayingStreamedEvent event);

	public void stopPlaying(PlayingStreamedEvent event);
}
