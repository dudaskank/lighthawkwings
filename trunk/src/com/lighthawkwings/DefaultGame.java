package com.lighthawkwings;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import com.lighthawkwings.graphic.DefaultScreenManager;
import com.lighthawkwings.graphic.ScreenManager;
import com.lighthawkwings.input.DefaultInputManager;
import com.lighthawkwings.input.InputManager;
import com.lighthawkwings.sound.midi.DefaultMidiSoundManager;
import com.lighthawkwings.sound.midi.MidiSoundManager;
import com.lighthawkwings.sound.sampled.DefaultSampledSoundManager;
import com.lighthawkwings.sound.sampled.SampledSoundManager;
import com.lighthawkwings.state.DefaultStateManager;
import com.lighthawkwings.state.GameState;
import com.lighthawkwings.state.StateManager;
import com.lighthawkwings.timer.Clock;
import com.lighthawkwings.timer.NanoClock;

/**
 * <p>
 * Classe abstrata principal da API de jogos. Ela já instancia algumas classes
 * necessárias para o funcionamento dos jogos, que são os gerenciadores de
 * vídeo, recursos, teclado e sons.
 * </p>
 * <p>
 * Deve-se criar uma sub-classe desta alterando e acrescentando o que for
 * necessário para o jogo.
 * </p>
 *
 * @author Eduardo "Dudaskank" Oliveira
 */
public class DefaultGame implements Game {
	/** Nome do jogo */
	protected String gameName = "Game";

	/** Versão do jogo */
	protected String gameVersion = "1.0";

	/** Flag indicando se o jogo está rodando ou não */
	protected boolean running;

	/** Gerenciador de recursos */
	protected ResourceManager resourceManager;

	/** Relógio interno do jogo */
	protected Clock clock;

	/** Comment for <code>oldTime</code> */
	protected long oldTime;

	/** Comment for <code>viewPort</code> */
	protected Rectangle viewPort;

	/**
	 * Gerenciador da entrada, para ajudar na leitura do estado das teclas e do
	 * mouse
	 */
	protected InputManager inputManager;

	/**
	 * Gerenciador da tela, para ajudar na criação da janela de exibição e
	 * exibição dos gráficos
	 */
	protected ScreenManager screenManager;

	/** Gerenciador de midis */
	protected MidiSoundManager midiSoundManager;

	/** Gerenciador de samples */
	protected SampledSoundManager sampledSoundManager;

	/** Gerenciador de estados do jogo */
	protected StateManager stateManager;

	/** Propriedades relativas ao jogo */
	protected GameProperties properties;

