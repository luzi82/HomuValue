package com.luzi82.homuvalue;

import com.luzi82.homuvalue.Value.Listener;

public class Slot<T> extends Dynamic<T> implements Value.Listener {
	private Value<T> v;
	private T def;

	public Slot(T t) {
		this.def = t;
	}

	public void set(Value<T> v) {
		if (this.v == v)
			return;
		if (dirty()) {
			changeV(v);
			return;
		}
		if (v == null) {
			if (get() != def) {
				markDirty();
			}
			changeV(v);
			return;
		}
		if (v.dirty()) {
			markDirty();
			changeV(v);
			return;
		}
		T oldV = get();
		T newV = v.get();
		if (oldV != newV) {
			markDirty();
			changeV(v);
			return;
		}
		changeV(v);
	}

	private void changeV(Value<T> v) {
		if (this.v != null)
			this.v.removeListener(this);
		this.v = v;
		if (this.v != null)
			this.v.addListener(this);
	}

	@Override
	public T update() {
		if (v == null) {
			return def;
		}
		return v.get();
	}

	@Override
	public void onValueDirty(Value v) {
		markDirty();
	}
}