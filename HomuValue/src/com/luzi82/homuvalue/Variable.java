package com.luzi82.homuvalue;

public class Variable<T> extends Dynamic<T> {
	private T var;

	public void set(T t) {
		if (var == t)
			return;
		var = t;
		markDirty();
	}

	@Override
	public T update() {
		return var;
	}

}