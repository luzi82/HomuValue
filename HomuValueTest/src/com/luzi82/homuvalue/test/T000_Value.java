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

}
