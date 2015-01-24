package com.luzi82.homuvalue;

public abstract class Variable<T> extends Dynamic<T> {

	public void set(T t) {
		if (variableGet() == t)
			return;
		variableSet(t);
		markDirty();
	}

	@Override
	public T update() {
		return variableGet();
	}

	protected abstract void variableSet(T t);

	protected abstract T variableGet();

}