/*
 * Criado em 15/03/2005 To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package com.lighthawkwings.sample;

import java.util.Iterator;

import com.lighthawkwings.graphic.Animation;
import com.lighthawkwings.object.GameObject;

/**
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 *
 * @author unknown
 */
public class Enemy extends Actor {

	/**
	 * Valor da pontuação quando destruído o inimigo.
	 */
	int valor;

	/**
	 * @param game
	 * @param animation
	 */
	public Enemy(GameTest game, Animation animation) {
		super(game, animation);
		setMaxLife(1);
		setLife(getMaxLife());
		setValor(10);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.sample.SimpleActor#update(long)
	 */
	public void update(long deltaT) {
		GameObject gameObject;
		Explosion explosao;
		super.update(deltaT);
		/* se colidiu com algum objeto da classe Tiro, é destruído */
		gameObject = foiAtingido();
		if (gameObject != null) {
			setLife(getLife() - 1);
			if (gameObject instanceof Shot) {
				game.getStateManager().getActiveState().removeGameObject(gameObject);
			}
			else if (gameObject instanceof Player) {
				((Player) gameObject).setLife(((Player) gameObject).getLife()
						- getMaxLife() * 10);
			}
		}
		if (getLife() <= 0) {
			game.getStateManager().getActiveState().removeGameObject(this);
			explosao = new Explosion(game, this);
			game.getStateManager().getActiveState().addGameObject(explosao);
			/* atualiza o placar */
			game.setPontos(game.getPontos() + getValor());
		}
	}

	/**
	 * Verifica se o inimigo foi atingido por um tiro
	 *
	 * @return Retorna o tiro que o atingiu, ou null se não foi atingido por um
	 *         tiro.
	 */
	public GameObject foiAtingido() {
		Iterator<GameObject> iterator;
		GameObject gameObject;
		iterator = game.getStateManager().getActiveState().getObjects().iterator();
		while (iterator.hasNext()) {
			gameObject = iterator.next();
			if (gameObject instanceof Shot || gameObject instanceof Player) {
				if (checkCollision(gameObject)) {
					return gameObject;
				}
			}
		}
		return null;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Animation getAnimation() {
		return animation;
	}
}
