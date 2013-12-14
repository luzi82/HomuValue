package com.luzi82.homuvalue;

import com.luzi82.homuvalue.Value.Listener;

public abstract class Value<T> {

	public abstract T get();

	public abstract void addListener(Listener<Integer> intListener);

	public static class Constant<T> extends Value<T> {
		private final T value;

		Constant(T t) {
			value = t;
		}

		@Override
		public T get() {
			return value;
		}

		@Override
		public void addListener(Listener<Integer> intListener) {
			// do nothing
		}
	}

	public static <T> Constant<T> constant(T t) {
		Constant<T> ret = new Constant<T>(t);
		return ret;
	}
	
	public static class Variable<T> extends Value<T> {
		private T value;

		public void set(T t) {
			value = t;
		}

		@Override
		public T get() {
			return value;
		}

		@Override
		public void addListener(Listener<Integer> intListener) {
			// TODO
		}

	}

	public static <T> Variable<T> variable(T t) {
		Variable<T> ret = new Variable<T>();
		ret.set(t);
		return ret;
	}

	public static interface Listener<T> {

		public void onValueDirty(Value<T> v);

	}

}
