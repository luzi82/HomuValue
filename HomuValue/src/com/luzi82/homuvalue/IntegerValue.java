package com.luzi82.homuvalue;

import com.luzi82.homuvalue.Value.Function;

public class IntegerValue {

	private static class IntegerSum extends Function<Integer> {

		Value<Integer>[] values;

		public IntegerSum(Value<Integer>[] values) {
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
		return new IntegerSum(values).optimize();
	}

}
