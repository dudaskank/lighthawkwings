package com.lighthawkwings.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.List;

import com.lighthawkwings.Manager;

public interface InputManager extends Manager, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	// mouse codes
	public static final int MOUSE_MOVE_LEFT = 0;

	public static final int MOUSE_MOVE_RIGHT = 1;

	public static final int MOUSE_MOVE_UP = 2;

	public static final int MOUSE_MOVE_DOWN = 3;

	public static final int MOUSE_WHEEL_UP = 4;

	public static final int MOUSE_WHEEL_DOWN = 5;

	public static final int MOUSE_BUTTON_1 = 6;

	public static final int MOUSE_BUTTON_2 = 7;

	public static final int MOUSE_BUTTON_3 = 8;

	/**
	 * Sets whether realtive mouse mode is on or not. For relative mouse mode,
	 * the mouse is "locked" in the center of the screen, and only the changed
	 * in mouse movement is measured. In normal mode, the mouse is free to move
	 * about the screen.
	 */
	public void setRelativeMouseMode(boolean mode);

	/**
	 * Returns whether or not relative mouse mode is on.
	 */
	public boolean isRelativeMouseMode();

	/**
	 * Maps a GameAction to a specific key. The key codes are defined in
	 * java.awt.KeyEvent. If the key already has a GameAction mapped to it, the
	 * new GameAction overwrites it.
	 */
	public void mapToKey(GameAction gameAction, int keyCode);

	/**
	 * Maps a GameAction to a specific mouse action. The mouse codes are defined
	 * herer in InputManager (MOUSE_MOVE_LEFT, MOUSE_BUTTON_1, etc). If the
	 * mouse action already has a GameAction mapped to it, the new GameAction
	 * overwrites it.
	 */
	public void mapToMouse(GameAction gameAction, int mouseCode);

	/**
	 * Clears all mapped keys and mouse actions to this GameAction.
	 */
	public void clearMap(GameAction gameAction);

	/**
	 * Clears all mapped keys and mouse actions.
	 */
	public void clearAllMaps();

	/**
	 * Gets a List of names of the keys and mouse actions mapped to this
	 * GameAction. Each entry in the List is a String.
	 */
	public List<String> getMaps(GameAction gameCode);

	/**
	 * Resets all GameActions so they appear like they haven't been pressed.
	 */
	public void resetAllGameActions();

	/**
	 * Gets the x position of the mouse.
	 */
	public int getMouseX();

	/**
	 * Gets the y position of the mouse.
	 */
	public int getMouseY();
}