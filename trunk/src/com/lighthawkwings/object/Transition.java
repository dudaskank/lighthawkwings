package com.lighthawkwings.object;

import com.lighthawkwings.event.TransitionListener;

public interface Transition {
	public static final float TRANSITION_Z = 1000.0f;
	/**
	 * Adiciona um listener para os eventos gerados pelo manager.
	 *
	 * @param listener
	 */
	public void addTransitionListener(TransitionListener listener);

	/**
	 * Remove um listener para os eventos gerados pelo Transition.
	 *
	 * @param listener
	 */
	public void removeTransitionListener(TransitionListener listener);
}