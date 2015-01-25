package com.luzi82.homuvalue;

public class RemoteGroup<V> extends Group<V> {

	protected final V iV;

	public RemoteGroup(V aV) {
		iV = aV;
	}

	@Override
	public V update() {
		for (Value v : childSet) {
			v.get();
		}
		return iV;
	}

}
