package com.lighthawkwings.sample;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.lighthawkwings.object.AbstractGameObject;

public class IntroMessage extends AbstractGameObject<GameTest> {
	Image buffer;

	public IntroMessage(GameTest game) {
		super(game);
		setZ(10000);
	}

	public void paint(Graphics2D g) {
		int i;
		int x = game.getViewPort().x;
		int y = game.getViewPort().y;
		int w = game.getViewPort().width;
		int h = game.getViewPort().height;

		// na primeira vez que for desenhar, cria e desenha o texto no buffer
		if (buffer == null) {
			String mensagens[] = { "Setas - Movimentar",
					"Enter/Espaço/Botão Mouse 1 - Atirar", "ESC - Sair",
					"P - Pausa/Reinicia música", " ",
					"Aperte ENTER para iniciar ou ESC para sair" };
			Shape shape;
			Stroke stroke;
			Font font;
			FontRenderContext frc;
			TextLayout layout;
			Rectangle2D textBounds;
			Graphics2D bufferGraphics;

			buffer = new BufferedImage(w, h, BufferedImage.OPAQUE); //game.getScreenManager().getScreen().createImage(w, h);
			bufferGraphics = (Graphics2D) buffer.getGraphics();
			bufferGraphics.setBackground(Color.BLACK);
			bufferGraphics.setRenderingHint(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			bufferGraphics.setBackground(Color.BLACK);
			bufferGraphics.clearRect(x, y, w, h);

			frc = bufferGraphics.getFontRenderContext();
			font = bufferGraphics.getFont().deriveFont(w / 24.0f).deriveFont(
					Font.BOLD);
			bufferGraphics.setFont(font);
			stroke = new BasicStroke(w / 320.0f);
			bufferGraphics.setStroke(stroke);

			for (i = 0; i < mensagens.length; i++) {
				layout = new TextLayout(mensagens[i], font, frc);
				textBounds = layout.getBounds();
				shape = layout.getOutline(AffineTransform.getTranslateInstance(
						w / 2.0 - textBounds.getWidth() / 2.0, h / 2.0
								+ bufferGraphics.getFontMetrics().getHeight()
								* i));
				bufferGraphics.setColor(Color.BLUE);
				bufferGraphics.draw(shape);
				bufferGraphics.setColor(Color.WHITE);
				bufferGraphics.fill(shape);
			}
		}

		// desenha as mensagens na tela
		g.drawImage(buffer, 0, 0, null);
	}

	public void update(long elapsedTime) {
	}
}
