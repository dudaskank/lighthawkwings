package com.lighthawkwings;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.lighthawkwings.event.ManagerEvent;
import com.lighthawkwings.event.ManagerListener;

public abstract class AbstractManager implements Manager {
	private Collection<ManagerListener> listeners;

	protected Logger logger = Logger.getLogger(getClass());

	/* (non-Javadoc)
	 * @see com.lighthawkwings.core.Manager#init()
	 */
	public void init() {
		logger.debug("Initializing manager of class " + getClass().getName());
		/* gera o evento para os listeners */
		if (listeners != null) {
			ManagerEvent event = new ManagerEvent(this);
			for (ManagerListener listener : listeners) {
				listener.managerFinish(event);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.lighthawkwings.core.Manager#finish()
	 */
	public void finish() {
		logger.debug("Finishing manager of class " + getClass().getName());
		/* gera o evento para os listeners */
		if (listeners != null) {
			ManagerEvent event = new ManagerEvent(this);
			for (ManagerListener listener : listeners) {
				listener.managerFinish(event);
			}
			listeners.clear();
		}
	}

	/* (non-Javadoc)
	 * @see com.lighthawkwings.core.Manager#addManagerListener(com.lighthawkwings.core.events.ManagerListener)
	 */
	public void addManagerListener(ManagerListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<ManagerListener>();
		}
		listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see com.lighthawkwings.core.Manager#removeManagerListener(com.lighthawkwings.core.events.ManagerListener)
	 */
	public void removeManagerListener(ManagerListener listener) {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}
}
