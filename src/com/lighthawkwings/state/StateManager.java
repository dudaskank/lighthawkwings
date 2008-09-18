package com.lighthawkwings.state;

import com.lighthawkwings.Manager;

public interface StateManager extends Manager {
	public GameState getActiveState();

	public void setNextState(GameState nextState);

	public void updateState();
}
