package com.lighthawkwings;

import com.lighthawkwings.event.ManagerListener;

/**
 * <p>
 * A interface Manager é usada para especificar todos os Managers usandos pelo framework.
 * </p>
 * 
 * @author eduardo.machado
 */
public interface Manager {
	/**
	 * Inicializa o manager.
	 */
	public void init();

	/**
	 * Finaliza o manager.
	 */
	public void finish();

	/**
	 * Adiciona um listener para os eventos gerados pelo manager.
	 * 
	 * @param listener
	 */
	public void addManagerListener(ManagerListener listener);

	/**
	 * Remove um listener para os eventos gerados pelo manager.
	 * 
	 * @param listener
	 */
	public void removeManagerListener(ManagerListener listener);
}
