package com.luzi82.homuvalue.obj;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.luzi82.homuvalue.AbstractVariable;
import com.luzi82.homuvalue.Value;

public class ObjectVariable extends AbstractVariable<Map<String, Object>> {

	protected HashMap<String, Field> mFieldMap = new HashMap<String, Field>();

	@SuppressWarnings("unchecked")
	protected void addField(Field aField) {
		mFieldMap.put(aField.getName(), aField);
		aField.addListener(mListener);
	}

	@SuppressWarnings("rawtypes")
	private Listener mListener = new Listener() {
		@Override
		public void onValueDirty(Value v) {
			ObjectVariable.this.markDirty();
		}
	};

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void variableSet(Map<String, Object> t) {
		for (Field f : mFieldMap.values()) {
			String k = f.getName();
			f.input(t.get(k));
		}
	}

	@Override
	protected Map<String, Object> variableGet() {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		for (Field f : mFieldMap.values()) {
			ret.put(f.getName(), f.output());
		}
		return ret;
	}

	public abstract static class Field<I, O> extends AbstractVariable<I> {

		protected final String mName;

		protected I mObj;

		public Field(String aName) {
			mName = aName;
		}

		public String getName() {
			return mName;
		}

		@Override
		protected void variableSet(I t) {
			mObj = t;
		}

		@Override
		protected I variableGet() {
			return mObj;
		}

		public abstract void input(O o);

		public abstract O output();

	}

	public static class ObjectField<O> extends Field<O, O> {

		public ObjectField(String aName) {
			super(aName);
		}

		@Override
		public void input(O o) {
			set(o);
		}

		@Override
		public O output() {
			return get();
		}

	}

	public static class VarField<I extends AbstractVariable<O>, O> extends Field<I, O> {

		private final Constructor<I> mConstructor;

		public VarField(String aName, Constructor<I> aConstructor) {
			super(aName);
			mConstructor = aConstructor;
		}

		public VarField(String aName, Class<I> aClass) {
			super(aName);
			try {
				mConstructor = aClass.getConstructor();
			} catch (NoSuchMethodException e) {
				throw new Error(e);
			}
		}

		@Override
		public void input(O o) {
			if (mObj != null) {
				mObj.removeListener(mListener);
			}
			try {
				mObj = mConstructor.newInstance();
			} catch (Exception e) {
				throw new Error(e);
			}
			if (mObj != null) {
				mObj.addListener(mListener);
			}
		}

		@Override
		public O output() {
			I i = get();
			if (i == null) {
				return null;
			}
			return i.get();
		}

		private Listener<O> mListener = new Listener<O>() {
			@Override
			public void onValueDirty(Value<O> v) {
				VarField.this.markDirty();
			}
		};

		@Override
		protected void variableSet(I t) {
			if (mObj != null) {
				mObj.removeListener(mListener);
			}
			super.variableSet(t);
			if (mObj != null) {
				mObj.addListener(mListener);
			}
		}

	}

}
