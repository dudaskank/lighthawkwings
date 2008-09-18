package com.lighthawkwings.sample;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.lighthawkwings.object.AbstractGameObject;

public class EnergyBar extends AbstractGameObject<GameTest> {
	Actor actor;

	Rectangle contorno, preenchimento;

	Color corPreenchimento;

	public EnergyBar(GameTest game, Actor actor, Rectangle area) {
		super(game);
		setZ(10000);
		setX(area.x);
		setY(area.y);
		setW(area.width);
		setH(area.height);
		this.actor = actor;
		this.contorno = new Rectangle(area);
		this.preenchimento = new Rectangle(area.x + 1, area.y + 1,
				area.width - 2, area.height - 2);
		this.corPreenchimento = new Color(255, 0, 0, 128);
	}

	public void paint(Graphics2D g) {
		float pct;
		pct = ((float) actor.getLife()) / ((float) actor.getMaxLife());
		if (pct < 0) {
			pct = 0;
		}
		else if (pct > 1) {
			pct = 1;
		}
		g.setColor(Color.RED);
		g.draw(contorno);
		g.setColor(corPreenchimento);
		preenchimento.width = (int) ((contorno.width - 2) * pct);
		g.fill(preenchimento);
	}

	public void update(long elapsedTime) {
	}
}
