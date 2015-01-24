package com.luzi82.homuvalue;

public class LocalVariable<T> extends Variable<T> {
	private T var;

	@Override
	protected void variableSet(T t) {
		var = t;
	}

	@Override
	protected T variableGet() {
		return var;
	}

}