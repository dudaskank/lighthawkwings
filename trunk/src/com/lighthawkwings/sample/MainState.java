package com.lighthawkwings.sample;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import com.lighthawkwings.ResourceManager;
import com.lighthawkwings.graphic.Animation;
import com.lighthawkwings.graphic.ScreenManager;
import com.lighthawkwings.input.GameAction;
import com.lighthawkwings.input.InputManager;
import com.lighthawkwings.object.FollowObjectViewport;
import com.lighthawkwings.object.GameObject;
import com.lighthawkwings.object.ShowFPS;
import com.lighthawkwings.sound.midi.MidiSoundManager;
import com.lighthawkwings.sound.sampled.Sound;
import com.lighthawkwings.state.AbstractGameState;

/**
 * @author Eduardo Oliveira
 */
public class MainState extends AbstractGameState<GameTest> {
	public MainState(GameTest game) {
		super(game);
	}

	GameAction quit;

	GameAction moveLeft;

	GameAction moveRight;

	GameAction moveUp;

	GameAction moveDown;

	GameAction fire;

	GameAction pauseMusic;

	Player player;

	GameObject gameOver;

	Sequence gameMusic;

	Sound gameOverSound;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.Game#init()
	 */
	public void init() {
		super.init();

		Graphics g;
		int nActors = 300, i, nAnimAst = 15, nAnimExplosao = 30;
		Animation animBackGround, animAst;

		/* mostra que está carregando dados */
		ScreenManager screenManager = game.getScreenManager();
		g = screenManager.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, screenManager.getWidth(), screenManager.getHeight());
		g.setColor(Color.WHITE);
		g.drawString("Carregando...", screenManager.getWidth() / 2, screenManager.getHeight() / 2);
		g.dispose();
		screenManager.showBuffer();

		/* cria e carrega as animações */
		ResourceManager resourceManager = game.getResourceManager();
		animBackGround = new Animation();
		animBackGround.addFrame(resourceManager.loadImage("/data/images/space.png"), Integer.MAX_VALUE);
		animAst = new Animation();
		for (i = 1; i <= nAnimAst; i++) {
			animAst.addFrame(resourceManager.loadImage("/data/images/asta"
			        + ((i < 10) ? "0" + i : String.valueOf(i)) + ".gif"), 100);
		}
		Animation animExplosao;
		animExplosao = new Animation();
		for (i = 1; i <= nAnimExplosao; i++) {
			String arq = "/data/images/e" + ((i < 10) ? "0" + i : String.valueOf(i)) + ".gif";
			animExplosao.addFrame(resourceManager.loadImage(arq), 30);
		}
		game.setAnimExplosao(animExplosao);

		gameMusic = resourceManager.loadSequence("/data/sounds/game.mid");
		gameOverSound = resourceManager.loadSound("/data/sounds/death.wav");
		resourceManager.loadSound("/data/sounds/boom.wav");

		/* adiciona os atores que ficam pulando de um lado ao outro */
		for (i = 0; i < nActors; i++) {
			Animation a = (Animation) animAst.clone();
			/* faz um update para apenas mudar o quadro atual de animação, para não parecerem todos iguais. */
			a.update((int) (Math.random() * 100000));
			this.addGameObject(new Enemy(game, a));
		}
		/* adiciona o chefão, hehe */
		this.addGameObject(new Boss(game, (Animation) animAst.clone()));
		/* adiciona o background */
		this.addGameObject(new Background(game, animBackGround));
		/* adiciona o jogador */
		player = new Player(game, 650);
		this.addGameObject(player);
		/* segue o jogador pela tela */
		this.addGameObject(new FollowObjectViewport(game, player));
		/* adiciona barra de energia do jogador */
		this.addGameObject(new EnergyBar(game, player, new Rectangle(10, 10, 300, 30)));
		/* adiciona o placar */
		this.addGameObject(new Score(game, 10, screenManager.getHeight() - 10));

