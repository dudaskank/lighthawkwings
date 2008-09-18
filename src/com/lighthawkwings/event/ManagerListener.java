package com.lighthawkwings.event;

import java.util.EventListener;

/**
 * <p>
 * O listener para os eventos de um manager.
 * </p>
 * 
 * @author eduardo.machado
 */
public interface ManagerListener extends EventListener {
	/**
	 * Disparado ap�s o manager ser iniciado.
	 * 
	 * @param event
	 */
	public void managerInit(ManagerEvent event);

	/**
	 * Disparado ap�s o manager ser finalizado.
	 * 
	 * @param event
	 */
	public void managerFinish(ManagerEvent event);
}
