package com.lighthawkwings.graphic;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

import com.lighthawkwings.AbstractManager;
import com.lighthawkwings.Game;

public class DefaultScreenManager extends AbstractManager implements ScreenManager, PropertyChangeListener {
	protected Game game;

	protected JFrame screen;

	protected JPanel panelScreen;

	protected DisplayMode mode;

	protected boolean fullScreen;

	protected Insets insets;

	protected BufferStrategy strategy;

	protected boolean antialias;

	protected BufferedImage buffer;

	public DefaultScreenManager(Game game) {
		this.game = game;
		game.getProperties().addPropertyChangeListener(ScreenManager.ANTIALIASED_PROPERTY, this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.ScreenManager#init()
	 */
	@Override
	public void init() {
		init(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.ScreenManager#init(int, int, int, int)
	 */
	public void init(int w, int h, int bpp, int rr) {
		init(new DisplayMode(w, h, bpp, rr), false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.ScreenManager#init(int, int, int, int, boolean)
	 */
	public void init(int w, int h, int bpp, int rr, boolean fullScreen) {
		init(new DisplayMode(w, h, bpp, rr), fullScreen);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.ScreenManager#init(java.awt.DisplayMode)
	 */
	public void init(DisplayMode mode) {
		init(mode, false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.ScreenManager#init(java.awt.DisplayMode, boolean)
	 */
	public void init(DisplayMode mode, boolean fullScreen) {
		super.init();

		if (screen != null) {
			finish();
		}
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		GraphicsConfiguration gc = device.getDefaultConfiguration();

		this.mode = mode;
		this.fullScreen = fullScreen;

		screen = new JFrame(game.getGameName(), gc);
		screen.setIgnoreRepaint(true);
		screen.setResizable(false);
		screen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		panelScreen = new JPanel();
		panelScreen.setOpaque(false);
		panelScreen.setIgnoreRepaint(true);
		panelScreen.setFocusable(true);
		screen.getContentPane().add(panelScreen);
		panelScreen.requestFocus();

		if (fullScreen) {
			screen.setUndecorated(true);
			device.setFullScreenWindow(screen);
			device.setDisplayMode(mode);
			insets = screen.getInsets();
		} else {
			screen.setVisible(true);
			insets = screen.getInsets();
			screen.setPreferredSize(new Dimension(insets.left + insets.right + mode.getWidth(), insets.top
			        + insets.bottom + mode.getHeight()));
			screen.pack();
			Toolkit toolKit = Toolkit.getDefaultToolkit();
			screen.setLocation(toolKit.getScreenSize().width / 2 - screen.getWidth() / 2,
			        toolKit.getScreenSize().height / 2 - screen.getHeight() / 2);
		}
		screen.createBufferStrategy(2);
		strategy = screen.getBufferStrategy();

		/* instala o NullRepaintManager para acelerar um pouco mais as coisas */
		RepaintManager repaintManager = new NullRepaintManager();
		repaintManager.setDoubleBufferingEnabled(false);
		RepaintManager.setCurrentManager(repaintManager);

		screen.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				logger.debug("Closing window");
			    game.stop();
			}
		});

		antialias = game.getProperties().getBoolean(ScreenManager.ANTIALIASED_PROPERTY);

		buffer = new BufferedImage(mode.getWidth(), mode.getHeight(), BufferedImage.OPAQUE);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.ScreenManager#finish()
	 */
	public void finish() {
		if (screen != null) {
			if (fullScreen) {
				GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice device = env.getDefaultScreenDevice();
				device.setFullScreenWindow(null);
			}
			screen.dispose();
			screen = null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.ScreenManager#getDrawGraphics()
	 */
	public Graphics2D getDrawGraphics() {
		Graphics2D graphics;
//		graphics = (Graphics2D) strategy.getDrawGraphics();
//		graphics.translate(insets.left, insets.top);
		graphics = buffer.createGraphics();
		if (isAntialiased()) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		return graphics;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.ScreenManager#showBuffer()
	 */
	public void showBuffer() {
		Graphics2D graphics;
		graphics = (Graphics2D) strategy.getDrawGraphics();
		if (graphics != null && !strategy.contentsLost()){
			graphics.translate(insets.left, insets.top);
			graphics.drawImage(buffer, 0, 0, null);
			strategy.show();
		}
		graphics.dispose();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.ScreenManager#getScreen()
	 */
	public JComponent getScreen() {
		return panelScreen;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.ScreenManager#getWidth()
	 */
	public int getWidth() {
		return mode.getWidth();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.ScreenManager#getHeight()
	 */
	public int getHeight() {
		return mode.getHeight();
	}

	/**
	 * The NullRepaintManager is a RepaintManager that doesn't do any repainting. Useful when all the rendering is
	 * done manually by the application.
	 */
	public class NullRepaintManager extends RepaintManager {
		public void addInvalidComponent(JComponent c) {
			// do nothing
		}

		public void addDirtyRegion(JComponent c, int x, int y, int w, int h) {
			// do nothing
		}

		public void markCompletelyDirty(JComponent c) {
			// do nothing
		}

		public void paintDirtyRegions() {
			// do nothing
		}
	}

	public void setTitle(String title) {
		screen.setTitle(title);
	}

	public boolean isAntialiased() {
		return antialias;
	}

	public void setAntialias(boolean antialias) {
		// a variável muda no evento propertyChange
		game.getProperties().put(ScreenManager.ANTIALIASED_PROPERTY, Boolean.valueOf(antialias).toString());
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(ScreenManager.ANTIALIASED_PROPERTY)) {
			this.antialias = game.getProperties().getBoolean(ScreenManager.ANTIALIASED_PROPERTY);
		}
	}
}