package com.luzi82.homuvalue;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class Dirty<T> {

	private boolean dirty;
	private LinkedList<WeakReference<Value.Listener<T>>> list;
	private final Value<T> iParent;

	public Dirty(Value<T> aParent) {
		iParent = aParent;
		dirty = true;
		list = new LinkedList<WeakReference<Value.Listener<T>>>();
	}

	public void set(boolean v) {
		if (dirty == v)
			return;
		dirty = v;
		if (!v)
			return;
		LinkedList<WeakReference<AbstractValue.Listener<T>>> deadList = new LinkedList<WeakReference<AbstractValue.Listener<T>>>();
		for (WeakReference<AbstractValue.Listener<T>> ref : list) {
			AbstractValue.Listener<T> l = ref.get();
			if (l == null) {
				deadList.add(ref);
			} else {
				l.onValueDirty(iParent);
			}
		}
		list.removeAll(deadList);
	}

	public boolean get() {
		return dirty;
	}

	public void addListener(Value.Listener<T> listener) {
		list.add(new WeakReference<Value.Listener<T>>(listener));
	}

	public void removeListener(AbstractValue.Listener<T> listener) {
		WeakReference<AbstractValue.Listener<T>> target = null;
		LinkedList<WeakReference<AbstractValue.Listener<T>>> deadList = new LinkedList<WeakReference<AbstractValue.Listener<T>>>();
		for (WeakReference<AbstractValue.Listener<T>> ref : list) {
			AbstractValue.Listener<T> l = ref.get();
			if (l == null) {
				deadList.add(ref);
			} else if (l == listener) {
				target = ref;
			}
		}
		list.removeAll(deadList);
		list.remove(target);
	}
}
