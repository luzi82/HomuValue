package com.luzi82.homuvalue;

public abstract class Var<T> extends Dynamic<T> {

	public void set(T t) {
		if (remoteGet() == t)
			return;
		remoteSet(t);
		markDirty();
	}

	@Override
	public T update() {
		return remoteGet();
	}

	public abstract void remoteSet(T t);

	public abstract T remoteGet();

}