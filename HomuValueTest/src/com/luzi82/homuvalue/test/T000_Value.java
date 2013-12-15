package com.luzi82.homuvalue.test;

import org.junit.Assert;
import org.junit.Test;

import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Slot;

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

		intValue.set(200);

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

	@Test
	public void dirtyListener() {
		Value.Variable<Integer> intValue = Value.variable(100);

		TestListener<Integer> intListener = new TestListener<Integer>();
		intValue.addListener(intListener);

		intValue.get();

		intValue.set(200);

		Assert.assertSame(intValue, intListener.v);

		intListener.v = null;

		intValue.set(300);

		Assert.assertSame(null, intListener.v);

		intValue.get();

		intValue.set(400);

		Assert.assertSame(intValue, intListener.v);
	}

	@Test
	public void sameNoDirty() {
		Value.Variable<Integer> intVar = Value.variable(100);
		Assert.assertTrue(intVar.dirty());

		intVar.get();
		Assert.assertFalse(intVar.dirty());

		intVar.set(100);
		Assert.assertFalse(intVar.dirty());
	}
	
	@Test
	public void slot(){
		Slot<Integer> slot = Value.slot(100);
		Assert.assertEquals((Integer) 100, slot.get());
		
		slot.set(Value.constant(150));
		Assert.assertEquals((Integer) 150, slot.get());
	}
	
	@Test
	public void removeListener() {
		Value.Variable<Integer> intValue = Value.variable(100);

		intValue.get();

		TestListener<Integer> intListener = new TestListener<Integer>();
		intValue.addListener(intListener);

		intValue.set(200);

		Assert.assertSame(intValue, intListener.v);

		intListener.v = null;
		
		intValue.get();
		
		intValue.removeListener(intListener);

		intValue.set(300);

		Assert.assertNull(intListener.v);

	}

}
