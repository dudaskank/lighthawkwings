package com.lighthawkwings.object;

import java.util.ArrayList;
import java.util.Collection;

import com.lighthawkwings.Game;
import com.lighthawkwings.event.TransitionEvent;
import com.lighthawkwings.event.TransitionListener;

public abstract class AbstractTransition<G extends Game> extends AbstractGameObject<G> implements Transition {
	private Collection<TransitionListener> listeners;

	private long time;

	public AbstractTransition(G game, long time) {
		super(game);
		setX(0);
		setY(0);
		setW(game.getViewPort().width);
		setH(game.getViewPort().height);
		setZ(Float.MAX_VALUE);
		setTime(time);
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void update(long elapsedTime) {
		setTime(getTime() - elapsedTime);
		if (getTime() <= 0) {
			game.getStateManager().getActiveState().removeGameObject(this);
			/* gera o evento para os listeners */
			if (listeners != null) {
				TransitionEvent event = new TransitionEvent(this);
				for (TransitionListener listener : listeners) {
					listener.transitionFinish(event);
				}
				listeners.clear();
			}

		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.Manager#addManagerListener(com.lighthawkwings.core.events.ManagerListener)
	 */
	public void addTransitionListener(TransitionListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<TransitionListener>();
		}
		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.lighthawkwings.core.Transition#removeTransitionListener(com.lighthawkwings.core.events.TransitionListener)
	 */
	public void removeTransitionListener(TransitionListener listener) {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}
}