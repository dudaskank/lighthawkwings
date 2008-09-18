/*
 * Criado em 30/03/2005 To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package com.lighthawkwings.sample;

import com.lighthawkwings.graphic.Animation;
import com.lighthawkwings.graphic.Sprite;
import com.lighthawkwings.sound.sampled.Sound;

/**
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation -
 * Code and Comments
 *
 * @author unknown
 */
public class Explosion extends Sprite<GameTest> {

	/**
	 * Guarda o �ltimo frame mostrado na anima��o, usado para apagar a explos�o depois que der um loop completo
	 * pelas imagens.
	 */
	int ultimoFrame;

	/**
	 * Som da explos�o.
	 */
	Sound boom;

	public Explosion(GameTest game, Sprite<GameTest> origem) {
		super(game, (Animation) game.getAnimExplosao().clone());
		setX(origem.getX());
		setY(origem.getY());
		setZ(origem.getZ());
		setVx(origem.getVx());
		setVy(origem.getVy());
		setW(origem.getW());
		setH(origem.getH());
		ultimoFrame = -1;
		boom = game.getResourceManager().loadSound("/data/sounds/boom2.wav");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.graphics.Sprite#update(long)
	 */
	public void update(long deltaT) {
		super.update(deltaT);
		if (ultimoFrame == -1) {
			ultimoFrame = animation.getCurrentFrameIndex();
			if (game.getSampledSoundManager() != null) {
				game.getSampledSoundManager().play(boom);
			}
		} else {
			/* j� deu um loop pelos frames, apaga o objeto */
			if (animation.getCurrentFrameIndex() < ultimoFrame || animation.getTotalFrames() == 1) {
				game.getStateManager().getActiveState().removeGameObject(this);
			}
			ultimoFrame = animation.getCurrentFrameIndex();
		}
	}
}