		/* mapeia as ações do jogo */
		quit = new GameAction("Sair");
		moveLeft = new GameAction("Esquerda");
		moveRight = new GameAction("Direita");
		moveUp = new GameAction("Cima");
		moveDown = new GameAction("Baixo");
		fire = new GameAction("Tiro");
		pauseMusic = new GameAction("Parar música", GameAction.DETECT_INITAL_PRESS_ONLY);

		InputManager inputManager = game.getInputManager();
		inputManager.mapToKey(quit, KeyEvent.VK_ESCAPE);
		inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
		inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
		inputManager.mapToKey(moveUp, KeyEvent.VK_UP);
		inputManager.mapToKey(moveDown, KeyEvent.VK_DOWN);
		inputManager.mapToKey(fire, KeyEvent.VK_ENTER);
		inputManager.mapToKey(fire, KeyEvent.VK_SPACE);
		inputManager.mapToMouse(fire, InputManager.MOUSE_BUTTON_1);
		inputManager.mapToKey(pauseMusic, KeyEvent.VK_P);

		/* inicializa os pontos */
		game.setPontos(0);

		/* começa a música de fundo */
		if (game.getMidiSoundManager() != null) {
			try {
				game.getMidiSoundManager().playSequence(gameMusic, MidiSoundManager.LOOP_CONTINUOUSLY);
			} catch (InvalidMidiDataException e) {
				logger.error("Error with midi", e);
			}
		}

		GameObject fps = new ShowFPS(game, Color.YELLOW);
		fps.setX(game.getScreenManager().getWidth() - 60);
		addGameObject(fps);
	}

	public void checkInput(long elapsedTime) {
		float vx, vy;

		if (quit.isPressed()) {
			game.stop();
		}

		if (pauseMusic.isPressed()) {
			if (game.getMidiSoundManager().isSequencePlaying()) {
				game.getMidiSoundManager().stopSequence();
			} else {
				game.getMidiSoundManager().resumeSequence();
			}
		}

		vx = player.getVx();
		vy = player.getVy();
		if (moveLeft.isPressed()) {
			vx = (vx > -Player.MAX_ABS_VX) ? vx - Player.ACCELERATION : -Player.MAX_ABS_VX;
		}
		if (moveRight.isPressed()) {
			vx = (vx < Player.MAX_ABS_VX) ? vx + Player.ACCELERATION : Player.MAX_ABS_VX;
		}
		if (moveUp.isPressed()) {
			vy = (vy > -Player.MAX_ABS_VY) ? vy - Player.ACCELERATION : -Player.MAX_ABS_VY;
		}
		if (moveDown.isPressed()) {
			vy = (vy < Player.MAX_ABS_VY) ? vy + Player.ACCELERATION : Player.MAX_ABS_VY;
		}
		player.setVx(vx);
		player.setVy(vy);
		player.setFiring(fire.isPressed());
	}

	public void update(long elapsedTime) {

		checkInput(elapsedTime);

		Iterator<GameObject> iterator;
		GameObject actor;
		boolean inimigos, jogador;
		super.update(elapsedTime);
		/* se não há inimigos ou o jogador, mostra mensagem de fim de jogo */
		if (gameOver == null) {
			/* procura inimigos e jogador */
			jogador = false;
			inimigos = false;
			iterator = getObjects().iterator();
			while (iterator.hasNext()) {
				actor = iterator.next();
				if (actor instanceof Enemy) {
					inimigos = true;
				} else if (actor instanceof Player) {
					jogador = true;
				}
			}
			if (!jogador) {
				/* mostra mensagem de game-over */
				gameOver = new GameOver(game, "Acabou sua energia... tente de novo!");
				if (game.getSampledSoundManager() != null) {
					game.getSampledSoundManager().play(gameOverSound);
				}
			} else if (!inimigos) {
				/* mostra mensagem de game-over */
				gameOver = new GameOver(game, "Parabéns! A ameaça dos asteróides foi aniquilada!");
			}
			if (gameOver != null) {
				addGameObject(gameOver);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.Game#finish()
	 */
	public void finish() {
		super.finish();
		if (getObjects() != null) {
			logger.debug("Objetos: " + getObjects().size());
		}
	}
}