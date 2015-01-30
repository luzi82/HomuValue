package com.luzi82.homuvalue.obj;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import com.luzi82.homuvalue.AbstractVariable;
import com.luzi82.homuvalue.Value;

public class VariableListVariable<I extends AbstractVariable<O>, O> extends AbstractListVariable<I, O> {

	private final Constructor<I> mConstructor;

	public VariableListVariable(Constructor<I> c) {
		this.mConstructor = c;
	}

	private Listener<O> mListener = new Listener<O>() {
		@Override
		public void onValueDirty(Value<O> v) {
			mDirty.set(true);
		}
	};

	@Override
	protected O toO(I i) {
		return i.get();
	}

	@Override
	protected I toI(O o) {
		I ret = null;
		try {
			ret = mConstructor.newInstance();
		} catch (InstantiationException e) {
			throw new Error(e);
		} catch (IllegalAccessException e) {
			throw new Error(e);
		} catch (IllegalArgumentException e) {
			throw new Error(e);
		} catch (InvocationTargetException e) {
			throw new Error(e);
		}
		ret.set(o);
		ret.addListener(mListener);
		return ret;
	}

	protected void beforeAdd(I e) {
		e.addListener(mListener);
		super.beforeAdd(e);
	}

	protected void afterRemove(I e) {
		super.afterRemove(e);
		e.removeListener(mListener);
	}

}
