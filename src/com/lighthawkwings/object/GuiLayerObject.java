package com.lighthawkwings.object;

import java.awt.Graphics2D;

import javax.swing.JComponent;

import com.lighthawkwings.Game;

public class GuiLayerObject extends AbstractGameObject<Game> {
	public static final float GUI_Z = Transition.TRANSITION_Z - 2f;

	public GuiLayerObject(Game game) {
		super(game);
		setZ(GUI_Z);
	}

	public JComponent getContainer() {
		return game.getScreenManager().getScreen();
	}

	public void paint(Graphics2D g) {
		getContainer().paintAll(g);
	}

	public void update(long elapsedTime) {
	}

}
