/*
 * Criado em 30/03/2005 To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package com.lighthawkwings.sample;

import com.lighthawkwings.graphic.Animation;

/**
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 * @author unknown
 */
public class Boss extends Enemy {

	public Boss(GameTest game, Animation animation) {
		super(game, animation);
		setW(getW() * 3);
		setH(getH() * 3);
		setMaxLife(15);
		setLife(getMaxLife());
		setValor(200);
	}
}
