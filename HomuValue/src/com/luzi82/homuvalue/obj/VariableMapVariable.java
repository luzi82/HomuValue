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
		ret.addListener(mListener);
		return ret;
	}

	@Override
	public void clear() {
		for (I i : values()) {
			i.removeListener(mListener);
		}
		super.clear();
	}

	@Override
	public I put(K key, I value) {
		value.addListener(mListener);
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends I> m) {
		for (I i : m.values()) {
			i.addListener(mListener);
		}
		super.putAll(m);
	}

	@Override
	public I remove(Object key) {
		I ret = super.remove(key);
		ret.removeListener(mListener);
		return ret;
	}

	//

}
