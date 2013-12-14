package com.luzi82.homuvalue.test;

import org.junit.Assert;
import org.junit.Test;

import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Listener;

public class T000_Value {

	@Test
	public void simpleGet() {
		Value<Integer> intValue = Value.constant(100);

		Assert.assertEquals((Integer) 100, intValue.get());
	}

	@Test
	public void variable() {
		Value.Variable<Integer> intValue = Value.variable(100);

		Assert.assertEquals((Integer) 100, intValue.get());

		@SuppressWarnings("unchecked")
		final Value<Integer>[] dirty = new Value[] { null };
		Listener<Integer> intListener = new Listener<Integer>() {
			@Override
			public void onValueDirty(Value<Integer> v) {
				dirty[0] = v;
			}
		};
		intValue.addListener(intListener);

		intValue.set(200);

		Assert.assertSame(intValue, dirty[0]);

		Assert.assertEquals((Integer) 200, intValue.get());
	}

}
