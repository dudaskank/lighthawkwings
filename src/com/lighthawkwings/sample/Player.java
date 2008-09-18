/*
 * Created on Mar 29, 2004 To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package com.lighthawkwings.sample;

import java.awt.Image;

import org.apache.log4j.Logger;

import com.lighthawkwings.graphic.Animation;

/**
 * @author Nome To change the template for this generated type comment go to Window - Preferences - Java - Code
 *         Generation - Code and Comments
 */
public class Player extends Actor {

	long ultimoTiro = 0;

	static final int intervaloTiro = 100;

	boolean firing;

	Image image;

	Animation anim[];

	protected Logger logger = Logger.getLogger(getClass());

	public final static float MAX_ABS_VX = 0.5f;

	public final static float MAX_ABS_VY = 0.5f;

	public final static float ACCELERATION = 0.01f;

	public Player(GameTest game, int maxLife) {
		super(game, null);
		int i;
		/* velocidade em pixels / segundo */
		setVx(0);
		setVy(0);
		/* pra ter certeza que ficará em cima de todos os outros */
		setZ(1001);
		setMaxLife(maxLife);
		setLife(getMaxLife());
		/* cria as animações */
		anim = new Animation[5];
		for (i = 0; i < 5; i++) {
			anim[i] = new Animation();
			anim[i].addFrame(game.getResourceManager().loadImage("/data/images/ship" + (i + 1) + ".gif"),
					Integer.MAX_VALUE);
		}
		this.animation = anim[2];
		if (getImage() != null) {
			setW(getImage().getWidth(null));
			setH(getImage().getHeight(null));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.dudaskank.lighthawkwings.Actor#update(float)
	 */
	public void update(long deltaT) {
		Explosion explosao;
		int animacao;
		/* vê se está atirando */
		if (isFiring()) {
			if (ultimoTiro + intervaloTiro < game.getClock().getCurrentTime()) {
				game.getStateManager().getActiveState().addGameObject(new Shot(game, this));
				ultimoTiro = game.getClock().getCurrentTime();
			}
		}
		/* Faz a movimentação normal */
		super.update(deltaT);
		/* se acabou a energia, fim do jogo */
		if (getLife() <= 0) {
			logger.debug("Player died");
			game.getStateManager().getActiveState().removeGameObject(this);
			explosao = new Explosion(game, this);
			game.getStateManager().getActiveState().addGameObject(explosao);
		}
		animacao = 2 + (int) (getVx() / MAX_ABS_VX * 2);
		this.animation = this.anim[animacao];
	}

	/**
	 * @return Returns the firing.
	 */
	public boolean isFiring() {
		return this.firing;
	}

	/**
	 * @param firing
	 *            The firing to set.
	 */
	public void setFiring(boolean firing) {
		this.firing = firing;
	}
}