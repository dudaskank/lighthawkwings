package com.lighthawkwings.event;

import java.util.EventObject;

import com.lighthawkwings.Manager;

public class ManagerEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	public ManagerEvent(Manager source) {
		super(source);
	}
}
