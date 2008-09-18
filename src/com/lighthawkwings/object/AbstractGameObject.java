package com.lighthawkwings.object;

import com.lighthawkwings.Game;

/**
 * <p>
 * Classe abstrata que ser� extendida para adicionar as funcionalidades necess�rias ao jogo, como por exemplo uma
 * imagem para o plano de fundo, os NPCs do jogo e o personagem do pr�prio jogador.
 * </p>
 *
 * @author Eduardo "Dudaskank" M. Oliveira
 */
public abstract class AbstractGameObject<G extends Game> implements GameObject {
	/**
	 * Implementa��o da classe <code>Game</code> onde o <code>GameObject</code> est�
	 */
	protected G game;

	/**
	 * Posi��o no eixo x
	 */
	protected float x;

	/**
	 * Posi��o no eixo y
	 */
	protected float y;

	/**
	 * Comprimento do objeto
	 */
	protected float w;

	/**
	 * Altura do objeto
	 */
	protected float h;

	/**
	 * Posi��o no eixo z
	 */
	protected float z;

	/**
	 * Construtor da classe.
	 *
	 * @param game
	 *            Inst�ncia de <code>Game</code> onde o objeto estar�
	 */
	public AbstractGameObject(G game) {
		this.game = game;
	}

	/**
	 * <p>
	 * M�todo padr�o para checar colis�es com outros objetos no jogo.
	 * </p>
	 * <p>
	 * Sua utiliza��o depende do tipo do objeto. Por exemplo, um objeto que represente um inimigo ir� usar ou
	 * sobrescrever este m�todo, enquanto que um objeto usado apenas para mostrar informa��es n�o ir� precisar
	 * utiliz�-lo.
	 * </p>
	 *
	 * @param other
	 *            O objeto que ser� verificado se colide com este.
	 * @return Retorna true caso os objetos estejam colidindo, false caso contr�rio.
	 */
	public boolean checkCollision(GameObject other) {
		/* se s�o o mesmo objeto, retorna falso */
		if (this == other) {
			return false;
		}
		/* l� a posi��o de cada objeto */
		float x1 = getX();
		float y1 = getY();
		float x2 = other.getX();
		float y2 = other.getY();
		/* verifica a colis�o */
		return (x1 < x2 + other.getW() && x2 < x1 + getW() && y1 < y2 + other.getH() && y2 < y1 + getH());
	}

	/**
	 * <p>
	 * Implementa��o do m�todo compareTo usado para ordenar os objetos.
	 * </p>
	 * <p>
	 * Esta implementa��o ordena pelo valor da posi��o no eixo z, com os maiores em cima.
	 * </p>
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(GameObject o) {
		return (int) Math.signum(this.z - o.getZ());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.GameObject#getH()
	 */
	public float getH() {
		return this.h;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.GameObject#setH(float)
	 */
	public void setH(float h) {
		this.h = h;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.GameObject#getW()
	 */
	public float getW() {
		return this.w;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.GameObject#setW(float)
	 */
	public void setW(float w) {
		this.w = w;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.GameObject#getX()
	 */
	public float getX() {
		return this.x;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.GameObject#setX(float)
	 */
	public void setX(float x) {
		this.x = x;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.GameObject#getY()
	 */
	public float getY() {
		return this.y;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.GameObject#setY(float)
	 */
	public void setY(float y) {
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.GameObject#getZ()
	 */
	public float getZ() {
		return this.z;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.GameObject#setZ(float)
	 */
	public void setZ(float z) {
		this.z = z;
	}

	public void finish() {
		// nada pro padr�o
	}
}