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

	// product

	@SuppressWarnings("unchecked")
	public static Value<Integer> product(Value<Integer> a, Value<Integer> b) {
		return product(new Value[] { a, b });
	}

	public static Value<Integer> product(Value<Integer>[] values) {
		return new Product(values).optimize();
	}

	private static class Product extends Function<Integer> {

		Value<Integer>[] values;

		public Product(Value<Integer>[] values) {
			this.values = values;
			for (Value<Integer> v : values) {
				addChild(v);
			}
		}

		@Override
		public Integer update() {
			int ret = 1;
			for (Value<Integer> v : values) {
				ret *= v.get();
				if (ret == 0)
					return ret;
			}
			return ret;
		}

		@Override
		public Value<Integer> optimize() {
			for (Value<Integer> v : values) {
				if (v.isConstant && v.get() == 0) {
					return Value.constant(0);
				}
			}
			return super.optimize();
		}
	}
}
