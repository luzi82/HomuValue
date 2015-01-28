package com.luzi82.homuvalue.obj;

import java.util.HashMap;
import java.util.Map;

import com.luzi82.homuvalue.Dirty;

public abstract class AbstractMapVariable<K, I, O> extends HashMap<K, I> implements MapVariable<K, I, O> {

	protected final Dirty<Map<K, O>> mDirty;

	public AbstractMapVariable() {
		mDirty = new Dirty<Map<K, O>>(this);
	}

	@Override
	public void addListener(Listener<Map<K, O>> listener) {
		mDirty.addListener(listener);
	}

	@Override
	public void removeListener(Listener<Map<K, O>> listener) {
		mDirty.removeListener(listener);
	}

	@Override
	public boolean isConstant() {
		return false;
	}

	@Override
	public boolean dirty() {
		return mDirty.get();
	}

	@Override
	public Map<K, O> get() {
		HashMap<K, O> ret = new HashMap<K, O>();
		for (Map.Entry<K, I> me : entrySet()) {
			ret.put(me.getKey(), toO(me.getValue()));
		}
		mDirty.set(false);
		return ret;
	}

	protected abstract O toO(I i);

	@Override
	public void set(Map<K, O> t) {
		super.clear();
		for (Map.Entry<K, O> me : t.entrySet()) {
			super.put(me.getKey(), toI(me.getValue()));
		}
		mDirty.set(true);
	}

	protected abstract I toI(O o);

	@Override
	public void clear() {
		super.clear();
		mDirty.set(true);
	}

	@Override
	public I put(K key, I value) {
		I ret = super.put(key, value);
		mDirty.set(true);
		return ret;
	}

	@Override
	public void putAll(Map<? extends K, ? extends I> m) {
		super.putAll(m);
		mDirty.set(true);
	}

	@Override
	public I remove(Object key) {
		I ret = super.remove(key);
		mDirty.set(true);
		return ret;
	}

	//

}
