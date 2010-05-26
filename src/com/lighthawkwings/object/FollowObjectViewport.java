package com.lighthawkwings.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.lighthawkwings.Game;

public class FollowObjectViewport extends AbstractGameObject<Game> {
	/** Objeto a seguir pela tela. */
	GameObject gameObject;

	public FollowObjectViewport(Game game, GameObject gameObject) {
		super(game);
		followObject(gameObject);
	}

	public void paint(Graphics2D g) {
	}

	public void update(long deltaT) {
		Rectangle viewPort;
		/* posiciona o viewport para acompanhar o jogador durante o jogo */
		viewPort = game.getViewPort();
		viewPort.x = Math.round(Math.max(0, Math.min((gameObject.getW() / 2f + gameObject.getX())
				- (viewPort.width / 2f), game.getScreenManager().getWidth() * 2f - viewPort.width)));
		viewPort.y = Math.round(Math.max(0, Math.min((gameObject.getH() / 2f + gameObject.getY())
				- (viewPort.height / 2f), game.getScreenManager().getHeight() * 2f - viewPort.height)));
		game.setViewPort(viewPort);
	}

	/**
	 * Sempre retorna false, este objeto nunca colide com os outros.
	 *
	 * @see AbstractGameObject#checkCollision(AbstractGameObject)
	 */
	@Override
	public boolean checkCollision(GameObject other) {
		return false;
	}

	/**
	 * Define o objeto que o viewport irá seguir.
	 *
	 * @param follow
	 */
	public void followObject(GameObject follow) {
		this.gameObject = follow;
		this.z = this.gameObject.getZ() + 1;
	}
}
