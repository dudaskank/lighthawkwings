package com.lighthawkwings.event;

import java.util.EventListener;

/**
 * <p>
 * O listener para os eventos de um transition.
 * </p>
 *
 * @author eduardo.machado
 */
public interface TransitionListener extends EventListener {
	/**
	 * Disparado ap�s o transition ser iniciado.
	 *
	 * @param event
	 */
	public void transitionInit(TransitionEvent event);

	/**
	 * Disparado ap�s o transition ser finalizado.
	 *
	 * @param event
	 */
	public void transitionFinish(TransitionEvent event);
}
