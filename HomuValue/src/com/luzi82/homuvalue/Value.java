package com.luzi82.homuvalue;


public abstract class Value<T> {

	public abstract T get();

	public static class Constant<T> extends Value<T> {
		private final T value;

		Constant(T t) {
			value = t;
		}

		@Override
		public T get() {
			return value;
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
	}

	public static <T> Variable<T> variable(T t) {
		Variable<T> ret = new Variable<T>();
		ret.set(t);
		return ret;
	}

}
