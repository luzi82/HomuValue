package com.luzi82.homuvalue;


@SuppressWarnings("rawtypes")
public abstract class Group<V> extends Dynamic<V> implements Value.Listener {

	@Override
	public void onValueDirty(Value v) {
		markDirty();
	}

}
