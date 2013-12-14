package com.luzi82.homuvalue;

public class IntegerValue {

	public static Value<Integer> sum(Value<Integer> a, Value<Integer> b) {
		return Value.constant(a.get() + b.get());
	}

	public static Value<Integer> sum(Value<Integer>[] values) {
		int ret = 0;
		for (Value<Integer> v : values) {
			ret += v.get();
		}
		return Value.constant(ret);
	}

}
