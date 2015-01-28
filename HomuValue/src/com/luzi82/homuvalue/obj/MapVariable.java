package com.luzi82.homuvalue.obj;

import java.util.Map;

import com.luzi82.homuvalue.Variable;

public abstract interface MapVariable<K, I, O> extends Variable<Map<K, O>>, Map<K,I> {

}
