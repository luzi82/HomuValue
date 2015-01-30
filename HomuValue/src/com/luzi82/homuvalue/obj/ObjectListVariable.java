package com.luzi82.homuvalue.obj;


public class ObjectListVariable<O> extends AbstractListVariable<O, O> {

	@Override
	protected O toO(O i) {
		return i;
	}

	@Override
	protected O toI(O o) {
		return o;
	}

}
