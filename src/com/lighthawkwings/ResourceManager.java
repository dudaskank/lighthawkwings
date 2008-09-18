package com.lighthawkwings;

import java.awt.Image;

import javax.sound.midi.Sequence;

import com.lighthawkwings.sound.sampled.Sound;

/**
 * <p>
 * Gerencia os recursos utilizados na aplica��o.
 * </p>
 * 
 * @author Eduardo Oliveira
 */
public interface ResourceManager extends Manager {
	/**
	 * <p>
	 * Carrega uma imagem de um arquivo.
	 * </p>
	 * 
	 * @param fileName
	 *            Nome do arquivo
	 * @return A imagem carregada, ou null se n�o conseguir carregar.
	 */
	public Image loadImage(String fileName);

	/**
	 * <p>
	 * Carrega um midi de um arquivo.
	 * </p>
	 * 
	 * @param fileName
	 *            Nome do arquivo
	 * @return O midi carregado, ou null se n�o conseguiu carregar.
	 */
	public Sequence loadSequence(String fileName);

	/**
	 * <p>
	 * Carrega um som de um arquivo.
	 * </p>
	 * 
	 * @param fileName
	 *            Nome do arquivo
	 * @return O sample carregado, ou null se n�o conseguiu carregar.
	 */
	public Sound loadSound(String fileName);

	/**
	 * <p>
	 * Libera recursos utilizados por determinado objeto.
	 * </p>
	 * 
	 * @param name
	 *            Recurso a ser liberado.
	 */
	public void unloadResource(Object name);

	/**
	 * <p>
	 * Libera todos os recursos utilizados pela aplica��o.
	 * </p>
	 */
	public void unloadAllResources();
}