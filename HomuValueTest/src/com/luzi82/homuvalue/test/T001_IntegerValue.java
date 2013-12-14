package com.luzi82.homuvalue.test;

import org.junit.Assert;
import org.junit.Test;

import com.luzi82.homuvalue.IntegerValue;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Variable;

public class T001_IntegerValue {

	@Test
	public void sum() {
		Value<Integer> i3 = Value.constant(3);
		Value<Integer> i4 = Value.constant(4);

		Value<Integer> i7 = IntegerValue.sum(i3, i4);
		Assert.assertEquals((Integer) 7, i7.get());

		@SuppressWarnings("unchecked")
		Value<Integer> i10 = IntegerValue.sum(new Value[] { i3, i3, i4 });
		Assert.assertEquals((Integer) 10, i10.get());

		Assert.assertTrue(i10.isConstant);
	}

	@Test
	public void sumVar() {
		Variable<Integer> i3 = Value.variable(3);
		Variable<Integer> i4 = Value.variable(4);

		Value<Integer> i7 = IntegerValue.sum(i3, i4);
		Assert.assertEquals((Integer) 7, i7.get());

		Assert.assertFalse(i7.isConstant);

		i3.set(9);

		Assert.assertEquals((Integer) 13, i7.get());
	}

	@Test
	public void netgative() {
		Value<Integer> c = Value.constant(3);
		Value<Integer> nc = IntegerValue.negative(c);

		Assert.assertEquals((Integer) (-3), nc.get());
		Assert.assertTrue(nc.isConstant);

		Variable<Integer> v = Value.variable(4);
		Value<Integer> nv = IntegerValue.negative(v);

		Assert.assertTrue(!nv.isConstant);

		Assert.assertEquals((Integer) (-4), nv.get());

		v.set(5);

		Assert.assertEquals((Integer) (-5), nv.get());
	}

	@Test
	public void product() {
		Value<Integer> c0 = Value.constant(2);
		Value<Integer> c1 = Value.constant(3);
		Value<Integer> c2 = Value.constant(4);

		Value<Integer> ret = IntegerValue.product(c0, c1);
		Assert.assertEquals((Integer) (6), ret.get());
		Assert.assertTrue(ret.isConstant);

		ret = IntegerValue.product(new Value[] { c0, c1, c2 });
		Assert.assertEquals((Integer) 24, ret.get());
		Assert.assertTrue(ret.isConstant);

		Variable<Integer> v0 = Value.variable(2);
		Variable<Integer> v1 = Value.variable(3);
		Variable<Integer> v2 = Value.variable(5);
		ret = IntegerValue.product(v0, v1);

		Assert.assertTrue(!ret.isConstant);
		Assert.assertEquals((Integer) (6), ret.get());

		v0.set(5);

		Assert.assertEquals((Integer) (15), ret.get());

		v0.set(2);
		v1.set(3);
		v2.set(5);

		ret = IntegerValue.product(new Value[] { v0, v1, v2 });

		Assert.assertTrue(!ret.isConstant);
		Assert.assertEquals((Integer) (30), ret.get());

		v0.set(5);

		Assert.assertEquals((Integer) (75), ret.get());
	}

	@Test
	public void productOpt() {
		Value<Integer> v0 = Value.variable(2);
		Value<Integer> v1 = Value.variable(3);
		Value<Integer> c2 = Value.constant(0);

		Value<Integer> ret = IntegerValue.product(new Value[] { v0, v1, c2 });
		Assert.assertEquals((Integer) 0, ret.get());
		Assert.assertTrue(ret.isConstant);
	}

}
