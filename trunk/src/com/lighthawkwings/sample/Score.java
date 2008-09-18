package com.lighthawkwings.sample;

import java.awt.Color;
import java.awt.Graphics2D;

import com.lighthawkwings.object.AbstractGameObject;

public class Score extends AbstractGameObject<GameTest> {

	public Score(GameTest game, float x, float y) {
		super(game);
		setX(x);
		setY(y);
		setZ(10000);
	}

	public void paint(Graphics2D g) {
		String mensagem;
		mensagem = "Pontos: " + game.getPontos();
		g.setColor(Color.BLACK);
		g.drawString(mensagem, getX(), getY());
		g.setColor(Color.WHITE);
		g.drawString(mensagem, getX() - 1, getY() - 1);
	}

	public void update(long elapsedTime) {
	}
}
