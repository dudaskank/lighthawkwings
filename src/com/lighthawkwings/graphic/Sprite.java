/*
 * Created on Mar 29, 2004 To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package com.lighthawkwings.graphic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import com.lighthawkwings.Game;
import com.lighthawkwings.movement.MovementHandler;
import com.lighthawkwings.movement.NewtonMovementHandler;
import com.lighthawkwings.object.AbstractGameObject;

/**
 * <p>
 * Subclasse de GameObject que implementa algumas funcionalidades básicas e
 * adiciona outras, como uma imagem para representar o objeto, velocidade nos
 * eixos x e y e uma forma de desenho padrão.
 * </p>
 *
 * @author Eduardo "Dudaskank" M. Oliveira
 */
public abstract class Sprite<G extends Game> extends AbstractGameObject<G> {

	/**
	 * Velocidade no eixo x, em pixels/s
	 */
	protected float vx;

	/**
	 * Velocidade no eixo y, em pixels/s
	 */
	protected float vy;

	/**
	 * Animação do sprite
	 */
	protected Animation animation;

	/**
	 * Gerenciador de movimento do objeto
	 */
	protected MovementHandler movementHandler;

	/**
	 * Construtor da classe.
	 *
	 * @param game
	 *            Instância de <code>Game</code> onde o objeto estará
	 */
	public Sprite(G game, Animation animation) {
		super(game);
		this.animation = animation;
		createMovementHandler();
	}

	/**
	 * Método utilizado para desenhar o objeto.
	 *
	 * @param g
	 *            Onde será desenhado.
	 */
	public void paint(Graphics2D g) {
		Image image;
		Rectangle viewPort;
		image = getImage();
		if (image != null) {
			viewPort = game.getViewPort();
			if (viewPort.intersects(x, y, w, h)) {
				g.drawImage(image, (int) Math.round(x - viewPort.x), (int) Math.round(y - viewPort.y),
						(int) Math.round(w), (int) Math.round(h), game.getScreenManager().getScreen());
			}
		}
	}

	public void createMovementHandler() {
		movementHandler = new NewtonMovementHandler<G>(this);
	}

	/**
	 * Atualiza o objeto.
	 *
	 * @param deltaT
	 *            Tempo passado desde a última atualização, em ms.
	 */
	public void update(long deltaT) {
		if (animation != null) {
			animation.update(deltaT);
		}
		if (movementHandler != null) {
			movementHandler.update(deltaT);
		}
	}

	/**
	 * @return Retorna a velocidade no eixo x, em pixels/ms.
	 */
	public float getVx() {
		return this.vx;
	}

	/**
	 * @param vx
	 *            Altera a velocidade no eixo x, em pixels/ms.
	 */
	public void setVx(float vx) {
		this.vx = vx;
	}

	/**
	 * @param vx
	 *            Retorna a velocidade no eixo y, em pixels/ms.
	 */
	public float getVy() {
		return this.vy;
	}

	/**
	 * @param vy
	 *            Altera a velocidade no eixo y, em pixels/ms.
	 */
	public void setVy(float vy) {
		this.vy = vy;
	}

	/**
	 * @return Retorna a imagem do sprite.
	 */
	public Image getImage() {
		if (this.animation != null) {
			return this.animation.getImage();
		} else {
			return null;
		}
	}
}