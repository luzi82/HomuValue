package com.luzi82.homuvalue;

public class Value<T> {

	T constant;

	public T get() {
		return constant;
	}

	public static <T> Value<T> constant(T i) {
		Value<T> ret = new Value<T>();
		ret.constant = i;
		return ret;
	}

}
