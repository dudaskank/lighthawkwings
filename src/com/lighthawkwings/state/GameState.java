package com.lighthawkwings.state;

import java.util.Collection;

import com.lighthawkwings.object.GameObject;

/**
 * <p>
 * Interface que deve ser implementada por um estado de jogo.
 * </p>
 *
 * <p>
 * A idéia é que cada opção no jogo tenha seu estado, como introdução, menu principal, opções, o jogo em si, e por
 * aí vai.
 * </p>
 *
 * @author Eduardo Oliveira
 *
 */
public interface GameState extends GameObject {
	/**
	 * Inicializa este estado de jogo.
	 */
	public void init();

	/**
	 * Finaliza este estado de jogo.
	 */
	public void finish();

	/**
	 * Adiciona um <code>GameObject</code> ao jogo. A adição é feita na próxima chamada ao método
	 * {@link #updateGameObjectsList()}.
	 *
	 * @param gameObject
	 *            Objeto que será adicionado ao jogo.
	 */
	public void addGameObject(GameObject gameObject);

	/**
	 * Retira um <code>GameObject</code> do jogo. A exclusão é feita na próxima chamada ao método
	 * {@link #updateGameObjectsList()}.
	 *
	 * @param gameObject
	 *            Objeto que será retirado do jogo.
	 */
	public void removeGameObject(GameObject gameObject);

	/**
	 * Retira todos os <code>GameObject</code> do jogo.
	 */
	public void clearGameObjects();

	/**
	 * @return Retorna a lista de objetos.
	 */
	public Collection<GameObject> getObjects();

	/**
	 * Atualiza a lista de objetos antes de iterar por ela, adicionando e removendo os objetos que estão na fila e
	 * ordenando tudo.
	 */
	public void updateGameObjectsList();

	public int getCurrentFps();
}
