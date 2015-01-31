package com.luzi82.homuvalue.test;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.luzi82.common.Factory;
import com.luzi82.homuvalue.obj.ObjectListVariable;
import com.luzi82.homuvalue.obj.ObjectMapVariable;
import com.luzi82.homuvalue.obj.ObjectVariable;
import com.luzi82.homuvalue.obj.VariableListVariable;
import com.luzi82.homuvalue.obj.VariableMapVariable;

public class T004_Hv {

	public static class O extends ObjectVariable {
		public ObjectVariable.ObjectField<Integer> i = new ObjectVariable.ObjectField<Integer>("i");

		public O() {
			addField(i);
		}
	}

	@Test
	public void t0() {
		O o = new O();
		o.i.set(1);

		Map<String, Object> m = o.get();
		Assert.assertEquals(1, m.get("i"));

		TestListener tl = new TestListener();
		o.addListener(tl);

		o.i.set(2);

		Assert.assertEquals(o, tl.v);

		o.get();
		tl.v = null;

		o.i.set(2);

		Assert.assertEquals(null, tl.v);
	}

	@Test
	public void t1() {

		class OO extends ObjectVariable {
			public ObjectVariable.VarField<O, Map<String, Object>> j = new ObjectVariable.VarField<O, Map<String, Object>>("j", Factory.C.create(O.class));

			public OO() {
				addField(j);
			}
		}

		OO oo = new OO();
		oo.j.set(new O());
		oo.j.get().i.set(1);

		Map<String, Object> mm = oo.get();
		Map<String, Object> m = (Map<String, Object>) mm.get("j");
		Assert.assertEquals(1, m.get("i"));

		TestListener tl = new TestListener();
		oo.addListener(tl);

		oo.j.get().i.set(2);

		Assert.assertEquals(oo, tl.v);

		oo.get();
		tl.v = null;

		oo.j.get().i.set(2);

		Assert.assertEquals(null, tl.v);

		oo.j.set(new O());

		Assert.assertEquals(oo, tl.v);
	}

	@Test
	public void t20() {
		ObjectListVariable<Integer> i = new ObjectListVariable<Integer>();

		List<Integer> l = i.get();
		Assert.assertEquals(0, l.size());

		TestListener tl = new TestListener();
		i.addListener(tl);

		i.add(42);

		Assert.assertEquals(i, tl.v);

		l = i.get();
		Assert.assertEquals(1, l.size());
		Assert.assertEquals(42, (int) l.get(0));
	}

	@Test
	public void t21() {
		ObjectListVariable<String> i = new ObjectListVariable<String>();

		List<String> l = i.get();
		Assert.assertEquals(0, l.size());

		TestListener tl = new TestListener();
		i.addListener(tl);

		i.add("asdf");

		Assert.assertEquals(i, tl.v);

		l = i.get();
		Assert.assertEquals(1, l.size());
		Assert.assertEquals("asdf", l.get(0));
	}

	@Test
	public void t3() {
		final Constructor[] constructor = new Constructor[1];
		try {
			constructor[0] = O.class.getConstructor();
		} catch (Exception e) {
			throw new Error(e);
		}

		VariableListVariable<O, Map<String, Object>> i = new VariableListVariable<O, Map<String, Object>>(constructor[0]);

		List<Map<String, Object>> l = i.get();
		Assert.assertEquals(0, l.size());

		TestListener tl = new TestListener();
		i.addListener(tl);

		O o = new O();
		o.i.set(42);
		i.add(o);

		Assert.assertEquals(i, tl.v);

		l = i.get();
		Assert.assertEquals(1, l.size());
		Assert.assertEquals(42, l.get(0).get("i"));
	}

	public void t4() {
		ObjectMapVariable<String, Integer> v = new ObjectMapVariable<String, Integer>();

		Map<String, Integer> m = v.get();
		Assert.assertEquals(0, m.size());

		TestListener tl = new TestListener();
		v.addListener(tl);

		v.put("a", 42);

		Assert.assertEquals(v, tl.v);

		m = v.get();
		Assert.assertEquals(1, m.size());
		Assert.assertEquals(42, (int) m.get("a"));
	}

	@Test
	public void t5() {
		VariableMapVariable<String, O, Map<String, Object>> i = new VariableMapVariable<String, O, Map<String, Object>>(Factory.C.create(O.class));

		Map<String, Map<String, Object>> l = i.get();
		Assert.assertEquals(0, l.size());

		TestListener tl = new TestListener();
		i.addListener(tl);

		O o = new O();
		o.i.set(42);
		i.put("a", o);

		Assert.assertEquals(i, tl.v);

		l = i.get();
		Assert.assertEquals(1, l.size());
		Assert.assertEquals(42, l.get("a").get("i"));
	}

}
