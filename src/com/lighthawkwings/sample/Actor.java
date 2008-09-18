/*
 * Created on Mar 29, 2004 To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package com.lighthawkwings.sample;

import java.util.Random;

import com.lighthawkwings.graphic.Animation;
import com.lighthawkwings.graphic.Sprite;

/**
 * @author Nome To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Actor extends Sprite<GameTest> {
	static Random random = null;

	/**
	 * Quantidade de vida restante do objeto.
	 */
	int life, maxLife;

	public Actor(GameTest game, Animation animation) {
		super(game, animation);
		if (random == null) {
			random = new Random();
		}
		setX(random.nextFloat() * (game.getScreenManager().getWidth() * 2 - getW()));
		setY(random.nextFloat() * (game.getScreenManager().getHeight() * 2 - getH()));
		/* velocidade em pixels / ms */
		setVx((float) ((random.nextFloat() - 0.5) * 0.2));
		setVy((float) ((random.nextFloat() - 0.5) * 0.2));
		setZ(random.nextInt(1000));
		if (getImage() != null) {
			setW(getImage().getWidth(null));
			setH(getImage().getHeight(null));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dudaskank.lighthawkwings.Actor#update(float)
	 */
	public void update(long deltaT) {
		if (this.x < 0) {
			this.vx = Math.abs(this.vx);
		}
		else if (this.x + this.w >= game.getScreenManager().getWidth() * 2) {
			this.vx = -Math.abs(this.vx);
		}
		if (this.y < 0) {
			this.vy = Math.abs(this.vy);
		}
		else if (this.y + this.h >= game.getScreenManager().getHeight() * 2) {
			this.vy = -Math.abs(this.vy);
		}
		super.update(deltaT);
	}

	/**
	 * @return Returns the life.
	 */
	public int getLife() {
		return this.life;
	}

	/**
	 * @param life
	 *            The life to set.
	 */
	public void setLife(int life) {
		this.life = life;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}
}