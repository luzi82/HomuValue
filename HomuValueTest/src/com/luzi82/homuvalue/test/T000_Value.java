package com.luzi82.homuvalue.test;

import org.junit.Assert;
import org.junit.Test;

import com.luzi82.homuvalue.Value;

public class T000_Value {

	@Test
	public void simpleGet() {
		Value<Integer> intValue = Value.constant(100);

		Assert.assertEquals((Integer) 100, intValue.get());
		Assert.assertFalse(intValue.dirty());
	}

	@Test
	public void variable() {
		Value.Variable<Integer> intValue = Value.variable(100);

		Assert.assertEquals((Integer) 100, intValue.get());

		TestListener<Integer> intListener = new TestListener<Integer>();
		intValue.addListener(intListener);

		intValue.set(200);

		Assert.assertSame(intValue, intListener.v);
		Assert.assertTrue(intValue.dirty());

		Assert.assertEquals((Integer) 200, intValue.get());
		Assert.assertFalse(intValue.dirty());
	}

}
