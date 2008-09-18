package com.lighthawkwings.sample;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.lighthawkwings.DefaultGame;
import com.lighthawkwings.graphic.Animation;
import com.lighthawkwings.graphic.DefaultScreenManager;
import com.lighthawkwings.graphic.ScreenManager;
import com.lighthawkwings.timer.NanoClock;

public class GameTest extends DefaultGame {

	Animation animExplosao;

	int pontos;

	public static void main(String[] args) {
		PatternLayout layout = new PatternLayout("[%5p] %m%n");
		Logger.getRoot().addAppender(new ConsoleAppender(layout, "System.out"));
		Logger.getRoot().setLevel(Level.INFO);
		Logger.getLogger(NanoClock.class).setLevel(Level.DEBUG);
		GameTest game = new GameTest();
		IntroState intro;
		intro = new IntroState(game);
		try {
			game.run(intro);
		} finally {
		}
	}

	protected void createScreenManager() {
		screenManager = new DefaultScreenManager(this);
		screenManager.init(320, 240, 32, 0, false);
		screenManager.getScreen().setCursor(ScreenManager.INVISIBLE_CURSOR);
	}

	public Animation getAnimExplosao() {
		return animExplosao;
	}

	public void setAnimExplosao(Animation animExplosao) {
		this.animExplosao = animExplosao;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

}
