package com.luzi82.homuvalue;

import com.luzi82.homuvalue.Value.Function;

public class IntegerValue {

	// sum

	private static class Sum extends Function<Integer> {

		Value<Integer>[] values;

		public Sum(Value<Integer>[] values) {
			this.values = values;
			for (Value<Integer> v : values) {
				addChild(v);
			}
		}

		@Override
		public Integer update() {
			int ret = 0;
			for (Value<Integer> v : values) {
				ret += v.get();
			}
			return ret;
		}
	}

	@SuppressWarnings("unchecked")
	public static Value<Integer> sum(Value<Integer> a, Value<Integer> b) {
		return sum(new Value[] { a, b });
	}

	public static Value<Integer> sum(Value<Integer>[] values) {
		return new Sum(values).optimize();
	}

	// negative

	private static class Negative extends Function<Integer> {

		final Value<Integer> v;

		public Negative(Value<Integer> v) {
			this.v = v;
			addChild(v);
		}

		@Override
		public Integer update() {
			return -v.get();
		}
	}

	public static Value<Integer> negative(Value<Integer> v) {
		return new Negative(v).optimize();
	}

}
