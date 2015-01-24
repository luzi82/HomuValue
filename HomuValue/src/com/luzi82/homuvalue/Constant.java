package com.luzi82.homuvalue;

import com.luzi82.homuvalue.Value.Listener;

public class Constant<T> extends Value<T> {
	private final T value;

	Constant(T t) {
		super(true);
		value = t;
	}

	@Override
	public final T get() {
		return value;
	}

	@Override
	public final void addListener(Value.Listener<T> listener) {
		// do nothing
	}

	@Override
	public void removeListener(Value.Listener<T> intListener) {
		// do nothing
	}

	@Override
	public boolean dirty() {
		return false;
	}

}