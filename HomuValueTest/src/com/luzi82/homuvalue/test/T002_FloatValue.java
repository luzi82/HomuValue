package com.luzi82.homuvalue.test;

import org.junit.Assert;
import org.junit.Test;

import com.luzi82.homuvalue.Constant;
import com.luzi82.homuvalue.FloatValue;
import com.luzi82.homuvalue.LocalVariable;
import com.luzi82.homuvalue.Value;

public class T002_FloatValue {

	@Test
	public void sum() {
		Value<Float> i3 = new Constant(3f);
		Value<Float> i4 = new Constant(4f);

		Value<Float> i7 = FloatValue.sum(i3, i4);
		Assert.assertEquals(7f, i7.get(), .0001f);

		@SuppressWarnings("unchecked")
		Value<Float> i10 = FloatValue.sum(new Value[] { i3, i3, i4 });
		Assert.assertEquals(10f, i10.get(), .0001f);

		Assert.assertTrue(i10.isConstant());
	}

	@Test
	public void sumVar() {
		LocalVariable<Float> i3 = new LocalVariable(3f);
		LocalVariable<Float> i4 = new LocalVariable(4f);

		Value<Float> i7 = FloatValue.sum(i3, i4);
		Assert.assertEquals(7f, i7.get(), .0001f);

		Assert.assertFalse(i7.isConstant());

		i3.set(9f);

		Assert.assertEquals(13f, i7.get(), .0001f);
	}

	@Test
	public void netgative() {
		Value<Float> c = new Constant(3f);
		Value<Float> nc = FloatValue.negative(c);

		Assert.assertEquals(-3f, nc.get(), .0001f);
		Assert.assertTrue(nc.isConstant());

		LocalVariable<Float> v = new LocalVariable(4f);
		Value<Float> nv = FloatValue.negative(v);

		Assert.assertTrue(!nv.isConstant());

		Assert.assertEquals(-4f, nv.get(), .0001f);

		v.set(5f);

		Assert.assertEquals(-5f, nv.get(), .0001f);
	}

	@Test
	public void product() {
		Value<Float> c0 = new Constant(2f);
		Value<Float> c1 = new Constant(3f);
		Value<Float> c2 = new Constant(4f);

		Value<Float> ret = FloatValue.product(c0, c1);
		Assert.assertEquals(6f, ret.get(), .0001f);
		Assert.assertTrue(ret.isConstant());

		ret = FloatValue.product(new Value[] { c0, c1, c2 });
		Assert.assertEquals(24f, ret.get(), .0001f);
		Assert.assertTrue(ret.isConstant());

		LocalVariable<Float> v0 = new LocalVariable(2f);
		LocalVariable<Float> v1 = new LocalVariable(3f);
		LocalVariable<Float> v2 = new LocalVariable(5f);
		ret = FloatValue.product(v0, v1);

		Assert.assertTrue(!ret.isConstant());
		Assert.assertEquals(6f, ret.get(), .0001f);

		v0.set(5f);

		Assert.assertEquals(15f, ret.get(), .0001f);

		v0.set(2f);
		v1.set(3f);
		v2.set(5f);

		ret = FloatValue.product(new Value[] { v0, v1, v2 });

		Assert.assertTrue(!ret.isConstant());
		Assert.assertEquals(30f, ret.get(), .0001f);

		v0.set(5f);

		Assert.assertEquals(75f, ret.get(), .0001f);
	}

	@Test
	public void productOpt() {
		Value<Float> v0 = new LocalVariable(2f);
		Value<Float> v1 = new LocalVariable(3f);
		Value<Float> c2 = new Constant(0f);

		Value<Float> ret = FloatValue.product(new Value[] { v0, v1, c2 });
		Assert.assertEquals(0f, ret.get(), .0001f);
		Assert.assertTrue(ret.isConstant());
	}

