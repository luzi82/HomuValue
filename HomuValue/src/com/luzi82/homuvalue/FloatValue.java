package com.luzi82.homuvalue;

import com.luzi82.homuvalue.Value.Function;

public class FloatValue {

	// sum

	private static class Sum extends Function<Float> {

		Value<Float>[] values;

		public Sum(Value<Float>[] values) {
			this.values = values;
			for (Value<Float> v : values) {
				addChild(v);
			}
		}

		@Override
		public Float update() {
			float ret = 0;
			for (Value<Float> v : values) {
				ret += v.get();
			}
			return ret;
		}
	}

	@SuppressWarnings("unchecked")
	public static Value<Float> sum(Value<Float> a, Value<Float> b) {
		return sum(new Value[] { a, b });
	}

	public static Value<Float> sum(Value<Float>[] values) {
		return new Sum(values).optimize();
	}

	// negative

	private static class Negative extends Function<Float> {

		final Value<Float> v;

		public Negative(Value<Float> v) {
			this.v = v;
			addChild(v);
		}

		@Override
		public Float update() {
			return -v.get();
		}
	}

	public static Value<Float> negative(Value<Float> v) {
		return new Negative(v).optimize();
	}

	// product

	@SuppressWarnings("unchecked")
	public static Value<Float> product(Value<Float> a, Value<Float> b) {
		return product(new Value[] { a, b });
	}

	public static Value<Float> product(Value<Float>[] values) {
		return new Product(values).optimize();
	}

	private static class Product extends Function<Float> {

		Value<Float>[] values;

		public Product(Value<Float>[] values) {
			this.values = values;
			for (Value<Float> v : values) {
				addChild(v);
			}
		}

		@Override
		public Float update() {
			float ret = 1;
			for (Value<Float> v : values) {
				ret *= v.get();
				if (ret == 0)
					return ret;
			}
			return ret;
		}

		@Override
		public Value<Float> optimize() {
			for (Value<Float> v : values) {
				if (v.isConstant && v.get() == 0) {
					return Value.constant(0f);
				}
			}
			return super.optimize();
		}
	}

	// div

	public static Value<Float> div(Value<Float> a, Value<Float> b) {
		return new Div(a, b).optimize();
	}

	private static class Div extends Function<Float> {

		Value<Float> a;
		Value<Float> b;

		public Div(Value<Float> a, Value<Float> b) {
			this.a = a;
			this.b = b;
			addChild(a);
			addChild(b);
		}

		@Override
		public Float update() {
			return a.get() / b.get();
		}

		@Override
		public Value<Float> optimize() {
			if (a.isConstant && a.get() == 0) {
				return Value.constant(0f);
			}
			return super.optimize();
		}
	}

	// min

	public static Value<Float> min(Value<Float> a, Value<Float> b) {
		return new Min(a, b).optimize();
	}

	private static class Min extends Function<Float> {

		Value<Float> a;
		Value<Float> b;

		public Min(Value<Float> a, Value<Float> b) {
			this.a = a;
			this.b = b;
			addChild(a);
			addChild(b);
		}

		@Override
		public Float update() {
			return Math.min(a.get(), b.get());
		}
	}

	// max

	public static Value<Float> max(Value<Float> a, Value<Float> b) {
		return new Max(a, b).optimize();
	}

	private static class Max extends Function<Float> {

		Value<Float> a;
		Value<Float> b;

		public Max(Value<Float> a, Value<Float> b) {
			this.a = a;
			this.b = b;
			addChild(a);
			addChild(b);
		}

		@Override
		public Float update() {
			return Math.max(a.get(), b.get());
		}
	}
}
