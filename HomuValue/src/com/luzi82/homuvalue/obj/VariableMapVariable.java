package com.luzi82.homuvalue.obj;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.luzi82.homuvalue.AbstractVariable;
import com.luzi82.homuvalue.Value;

public class VariableMapVariable<K, I extends AbstractVariable<O>, O> extends AbstractMapVariable<K, I, O> {

	private final Constructor<I> mConstructor;

	public VariableMapVariable(Constructor<I> c) {
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
		return ret;
	}

	@Override
	protected void innerClear() {
		Map<K, I> tmp = (Map<K, I>) mMap.clone();
		super.innerClear();
		for (I i : tmp.values()) {
			i.removeListener(mListener);
		}
	}

	@Override
	protected void innerPut(K key, I value) {
		value.addListener(mListener);
		super.innerPut(key, value);
	}

	@Override
	protected I innerRemove(Object key) {
		I ret = super.innerRemove(key);
		if (ret != null) {
			ret.removeListener(mListener);
		}
		return ret;
	}

}
