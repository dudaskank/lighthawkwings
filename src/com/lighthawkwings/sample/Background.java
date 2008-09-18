/*
 * Created on Mar 29, 2004 To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package com.lighthawkwings.sample;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import com.lighthawkwings.graphic.Animation;
import com.lighthawkwings.graphic.Sprite;

/**
 * @author Nome To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Background extends Sprite<GameTest> {

	public Background(GameTest game, Animation animation) {
		super(game, animation);
		setX(0);
		setY(0);
		setW(game.getScreenManager().getWidth());
		setH(game.getScreenManager().getHeight() * 3);
		setZ(Integer.MIN_VALUE);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.Sprite#update(long)
	 */
	public void update(long deltaT) {
		// não faz nada
	}

	public void paint(Graphics2D g) {
		Image image;
		Rectangle viewPort;
		image = getImage();
		viewPort = game.getViewPort();
		if (image == null) {
			g.setColor(Color.BLACK);
			g.fillRect(viewPort.x, viewPort.y, viewPort.width, viewPort.height);
		} else {
			int i, j, xi, yi;
			xi = viewPort.x % image.getWidth(null);
			yi = viewPort.y % image.getHeight(null);
			if (xi < 0)
				xi += image.getWidth(null);
			if (yi < 0)
				yi += image.getHeight(null);
			for (i = 0; i * image.getHeight(null) - yi <= viewPort.height; i++) {
				for (j = 0; j * image.getWidth(null) - xi <= viewPort.width; j++) {
					g.drawImage(image, j * image.getWidth(null) - xi, i * image.getHeight(null) - yi, null);
				}
			}
		}
	}
}