package com.lighthawkwings.state;

import java.util.Collection;

import com.lighthawkwings.object.GameObject;

/**
 * <p>
 * Interface que deve ser implementada por um estado de jogo.
 * </p>
 *
 * <p>
 * A id�ia � que cada op��o no jogo tenha seu estado, como introdu��o, menu principal, op��es, o jogo em si, e por
 * a� vai.
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
	 * Adiciona um <code>GameObject</code> ao jogo. A adi��o � feita na pr�xima chamada ao m�todo
	 * {@link #updateGameObjectsList()}.
	 *
	 * @param gameObject
	 *            Objeto que ser� adicionado ao jogo.
	 */
	public void addGameObject(GameObject gameObject);

	/**
	 * Retira um <code>GameObject</code> do jogo. A exclus�o � feita na pr�xima chamada ao m�todo
	 * {@link #updateGameObjectsList()}.
	 *
	 * @param gameObject
	 *            Objeto que ser� retirado do jogo.
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
	 * Atualiza a lista de objetos antes de iterar por ela, adicionando e removendo os objetos que est�o na fila e
	 * ordenando tudo.
	 */
	public void updateGameObjectsList();

	public int getCurrentFps();
}
