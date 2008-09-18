package com.lighthawkwings;

import java.awt.Rectangle;

import com.lighthawkwings.graphic.ScreenManager;
import com.lighthawkwings.input.InputManager;
import com.lighthawkwings.sound.midi.MidiSoundManager;
import com.lighthawkwings.sound.sampled.SampledSoundManager;
import com.lighthawkwings.state.GameState;
import com.lighthawkwings.state.StateManager;
import com.lighthawkwings.timer.Clock;

/**
 * <p>
 * Interface principal da API de jogos. Ela já instancia algumas classes
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
public interface Game {
	/**
	 * Começa a rodar o jogo a partir do estado passado
	 * @param initialState
	 */
	public void run(GameState initialState);

	/**
	 * Para o jogo
	 */
	public void stop();

	/**
	 * Inicializa o jogo
	 */
	public void init();

	/**
	 * Chamado quando o jogo termina pelo {@link Game#run(GameState)}.
	 */
	public void finish();

	/**
	 * <p>
	 * Loop principal do jogo.
	 * </p>
	 * <p>
	 * Este método calcula o tempo que se passou desde a última iteração com o
	 * jogo, checa a entrada do usuário e atualiza os objetos do jogo.
	 * </p>
	 */
	public void gameLoop();

	/**
	 * @return Retorna o gerenciador de recursos usado no jogo.
	 */
	public ResourceManager getResourceManager();

	/**
	 * @return Retorna o gerenciador de entrada usado no jogo.
	 */
	public InputManager getInputManager();

	/**
	 * @return Retorna o gerenciador de tela usado no jogo.
	 */
	public ScreenManager getScreenManager();

	/**
	 * @return Retorna o gerenciador de midis.
	 */
	public MidiSoundManager getMidiSoundManager();

	/**
	 * @return Retorna o gerenciador de samples.
	 */
	public SampledSoundManager getSampledSoundManager();

	/**
	 * @return Retorna o gerenciador de estados do jogo.
	 */
	public StateManager getStateManager();

	/**
	 * @return Retorna o relógio interno do jogo.
	 */
	public Clock getClock();

	/**
	 * @return Retorna uma cópia do retângulo que define o campo de visão do
	 *         jogo.
	 */
	public Rectangle getViewPort();

	/**
	 * @param viewPort
	 *            Copia o <code>viewPort</code> passado para o campo de visão
	 *            do jogo.
	 */
	public void setViewPort(Rectangle viewPort);

	/**
	 * @return Retorna as propriedades do jogo
	 */
	public GameProperties getProperties();

	/**
	 * @return Nome do jogo
	 */
	public String getGameName();

	/**
	 * @return Versão do jogo
	 */
	public String getGameVersion();
}