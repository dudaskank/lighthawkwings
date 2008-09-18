package com.lighthawkwings.event;

import java.util.EventObject;

import com.lighthawkwings.object.Transition;

public class TransitionEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	public TransitionEvent(Transition source) {
		super(source);
	}
}