	@Test
	public void div() {
		Value<Float> ret;

		LocalVariable<Float> v0 = new LocalVariable(3f);
		LocalVariable<Float> v1 = new LocalVariable(2f);
		ret = FloatValue.div(v0, v1);
		Assert.assertTrue(!ret.isConstant());
		Assert.assertEquals(1.5f, ret.get(), 0.0001);

		v0.set(10f);
		Assert.assertEquals(5f, ret.get(), 0.0001);

		v1.set(3f);
		Assert.assertEquals(3.33333f, ret.get(), 0.0001);
	}

	@Test
	public void divConst() {
		Value<Float> ret;

		Value<Float> v0 = new Constant(3f);
		Value<Float> v1 = new Constant(2f);
		ret = FloatValue.div(v0, v1);
		Assert.assertTrue(ret.isConstant());
		Assert.assertEquals(1.5f, ret.get(), 0.0001);
	}

	@Test
	public void divZero() {
		Value<Float> ret;

		Value<Float> v0 = new Constant(0f);
		Value<Float> v1 = new LocalVariable(2f);
		ret = FloatValue.div(v0, v1);
		Assert.assertTrue(ret.isConstant());
		Assert.assertEquals(0f, ret.get(), 0.0001);

		v0 = new LocalVariable(0f);
		v1 = new LocalVariable(2f);
		ret = FloatValue.div(v0, v1);
		Assert.assertTrue(!ret.isConstant());
		Assert.assertEquals(0f, ret.get(), 0.0001);
	}

	@Test
	public void min() {
		Value<Float> ret;

		LocalVariable<Float> v0 = new LocalVariable(3f);
		LocalVariable<Float> v1 = new LocalVariable(2f);
		ret = FloatValue.min(v0, v1);
		Assert.assertTrue(!ret.isConstant());
		Assert.assertEquals((Float) 2f, ret.get());

		v0.set(1f);
		Assert.assertEquals((Float) 1f, ret.get());

		v1.set(-1f);
		Assert.assertEquals((Float) (-1f), ret.get());
	}

	@Test
	public void max() {
		Value<Float> ret;

		LocalVariable<Float> v0 = new LocalVariable(3f);
		LocalVariable<Float> v1 = new LocalVariable(2f);
		ret = FloatValue.max(v0, v1);
		Assert.assertTrue(!ret.isConstant());
		Assert.assertEquals((Float) 3f, ret.get());

		v0.set(1f);
		Assert.assertEquals((Float) 2f, ret.get());

		v1.set(-1f);
		Assert.assertEquals((Float) 1f, ret.get());
	}

	@Test
	public void minMax() {
		Value<Float> ret;

		LocalVariable<Float> v0 = new LocalVariable(0f);
		LocalVariable<Float> v1 = new LocalVariable(1f);
		LocalVariable<Float> v2 = new LocalVariable(2f);
		ret = FloatValue.minMax(v0, v1, v2);
		Assert.assertEquals((Float) 1f, ret.get());

		v1.set(-1f);
		Assert.assertEquals((Float) 0f, ret.get());

		v1.set(3f);
		Assert.assertEquals((Float) 2f, ret.get());

		v0.set(2f);
		v1.set(1f);
		v2.set(0f);
		Assert.assertEquals((Float) 1f, ret.get());
	}

	@Test
	public void linear(){
		Value<Float> ret;
		
		LocalVariable<Float> u0 = new LocalVariable(10f);
		LocalVariable<Float> u1 = new LocalVariable(11f);
		LocalVariable<Float> v0 = new LocalVariable(1000f);
		LocalVariable<Float> v1 = new LocalVariable(1100f);
		LocalVariable<Float> t = new LocalVariable(10.5f);

		ret = FloatValue.linear(u0,u1,v0,v1,t);
		Assert.assertEquals(1050f, ret.get(),.0001);
		
		t.set(10.8f);
		Assert.assertEquals(1080f, ret.get(),.0001);
	}
	
}
