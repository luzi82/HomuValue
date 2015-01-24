package com.luzi82.homuvalue;


public abstract class Value<T> {

	public abstract T get();

	public abstract void addListener(Listener<T> listener);

	public abstract void removeListener(Listener<T> intListener);

	public final boolean isConstant;

	public Value(boolean isConstant) {
		this.isConstant = isConstant;
	}

	public abstract boolean dirty();

	public static <T> Constant<T> constant(T t) {
		Constant<T> ret = new Constant<T>(t);
		return ret;
	}

	public static <T> Variable<T> variable(T t) {
		Variable<T> ret = new Variable<T>();
		ret.set(t);
		return ret;
	}

	public static interface Listener<T> {

		public void onValueDirty(Value<T> v);

	}

	// slot

	public static <T> Slot<T> slot(T t) {
		return new Slot<T>(t);
	}

}
