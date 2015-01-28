package com.luzi82.homuvalue.obj;

public class TypeListVariable<O> extends AbstractListVariable<O, O> {

	@Override
	protected O toO(O i) {
		return i;
	}

	@Override
	protected O toI(O o) {
		return o;
	}

}
