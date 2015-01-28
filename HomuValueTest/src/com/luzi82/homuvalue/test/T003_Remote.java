package com.luzi82.homuvalue.test;

import org.junit.Assert;
import org.junit.Test;

import com.luzi82.homuvalue.AbstractVariable;
import com.luzi82.homuvalue.RemoteGroup;

public class T003_Remote {

	@Test
	public void t0() {
		class I {
			public int i = 0;
		}

		class T extends RemoteGroup<I> {
			public final AbstractVariable<Integer> i;

			public T(I aV) {
				super(aV);
				i = new AbstractVariable<Integer>() {
					@Override
					protected void variableSet(Integer t) {
						iV.i = t;
					}

					@Override
					protected Integer variableGet() {
						return iV.i;
					}
				};
				addChild(i);
			}
		}

		I i = new I();
		T t = new T(i);
		t.get();

		TestListener<I> l = new TestListener<I>();
		t.addListener(l);

		t.i.set(1);

		Assert.assertNotNull(l.v);
	}

	@Test
	public void t1() {
		final int[] i = { 0 };

		AbstractVariable<Integer> v = new AbstractVariable<Integer>() {
			@Override
			protected void variableSet(Integer t) {
				i[0] = t;
			}

			@Override
			protected Integer variableGet() {
				return i[0];
			}
		};
		v.get();

		TestListener<Integer> l = new TestListener<Integer>();
		v.addListener(l);

		v.set(1);

		Assert.assertEquals((Integer) 1, l.v.get());
	}

	@Test
	public void t2() {
		final int[] i = { 0 };
		final int[] j = { 0 };
		Assert.assertNotEquals(i.hashCode(), j.hashCode());
		Assert.assertEquals(System.identityHashCode(i), i.hashCode());
		Assert.assertEquals(System.identityHashCode(j), j.hashCode());

		class C {
			public int i = 0;
		}
		C c0 = new C();
		C c1 = new C();
		Assert.assertFalse(c0.equals(c1));
		Assert.assertNotEquals(c0.hashCode(), c1.hashCode());
		Assert.assertEquals(System.identityHashCode(c0), c0.hashCode());
		Assert.assertEquals(System.identityHashCode(c1), c1.hashCode());
	}
}
