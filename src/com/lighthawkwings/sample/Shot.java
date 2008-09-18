/*
 * Criado em 26/02/2005 To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package com.lighthawkwings.sample;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.lighthawkwings.graphic.Sprite;
import com.lighthawkwings.object.GameObject;

/**
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 * @author unknown
 */
public class Shot extends Sprite<GameTest> {
	protected long vida;

	public Shot(GameTest game, GameObject pai) {
		super(game, null);
		setVx(0);
		setVy(-1);
		setW(3);
		setH(25);
		setX(pai.getX() + pai.getW() / 2 - getW() / 2);
		setY(pai.getY() - getH());
		setZ(pai.getZ() + 1);
		// um segundo a duração
		vida = 1000;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lighthawkwings.core.GameObject#paint(java.awt.Graphics)
	 */
	public void paint(Graphics2D g) {
		Rectangle viewPort;
		viewPort = game.getViewPort();
		if (viewPort.intersects(this.x, this.y, this.w, this.h)) {
			g.setColor(Color.YELLOW);
			g.fillRect((int) (getX() - viewPort.x + 0.5), (int) (getY() - viewPort.y + 0.5),
					(int) (getW() + 0.5), (int) (getH() + 0.5));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.lighthawkwings.core.GameObject#update(float)
	 */
	public void update(long deltaT) {
		super.update(deltaT);
		vida -= deltaT;
		if (vida <= 0) {
			game.getStateManager().getActiveState().removeGameObject(this);
		}
	}
}