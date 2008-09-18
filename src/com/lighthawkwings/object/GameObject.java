package com.lighthawkwings.object;

import java.awt.Graphics2D;

/**
 * <p>
 * Interface básica para todos os objetos que estão envolvidos no jogo e devem ser atualizados periodicamente.
 * </p>
 *
 * @author Eduardo Oliveira
 *
 */
public interface GameObject extends Comparable<GameObject> {
	/**
	 * Método utilizado para desenhar o objeto.
	 *
	 * @param g
	 *            Onde será desenhado.
	 */
	public void paint(Graphics2D g);

	/**
	 * Atualiza o objeto.
	 *
	 * @param elapsedTime
	 *            Tempo passado desde a última atualização, em ms.
	 */
	public void update(long elapsedTime);

	/**
	 * <p>
	 * Método para checar colisões com outros objetos no jogo.
	 * </p>
	 *
	 * @param other
	 *            O objeto que será verificado se colide com este.
	 * @return Retorna true caso os objetos estejam colidindo, false caso contrário.
	 */
	public boolean checkCollision(GameObject other);

	/**
	 * @return Retorna a altura.
	 */
	public float getH();

	/**
	 * @param h
	 *            Altera a altura.
	 */
	public void setH(float h);

	/**
	 * @return Retorna o comprimento do objeto.
	 */
	public float getW();

	/**
	 * @param w
	 *            Altera o comprimento do objeto.
	 */
	public void setW(float w);

	/**
	 * @return Retorna a posição no eixo x.
	 */
	public float getX();

	/**
	 * @param x
	 *            Altera a posição no eixo x.
	 */
	public void setX(float x);

	/**
	 * @return Retorna a posição no eixo y.
	 */
	public float getY();

	/**
	 * @param y
	 *            Altera a posição no eixo y.
	 */
	public void setY(float y);

	/**
	 * @return Retorna a posição no eixo z.
	 */
	public float getZ();

	/**
	 * @param z
	 *            Altera a posição no eixo z.
	 */
	public void setZ(float z);

	/**
	 * Método que é chamado quando o {@link GameState} é finalizado
	 */
	public void finish();
}