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

		TestListener<Integer> intListener = new TestListener<Integer>();
		intValue.addListener(intListener);

		intValue.set(200);

		Assert.assertSame(intValue, intListener.v);

		Assert.assertEquals((Integer) 200, intValue.get());
	}

}
