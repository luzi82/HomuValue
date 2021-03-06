package com.luzi82.homuvalue;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public abstract class Value<T> {

	public abstract T get();

	public abstract void addListener(Listener<T> listener);

	public abstract void removeListener(Listener<T> intListener);

	public final boolean isConstant;

	public Value(boolean isConstant) {
		this.isConstant = isConstant;
	}

	public abstract boolean dirty();

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

		@Override
		public void removeListener(Listener<T> intListener) {
			// do nothing
		}

		@Override
		public boolean dirty() {
			return false;
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

		@Override
		public final void removeListener(Listener<T> listener) {
			WeakReference<Listener<T>> target = null;
			LinkedList<WeakReference<Listener<T>>> deadList = new LinkedList<WeakReference<Listener<T>>>();
			for (WeakReference<Listener<T>> ref : list) {
				Listener<T> l = ref.get();
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

	public static class Variable<T> extends Dynamic<T> {
		private T var;

		public void set(T t) {
			if (var == t)
				return;
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

	@SuppressWarnings("rawtypes")
	public abstract static class Function<T> extends Dynamic<T> implements
			Listener {
		public boolean isConst = true;

		@SuppressWarnings("unchecked")
		protected void addChild(Value v) {
			v.addListener(this);
			if (isConst && !v.isConstant) {
				isConst = false;
			}
		}

		@Override
		public void onValueDirty(Value v) {
			markDirty();
		}

		public Value<T> optimize() {
			if (isConst) {
				return Value.constant(get());
			} else {
				return this;
			}
		}
	}

	public static interface Listener<T> {

		public void onValueDirty(Value<T> v);

	}

	// slot

	public static <T> Slot<T> slot(T t) {
		return new Slot<T>(t);
	}

	public static class Slot<T> extends Dynamic<T> implements Listener {
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

}
