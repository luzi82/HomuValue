package com.luzi82.homuvalue;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public abstract class Dynamic<T> extends Value<T> {
	private T value;
	private boolean dirty;
	private LinkedList<WeakReference<Value.Listener<T>>> list;

	public abstract T update();

	public Dynamic() {
		super(false);
		dirty = true;
		list = new LinkedList<WeakReference<Value.Listener<T>>>();
	}

	protected final void markDirty() {
		if (dirty)
			return;
		dirty = true;

		LinkedList<WeakReference<Value.Listener<T>>> deadList = new LinkedList<WeakReference<Value.Listener<T>>>();
		for (WeakReference<Value.Listener<T>> ref : list) {
			Value.Listener<T> l = ref.get();
			if (l == null) {
				deadList.add(ref);
			} else {
				l.onValueDirty(this);
			}
		}
		list.removeAll(deadList);
	}

	@Override
	public final T get() {
		if (dirty) {
			value = update();
			dirty = false;
		}
		return value;
	}

	@Override
	public final void addListener(Value.Listener<T> listener) {
		list.add(new WeakReference<Value.Listener<T>>(listener));
	}

	@Override
	public final void removeListener(Value.Listener<T> listener) {
		WeakReference<Value.Listener<T>> target = null;
		LinkedList<WeakReference<Value.Listener<T>>> deadList = new LinkedList<WeakReference<Value.Listener<T>>>();
		for (WeakReference<Value.Listener<T>> ref : list) {
			Value.Listener<T> l = ref.get();
			if (l == null) {
				deadList.add(ref);
			} else if (l == listener) {
				target = ref;
			}
		}
		list.removeAll(deadList);
		list.remove(target);
	}

	@Override
	public boolean dirty() {
		return dirty;
	}
}