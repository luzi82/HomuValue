package com.luzi82.homuvalue;

import com.luzi82.homuvalue.Value.Listener;

@SuppressWarnings("rawtypes")
public abstract class Function<T> extends Dynamic<T> implements
		Value.Listener {
	public boolean isConst = true;

	@SuppressWarnings("unchecked")
	protected void addChild(Value v) {
		v.addListener(this);
		if (isConst && !v.isConstant) {
			isConst = false;
		}
	}

	@Override
	public void onValueDirty(Value v) {
		markDirty();
	}

	public Value<T> optimize() {
		if (isConst) {
			return Value.constant(get());
		} else {
			return this;
		}
	}
}