package com.luzi82.homuvalue.test;

import org.junit.Assert;
import org.junit.Test;

import com.luzi82.homuvalue.Value;

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

	@Test
	public void dirty() {
		Value<Integer> intValue = Value.constant(100);
		Assert.assertFalse(intValue.dirty());

		Value.Variable<Integer> intVar = Value.variable(100);
		Assert.assertTrue(intVar.dirty());

		intVar.get();
		Assert.assertFalse(intVar.dirty());

		intVar.set(200);
		Assert.assertTrue(intVar.dirty());

		intVar.get();
		Assert.assertFalse(intVar.dirty());
	}
}
