package com.lighthawkwings.sample;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.lighthawkwings.input.GameAction;
import com.lighthawkwings.input.InputManager;
import com.lighthawkwings.object.ShowFPS;
import com.lighthawkwings.state.AbstractGameState;

public class IntroState extends AbstractGameState<GameTest> {
	GameAction init;

	GameAction quit;

	public IntroState(GameTest game) {
		super(game);
	}

	public void update(long elapsedTime) {
		if (init.isPressed()) {
			game.getStateManager().setNextState(new MainState(game));
		} else if (quit.isPressed()) {
			game.stop();
		}
		super.update(elapsedTime);
	}

	public void init() {
		super.init();
		InputManager inputManager = game.getInputManager();
		init = new GameAction("ENTER");
		quit = new GameAction("ESC");
		inputManager.mapToKey(init, KeyEvent.VK_ENTER);
		inputManager.mapToKey(quit, KeyEvent.VK_ESCAPE);
		addGameObject(new IntroMessage(game));
		addGameObject(new ShowFPS(game, Color.WHITE));
	}
}