	protected Logger logger = Logger.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#run()
	 */
	public void run(GameState initialState) {
		try {
			init();
			stateManager.setNextState(initialState);
			gameLoop();
		} catch (Exception e) {
			logger.error("Error running game of class " + getClass().getName(),
					e);
		} finally {
			finish();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#stop()
	 */
	public void stop() {
		this.running = false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#init()
	 */
	public void init() {
		createGameProperties();
		createScreenManager();
		createSoundManager();
		createResourceManager();
		createInputManager();
		createStateManager();
		createClock(50.0f);
		running = true;
		setViewPort(new Rectangle(new Dimension(screenManager.getWidth(),
				screenManager.getHeight())));
	}

	private void createGameProperties() {
		logger.debug("Setting defaults properties");
		GameProperties defaults = new GameProperties();
		defaults.setProperty(ScreenManager.ANTIALIASED_PROPERTY, "true");
		logger.debug("Defaults properties ok");
		properties = new GameProperties(defaults);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#finish()
	 */
	public void finish() {
		stateManager.finish();
		if (clock != null) {
			logger.debug("Sytem time: " + System.currentTimeMillis());
			logger.debug("Clock time: " + clock.getCurrentTime());
			logger.debug("Diff: "
					+ (System.currentTimeMillis() - clock.getCurrentTime()));
			clock.stop();
		}
		screenManager.finish();
		if (midiSoundManager != null) {
			midiSoundManager.finish();
		}
		if (sampledSoundManager != null) {
			sampledSoundManager.finish();
		}
		inputManager.finish();
		resourceManager.finish();

		OutputStream out = new ByteArrayOutputStream();
		properties.list(new PrintStream(out));
		logger.debug(out.toString());
		properties.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#gameLoop()
	 */
	public void gameLoop() {
		Graphics2D graphics;
		long newTime;
		long deltaT;
		GameState activeState;

		if (oldTime == -1) {
			oldTime = clock.getCurrentTime();
		}

		// roda a lógica para atualizar o clock do jogo
		while (running) {
			clock.update();
			// roda a lógica para atualizar o clock do jogo
			newTime = clock.getCurrentTime();
			if (oldTime != newTime) {
				/* calcula a diferença de tempo e atualiza o oldTime */
				deltaT = newTime - oldTime;
				oldTime = newTime;

				/* pega o estado atual do jogo */
				stateManager.updateState();
				activeState = stateManager.getActiveState();

				/*
				 * adiciona e remove os objetos que se encontram nas listas de
				 * espera
				 */
				activeState.updateGameObjectsList();
				/* atualiza o estado do jogo */
				activeState.update(deltaT);

				/* desenha o frame */
				graphics = screenManager.getDrawGraphics();
				activeState.paint(graphics);
				graphics.dispose();
				screenManager.showBuffer();
			}
			clock.updateAndSleep();
		}
	}

	/**
	 * Cria o relógio interno do jogo.
	 */
	protected void createClock(float fps) {
		logger.debug(String.format("Starting clock at %.2f fps", fps));
		logger.debug(((int) 1000 / fps) * fps);

		clock = new NanoClock(fps);

		if (clock != null) {
			clock.start();
			oldTime = -1;
		}
	}

	/**
	 * Cria a instância do gerenciador de recursos utilizado pelo jogo.
	 */
	protected void createResourceManager() {
		resourceManager = new DefaultResourceManager(this);
		resourceManager.init();
	}

	/**
	 * Cria a instância do gerenciador de vídeo utilizado pelo jogo.
	 */
	protected void createScreenManager() {
		screenManager = new DefaultScreenManager(this);
		screenManager.init(640, 480, 32, 0, false);
		screenManager.getScreen().setCursor(ScreenManager.INVISIBLE_CURSOR);
	}

	/**
	 * Cria a instância dos gerenciadores de sons utilizado pelo jogo (midi e
	 * sample).
	 */
	protected void createSoundManager() {
		midiSoundManager = new DefaultMidiSoundManager();
		midiSoundManager.init();
		sampledSoundManager = new DefaultSampledSoundManager();
		sampledSoundManager.init();
	}

	/**
	 * Cria a instância do gerenciador de entradas utilizado no jogo.
	 */
	protected void createInputManager() {
		inputManager = new DefaultInputManager(screenManager.getScreen());
		inputManager.init();
	}

	/**
	 * Cria a instância do gerenciador de estados do jogo.
	 */
	protected void createStateManager() {
		stateManager = new DefaultStateManager(this);
		stateManager.init();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#getResourceManager()
	 */
	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#getInputManager()
	 */
	public InputManager getInputManager() {
		return inputManager;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#getScreenManager()
	 */
	public ScreenManager getScreenManager() {
		return screenManager;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#getMidiSoundManager()
	 */
	public MidiSoundManager getMidiSoundManager() {
		return midiSoundManager;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#getSampledSoundManager()
	 */
	public SampledSoundManager getSampledSoundManager() {
		return sampledSoundManager;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#getStateManager()
	 */
	public StateManager getStateManager() {
		return stateManager;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#getClock()
	 */
	public Clock getClock() {
		return clock;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#getViewPort()
	 */
	public Rectangle getViewPort() {
		return this.viewPort;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.Game#setViewPort(java.awt.Rectangle)
	 */
	public void setViewPort(Rectangle viewPort) {
		if (this.viewPort == null) {
			this.viewPort = new Rectangle();
		}
		this.viewPort.setRect(viewPort);
	}

	@Override
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
		if (getScreenManager() != null) {
			getScreenManager().setTitle(gameName);
		}
	}

	public GameProperties getProperties() {
		return properties;
	}

	@Override
	public String getGameVersion() {
		return gameVersion;
	}

	public void setGameVersion(String gameVersion) {
		this.gameVersion = gameVersion;
	}
}