package com.lighthawkwings.graphic;

import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JComponent;

import com.lighthawkwings.Manager;

public interface ScreenManager extends Manager {
	/**
	 * An invisible cursor.
	 */
	public static final Cursor INVISIBLE_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(
	        Toolkit.getDefaultToolkit().getImage(""), new Point(0, 0), "invisible");

	public static final String ANTIALIASED_PROPERTY = "screen.antialiased";

	public void init(int w, int h, int bpp, int rr);

	public void init(int w, int h, int bpp, int rr, boolean fullScreen);

	public void init(DisplayMode mode);

	public void init(DisplayMode mode, boolean fullScreen);

	public Graphics2D getDrawGraphics();

	public void showBuffer();

	public JComponent getScreen();

	public int getWidth();

	public int getHeight();

	public void setTitle(String title);

	public boolean isAntialiased();

	public void setAntialias(boolean antialias);
}