package com.lighthawkwings.object;

import java.awt.Graphics2D;

/**
 * <p>
 * Interface b�sica para todos os objetos que est�o envolvidos no jogo e devem ser atualizados periodicamente.
 * </p>
 *
 * @author Eduardo Oliveira
 *
 */
public interface GameObject extends Comparable<GameObject> {
	/**
	 * M�todo utilizado para desenhar o objeto.
	 *
	 * @param g
	 *            Onde ser� desenhado.
	 */
	public void paint(Graphics2D g);

	/**
	 * Atualiza o objeto.
	 *
	 * @param elapsedTime
	 *            Tempo passado desde a �ltima atualiza��o, em ms.
	 */
	public void update(long elapsedTime);

	/**
	 * <p>
	 * M�todo para checar colis�es com outros objetos no jogo.
	 * </p>
	 *
	 * @param other
	 *            O objeto que ser� verificado se colide com este.
	 * @return Retorna true caso os objetos estejam colidindo, false caso contr�rio.
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
	 * @return Retorna a posi��o no eixo x.
	 */
	public float getX();

	/**
	 * @param x
	 *            Altera a posi��o no eixo x.
	 */
	public void setX(float x);

	/**
	 * @return Retorna a posi��o no eixo y.
	 */
	public float getY();

	/**
	 * @param y
	 *            Altera a posi��o no eixo y.
	 */
	public void setY(float y);

	/**
	 * @return Retorna a posi��o no eixo z.
	 */
	public float getZ();

	/**
	 * @param z
	 *            Altera a posi��o no eixo z.
	 */
	public void setZ(float z);

	/**
	 * M�todo que � chamado quando o {@link GameState} � finalizado
	 */
	public void finish();
}