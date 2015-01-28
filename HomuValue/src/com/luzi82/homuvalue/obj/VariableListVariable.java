package com.luzi82.homuvalue.obj;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import com.luzi82.homuvalue.AbstractVariable;
import com.luzi82.homuvalue.Value;

public class VariableListVariable<I extends AbstractVariable<O>, O> extends AbstractListVariable<I, O> {

	private final Constructor<I> mConstructor;

	public VariableListVariable(Constructor<I> c) {
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

	//

	@Override
	public boolean add(I e) {
		e.addListener(mListener);
		return super.add(e);
	}

	@Override
	public void add(int index, I element) {
		element.addListener(mListener);
		super.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends I> c) {
		for (I i : c) {
			i.addListener(mListener);
		}
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends I> c) {
		for (I i : c) {
			i.addListener(mListener);
		}
		return super.addAll(index, c);
	}

	@Override
	public void addFirst(I e) {
		e.addListener(mListener);
		super.addFirst(e);
	}

	@Override
	public void addLast(I e) {
		e.addListener(mListener);
		super.addLast(e);
	}

	@Override
	public void clear() {
		for (I i : this) {
			i.removeListener(mListener);
		}
		super.clear();
	}

	@Override
	public boolean offer(I e) {
		e.addListener(mListener);
		return super.offer(e);
	}

	@Override
	public boolean offerFirst(I e) {
		e.addListener(mListener);
		return super.offerFirst(e);
	}

	@Override
	public boolean offerLast(I e) {
		e.addListener(mListener);
		return super.offerLast(e);
	}

	@Override
	public I poll() {
		I ret = super.poll();
		if (ret != null) {
			ret.removeListener(mListener);
		}
		return ret;
	}

	@Override
	public I pollFirst() {
		I ret = super.pollFirst();
		if (ret != null) {
			ret.removeListener(mListener);
		}
		return ret;
	}

	@Override
	public I pollLast() {
		I ret = super.pollLast();
		if (ret != null) {
			ret.removeListener(mListener);
		}
		return ret;
	}

	@Override
	public I pop() {
		I ret = super.pop();
		if (ret != null) {
			ret.removeListener(mListener);
		}
		return ret;
	}

	@Override
	public void push(I e) {
		e.addListener(mListener);
		super.push(e);
	}

	@Override
	public I remove() {
		I ret = super.remove();
		if (ret != null) {
			ret.removeListener(mListener);
		}
		return ret;
	}

	@Override
	public I remove(int index) {
		I ret = super.remove(index);
		if (ret != null) {
			ret.removeListener(mListener);
		}
		return ret;
	}

	@Override
	public boolean remove(Object o) {
		if (!contains(o))
			return false;
		Value<O> i = (Value<O>) o;
		i.removeListener(mListener);
		return super.remove(o);
	}

	@Override
	public I removeFirst() {
		I ret = super.removeFirst();
		if (ret != null) {
			ret.removeListener(mListener);
		}
		return ret;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		if (!contains(o))
			return false;
		Value<O> i = (Value<O>) o;
		i.removeListener(mListener);
		return super.removeFirstOccurrence(o);
	}

	@Override
	public I removeLast() {
		I ret = super.removeLast();
		if (ret != null) {
			ret.removeListener(mListener);
		}
		return ret;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		if (!contains(o))
			return false;
		Value<O> i = (Value<O>) o;
		i.removeListener(mListener);
		return super.removeLastOccurrence(o);
	}

	@Override
	public I set(int index, I element) {
		element.addListener(mListener);
		return super.set(index, element);
	}

}
