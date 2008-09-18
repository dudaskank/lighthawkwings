package com.lighthawkwings.input;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import com.lighthawkwings.AbstractManager;

/**
 * The InputManager manages input of key and mouse events. Events are mapped to GameActions.
 */
public class DefaultInputManager extends AbstractManager implements InputManager {
	private static final int NUM_MOUSE_CODES = 9;

	// key codes are defined in java.awt.KeyEvent.
	// most of the codes (except for some rare ones like
	// "alt graph") are less than 600.
	private static final int NUM_KEY_CODES = 600;

	private GameAction[] keyActions = new GameAction[NUM_KEY_CODES];

	private GameAction[] mouseActions = new GameAction[NUM_MOUSE_CODES];

	private Point mouseLocation;

	private Point centerLocation;

	private JComponent comp;

	private Robot robot;

	private boolean isRecentering;

	/**
	 * Creates a new InputManager that listens to input from the specified component.
	 */
	public DefaultInputManager(JComponent comp) {
		this.comp = comp;
		mouseLocation = new Point();
		centerLocation = new Point();

		// register key and mouse listeners
		comp.addKeyListener(this);
		comp.addMouseListener(this);
		comp.addMouseMotionListener(this);
		comp.addMouseWheelListener(this);

		// allow input of the TAB key and other keys normally
		// used for focus traversal
		comp.setFocusTraversalKeysEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#setRelativeMouseMode(boolean)
	 */
	public void setRelativeMouseMode(boolean mode) {
		if (mode == isRelativeMouseMode()) {
			return;
		}

		if (mode) {
			try {
				robot = new Robot();
				recenterMouse();
			} catch (AWTException ex) {
				// couldn't create robot!
				robot = null;
			}
		} else {
			robot = null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#isRelativeMouseMode()
	 */
	public boolean isRelativeMouseMode() {
		return (robot != null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#mapToKey(com.lighthawkwings.input.GameAction, int)
	 */
	public void mapToKey(GameAction gameAction, int keyCode) {
		keyActions[keyCode] = gameAction;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#mapToMouse(com.lighthawkwings.input.GameAction, int)
	 */
	public void mapToMouse(GameAction gameAction, int mouseCode) {
		mouseActions[mouseCode] = gameAction;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#clearMap(com.lighthawkwings.input.GameAction)
	 */
	public void clearMap(GameAction gameAction) {
		for (int i = 0; i < keyActions.length; i++) {
			if (keyActions[i] == gameAction) {
				keyActions[i] = null;
			}
		}

		for (int i = 0; i < mouseActions.length; i++) {
			if (mouseActions[i] == gameAction) {
				mouseActions[i] = null;
			}
		}

		gameAction.reset();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#clearAllMaps()
	 */
	public void clearAllMaps() {
		for (int i = 0; i < keyActions.length; i++) {
			keyActions[i] = null;
		}

		for (int i = 0; i < mouseActions.length; i++) {
			mouseActions[i] = null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#getMaps(com.lighthawkwings.input.GameAction)
	 */
	public List<String> getMaps(GameAction gameCode) {
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < keyActions.length; i++) {
			if (keyActions[i] == gameCode) {
				list.add(getKeyName(i));
			}
		}

		for (int i = 0; i < mouseActions.length; i++) {
			if (mouseActions[i] == gameCode) {
				list.add(getMouseName(i));
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#resetAllGameActions()
	 */
	public void resetAllGameActions() {
		for (int i = 0; i < keyActions.length; i++) {
			if (keyActions[i] != null) {
				keyActions[i].reset();
			}
		}

		for (int i = 0; i < mouseActions.length; i++) {
			if (mouseActions[i] != null) {
				mouseActions[i].reset();
			}
		}
	}

	/**
	 * Gets the name of a key code.
	 */
	public static String getKeyName(int keyCode) {
		return KeyEvent.getKeyText(keyCode);
	}

	/**
	 * Gets the name of a mouse code.
	 */
	public static String getMouseName(int mouseCode) {
		switch (mouseCode) {
		case MOUSE_MOVE_LEFT:
			return "Mouse Left";
		case MOUSE_MOVE_RIGHT:
			return "Mouse Right";
		case MOUSE_MOVE_UP:
			return "Mouse Up";
		case MOUSE_MOVE_DOWN:
			return "Mouse Down";
		case MOUSE_WHEEL_UP:
			return "Mouse Wheel Up";
		case MOUSE_WHEEL_DOWN:
			return "Mouse Wheel Down";
		case MOUSE_BUTTON_1:
			return "Mouse Button 1";
		case MOUSE_BUTTON_2:
			return "Mouse Button 2";
		case MOUSE_BUTTON_3:
			return "Mouse Button 3";
		default:
			return "Unknown mouse code " + mouseCode;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#getMouseX()
	 */
	public int getMouseX() {
		return mouseLocation.x;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#getMouseY()
	 */
	public int getMouseY() {
		return mouseLocation.y;
	}

	/**
	 * Uses the Robot class to try to postion the mouse in the center of the screen.
	 * <p>
	 * Note that use of the Robot class may not be available on all platforms.
	 */
	private synchronized void recenterMouse() {
		if (robot != null && comp.isShowing()) {
			centerLocation.x = comp.getWidth() / 2;
			centerLocation.y = comp.getHeight() / 2;
			SwingUtilities.convertPointToScreen(centerLocation, comp);
			isRecentering = true;
			robot.mouseMove(centerLocation.x, centerLocation.y);
		}
	}

	private GameAction getKeyAction(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode < keyActions.length) {
			return keyActions[keyCode];
		} else {
			return null;
		}
	}

	/**
	 * Gets the mouse code for the button specified in this MouseEvent.
	 */
	public static int getMouseButtonCode(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			return MOUSE_BUTTON_1;
		case MouseEvent.BUTTON2:
			return MOUSE_BUTTON_2;
		case MouseEvent.BUTTON3:
			return MOUSE_BUTTON_3;
		default:
			return -1;
		}
	}

	private GameAction getMouseButtonAction(MouseEvent e) {
		int mouseCode = getMouseButtonCode(e);
		if (mouseCode != -1) {
			return mouseActions[mouseCode];
		} else {
			return null;
		}
	}

	// from the KeyListener interface
	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		GameAction gameAction = getKeyAction(e);
		if (gameAction != null) {
			gameAction.press();
		}
		// make sure the key isn't processed for anything else
		e.consume();
	}

	// from the KeyListener interface
	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		GameAction gameAction = getKeyAction(e);
		if (gameAction != null) {
			gameAction.release();
		}
		// make sure the key isn't processed for anything else
		e.consume();
	}

	// from the KeyListener interface
	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		// make sure the key isn't processed for anything else
		e.consume();
	}

	// from the MouseListener interface
	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		GameAction gameAction = getMouseButtonAction(e);
		if (gameAction != null) {
			gameAction.press();
		}
	}

	// from the MouseListener interface
	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		GameAction gameAction = getMouseButtonAction(e);
		if (gameAction != null) {
			gameAction.release();
		}
	}

	// from the MouseListener interface
	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		// do nothing
	}

	// from the MouseListener interface
	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	// from the MouseListener interface
	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	// from the MouseMotionListener interface
	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	// from the MouseMotionListener interface
	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#mouseMoved(java.awt.event.MouseEvent)
	 */
	public synchronized void mouseMoved(MouseEvent e) {
		int mx, my;
		// calcula a posição relativa a tela de jogo
		mx = e.getXOnScreen() - comp.getLocationOnScreen().x;
		my = e.getYOnScreen() - comp.getLocationOnScreen().y;
		// this event is from re-centering the mouse - ignore it
		if (isRecentering && centerLocation.x == mx && centerLocation.y == my) {
			isRecentering = false;
		} else {
			int dx = mx - mouseLocation.x;
			int dy = my - mouseLocation.y;
			mouseHelper(MOUSE_MOVE_LEFT, MOUSE_MOVE_RIGHT, dx);
			mouseHelper(MOUSE_MOVE_UP, MOUSE_MOVE_DOWN, dy);

			if (isRelativeMouseMode()) {
				recenterMouse();
			}
		}

		mouseLocation.x = mx;
		mouseLocation.y = my;

	}

	// from the MouseWheelListener interface
	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.input.InputManager#mouseWheelMoved(java.awt.event.MouseWheelEvent)
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseHelper(MOUSE_WHEEL_UP, MOUSE_WHEEL_DOWN, e.getWheelRotation());
	}

	private void mouseHelper(int codeNeg, int codePos, int amount) {
		GameAction gameAction;
		if (amount < 0) {
			gameAction = mouseActions[codeNeg];
		} else {
			gameAction = mouseActions[codePos];
		}
		if (gameAction != null) {
			gameAction.press(Math.abs(amount));
			gameAction.release();
		}
	}

}
