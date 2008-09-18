/*
 * Created on Mar 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.lighthawkwings.movement;

import com.lighthawkwings.Game;
import com.lighthawkwings.graphic.Sprite;

/**
 * @author Nome
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class NewtonMovementHandler<G extends Game> implements MovementHandler {

	protected Sprite<G> actor;

	public NewtonMovementHandler(Sprite<G> actor) {
		this.actor = actor;
	}

	/* (non-Javadoc)
	 * @see com.dudaskank.lighthawkwings.MovementHandler#update(float)
	 */
	public void update(long deltaT) {
		actor.setX(actor.getX() + deltaT * actor.getVx());
		actor.setY(actor.getY() + deltaT * actor.getVy());
	}

}
