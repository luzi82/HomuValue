package com.luzi82.homuvalue.test;

import org.junit.Assert;
import org.junit.Test;

import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Slot;
import com.luzi82.homuvalue.Value.Variable;

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
	public void slot() {
		Slot<Integer> slot = Value.slot(100);
		Assert.assertEquals((Integer) 100, slot.get());

		slot.set(Value.constant(150));
		Assert.assertEquals((Integer) 150, slot.get());
	}

	@Test
	public void slotDirty() {
		Slot<Integer> slot = Value.slot(100);
		slot.get();
		Assert.assertFalse(slot.dirty());

		Variable<Integer> intV0 = Value.variable(200);
		slot.set(intV0);
		Assert.assertTrue(slot.dirty());

		slot.get();
		Assert.assertFalse(slot.dirty());

		intV0.set(300);
		Assert.assertTrue(slot.dirty());
	}

	@Test
	public void slotUndirty() {
		Slot<Integer> slot = Value.slot(100);

		Variable<Integer> intV0 = Value.variable(200);
		slot.set(intV0);

		slot.get();
		Assert.assertFalse(slot.dirty());

		Variable<Integer> intV1 = Value.variable(300);
		slot.set(intV1);

		slot.get();
		Assert.assertFalse(slot.dirty());

		intV0.set(400);
		Assert.assertFalse(slot.dirty());
	}

	@Test
	public void slotDefault() {
		Slot<Integer> slot = Value.slot(100);

		Variable<Integer> intV0 = Value.variable(200);
		slot.set(intV0);

		Assert.assertTrue(slot.dirty());
		Assert.assertEquals(200, (int) slot.get());
		Assert.assertFalse(slot.dirty());

		slot.set(null);
		Assert.assertTrue(slot.dirty());

		Assert.assertEquals(100, (int) slot.get());
		Assert.assertFalse(slot.dirty());

		intV0.set(400);
		Assert.assertFalse(slot.dirty());
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
