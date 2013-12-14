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
	}

	@Test
	public void sumVar() {
		Variable<Integer> i3 = Value.variable(3);
		Variable<Integer> i4 = Value.variable(4);

		Value<Integer> i7 = IntegerValue.sum(i3, i4);
		Assert.assertEquals((Integer) 7, i7.get());

		i3.set(9);

		Assert.assertEquals((Integer) 13, i7.get());
	}

}
