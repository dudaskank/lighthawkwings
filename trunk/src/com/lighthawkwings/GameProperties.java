package com.lighthawkwings;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GameProperties extends Properties {
	private static final long serialVersionUID = 6154591878343358132L;

	PropertyChangeSupport propertyChangeSupport;

	Logger logger;

	public GameProperties() {
		this(null);
	}

	public GameProperties(GameProperties defaults) {
		super(defaults);
		propertyChangeSupport = new PropertyChangeSupport(this);
		logger = Logger.getLogger(getClass());
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	@Override
	public synchronized Object put(Object key, Object value) {
		Object original, r;
		original = get(key);
		r = super.put(key, value);
		propertyChangeSupport.firePropertyChange((String) key, original, value);
		logger.debug("Property " + key + " changed from " + original + " to " + value);
		return r;
	}

	public Boolean getBoolean(String key) {
		String value;
		value = getProperty(key);
		if (value == null) {
			value = "false";
		}
		return Boolean.valueOf(value);
	}
}
