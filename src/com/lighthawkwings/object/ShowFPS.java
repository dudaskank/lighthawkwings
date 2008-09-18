package com.lighthawkwings.object;

import java.awt.Color;
import java.awt.Graphics2D;

import com.lighthawkwings.Game;

public class ShowFPS extends AbstractGameObject<Game> {

	int lastFps;

	String str;

	Color color;

	public ShowFPS(Game game, Color color) {
		super(game);
		lastFps = 0;
		str = "FPS: -";
		this.color = color;
		setZ(Transition.TRANSITION_Z - 1f);
	}

	public void paint(Graphics2D g) {
		g.setFont(game.getScreenManager().getScreen().getFont());
		g.setColor(color);
		g.drawString(str, x, y + g.getFontMetrics().getAscent());
	}

	public void update(long elapsedTime) {
		int currentFps = game.getStateManager().getActiveState().getCurrentFps();
		if (currentFps != lastFps) {
			str = String.format("FPS: %3d", currentFps);
			lastFps = currentFps;
		}
	}
}
