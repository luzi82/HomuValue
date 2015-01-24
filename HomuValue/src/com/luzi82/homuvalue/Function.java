package com.luzi82.homuvalue;

public abstract class Function<T> extends Group<T> {
	public boolean isConst = true;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addChild(Value v) {
		v.addListener(this);
		if (isConst && !v.isConstant) {
			isConst = false;
		}
	}

	public Value<T> optimize() {
		if (isConst) {
			return Value.constant(get());
		} else {
			return this;
		}
	}
}