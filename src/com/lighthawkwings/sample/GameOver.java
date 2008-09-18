package com.lighthawkwings.sample;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.lighthawkwings.object.AbstractGameObject;

public class GameOver extends AbstractGameObject<GameTest> {
	String mensagens[];

	Font font;

	public GameOver(GameTest game, String mensagem) {
		super(game);
		setZ(10000);
		mensagens = new String[3];
		mensagens[0] = "GAME OVER";
		mensagens[1] = "Aperte ESC para sair";
		mensagens[2] = mensagem;
	}

	public void paint(Graphics2D g) {
		int i, tamanhoMensagem;
		int w = game.getViewPort().width;
		int h = game.getViewPort().height;

		if (font == null) {
			font = g.getFont().deriveFont(game.getScreenManager().getWidth() / 32.0f).deriveFont(Font.BOLD);
		}
		g.setFont(font);

		for (i = 0; i < mensagens.length; i++) {
			tamanhoMensagem = g.getFontMetrics().stringWidth(mensagens[i]);
			g.setColor(Color.WHITE);
			g.drawString(mensagens[i], (w - tamanhoMensagem) / 2, h / 2 + i * g.getFontMetrics().getHeight());
		}
	}

	public void update(long elapsedTime) {
	}
}
