package com.luzi82.homuvalue.test;

import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Listener;

public class TestListener<T> implements Listener<T> {

	public Value<T> v;

	@Override
	public void onValueDirty(Value<T> v) {
		this.v = v;
	}

}
