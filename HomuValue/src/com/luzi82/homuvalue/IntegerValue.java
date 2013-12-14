package com.luzi82.homuvalue;

import com.luzi82.homuvalue.Value.Dynamic;

public class IntegerValue {

	private static class IntegerSum extends Dynamic<Integer> implements
			com.luzi82.homuvalue.Value.Listener<Integer> {

		Value<Integer>[] values;

		public IntegerSum(Value<Integer>[] values) {
			this.values = values;
			for (Value<Integer> v : values) {
				v.addListener(this);
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

		@Override
		public void onValueDirty(Value<Integer> v) {
			markDirty();
		}
	}

	@SuppressWarnings("unchecked")
	public static Value<Integer> sum(Value<Integer> a, Value<Integer> b) {
		return sum(new Value[] { a, b });
	}

	public static Value<Integer> sum(Value<Integer>[] values) {
		return new IntegerSum(values);
	}

}
