package com.luzi82.homuvalue;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public abstract class Value<T> {

	public abstract T get();

	public abstract void addListener(Listener<T> listener);

	public final boolean isConstant;

	public Value(boolean isConstant) {
		this.isConstant = isConstant;
	}

	public static class Constant<T> extends Value<T> {
		private final T value;

		Constant(T t) {
			super(true);
			value = t;
		}

		@Override
		public final T get() {
			return value;
		}

		@Override
		public final void addListener(Listener<T> listener) {
			// do nothing
		}
	}

	public static <T> Constant<T> constant(T t) {
		Constant<T> ret = new Constant<T>(t);
		return ret;
	}

	public abstract static class Dynamic<T> extends Value<T> {
		private T value;
		private boolean dirty;
		private LinkedList<WeakReference<Listener<T>>> list;

		public abstract T update();

		public Dynamic() {
			super(false);
			dirty = true;
			list = new LinkedList<WeakReference<Listener<T>>>();
		}

		protected final void markDirty() {
			if (dirty)
				return;
			dirty = true;

			LinkedList<WeakReference<Listener<T>>> deadList = new LinkedList<WeakReference<Listener<T>>>();
			for (WeakReference<Listener<T>> ref : list) {
				Listener<T> l = ref.get();
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
		public final void addListener(Listener<T> listener) {
			list.add(new WeakReference<Listener<T>>(listener));
		}
	}

	public static class Variable<T> extends Dynamic<T> {
		private T var;

		public void set(T t) {
			var = t;
			markDirty();
		}

		@Override
		public T update() {
			return var;
		}
	}

	public static <T> Variable<T> variable(T t) {
		Variable<T> ret = new Variable<T>();
		ret.set(t);
		return ret;
	}

	public static interface Listener<T> {

		public void onValueDirty(Value<T> v);

	}

}
