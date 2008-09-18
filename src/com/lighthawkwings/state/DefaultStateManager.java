package com.lighthawkwings.state;

import com.lighthawkwings.AbstractManager;
import com.lighthawkwings.Game;

public class DefaultStateManager extends AbstractManager implements StateManager {
	Game game;

	GameState activeState;

	GameState nextState;

	public DefaultStateManager(Game game) {
	    this.game = game;
    }

	public GameState getActiveState() {
		return activeState;
	}

	public void setNextState(GameState nextState) {
		this.nextState = nextState;
	}

	public void updateState() {
		if (nextState != null) {
			if (activeState != null) {
				activeState.finish();
				game.getInputManager().clearAllMaps();
			}
			nextState.init();
			activeState = nextState;
			nextState = null;
		}
	}

	@Override
	public void finish() {
	    super.finish();
	    if (activeState != null) {
	    	activeState.finish();
	    	game.getInputManager().clearAllMaps();
	    }
	}
}
