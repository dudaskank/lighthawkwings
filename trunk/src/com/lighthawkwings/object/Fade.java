package com.lighthawkwings.object;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import org.apache.log4j.Logger;

import com.lighthawkwings.Game;

public class Fade extends AbstractTransition<Game> {
	public enum FadeType {
		in, out
	}

	FadeType fade;

	float alpha;

	float alphaStep;

	AlphaComposite alphaComposite;

	Logger logger = Logger.getLogger(getClass());

	public Fade(Game game, FadeType fade, long time) {
		super(game, time);
		this.fade = fade;
		if (fade == FadeType.in) {
			alpha = 1.0f;
			alphaStep = -1.0f / time;
		} else {
			alpha = 0.0f;
			alphaStep = 1.0f / time;
		}

		alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha);

		logger.debug("Tipo: " + fade + ", step: " + alphaStep);

		setX(0);
		setY(0);
		setW(game.getViewPort().width);
		setH(game.getViewPort().height);
		setZ(Transition.TRANSITION_Z);
	}

	public void update(long elapsedTime) {
		super.update(elapsedTime);
		alpha += alphaStep * elapsedTime;
		if (alpha > 1.0f) {
			alpha = 1.0f;
		} else if (alpha < 0.0f) {
			alpha = 0.0f;
		}
		if (getTime() > 0) {
			alphaComposite = alphaComposite.derive(alpha);
		}
	}

	public void paint(Graphics2D g) {
		g.setComposite(alphaComposite);
		g.setColor(Color.BLACK);
		g.fill(game.getViewPort());
		g.setComposite(AlphaComposite.Dst);
	}
}
