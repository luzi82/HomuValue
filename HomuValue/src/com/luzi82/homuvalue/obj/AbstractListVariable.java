package com.luzi82.homuvalue.obj;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.luzi82.homuvalue.Dirty;

public abstract class AbstractListVariable<I, O> extends LinkedList<I> implements ListVariable<I, O> {

	protected final Dirty<List<O>> mDirty;

	public AbstractListVariable() {
		mDirty = new Dirty<List<O>>(this);
	}

	@Override
	public void addListener(Listener<List<O>> listener) {
		mDirty.addListener(listener);
	}

	@Override
	public void removeListener(Listener<List<O>> listener) {
		mDirty.removeListener(listener);
	}

	@Override
	public boolean isConstant() {
		return false;
	}

	@Override
	public boolean dirty() {
		return mDirty.get();
	}

	@Override
	public List<O> get() {
		LinkedList<O> ret = new LinkedList<O>();
		for (I i : this) {
			ret.add(toO(i));
		}
		mDirty.set(false);
		return ret;
	}

	protected abstract O toO(I i);

	@Override
	public void set(List<O> t) {
		super.clear();
		for (O o : t) {
			add(toI(o));
		}
		mDirty.set(true);
	}

	protected abstract I toI(O o);

	//

	@Override
	public boolean add(I e) {
		boolean ret = super.add(e);
		if (ret)
			mDirty.set(true);
		return ret;
	}

	@Override
	public void add(int index, I element) {
		super.add(index, element);
		mDirty.set(true);
	}

	@Override
	public boolean addAll(Collection<? extends I> c) {
		boolean ret = super.addAll(c);
		if (ret)
			mDirty.set(true);
		return ret;
	}

	@Override
	public boolean addAll(int index, Collection<? extends I> c) {
		boolean ret = super.addAll(index, c);
		if (ret)
			mDirty.set(true);
		return ret;
	}

	@Override
	public void addFirst(I e) {
		super.addFirst(e);
		mDirty.set(true);
	}

	@Override
	public void addLast(I e) {
		super.addLast(e);
		mDirty.set(true);
	}

	@Override
	public void clear() {
		super.clear();
		mDirty.set(true);
	}

	@Override
	public boolean offer(I e) {
		boolean ret = super.offer(e);
		if (ret)
			mDirty.set(true);
		return ret;
	}

	@Override
	public boolean offerFirst(I e) {
		boolean ret = super.offerFirst(e);
		if (ret)
			mDirty.set(true);
		return ret;
	}

	@Override
	public boolean offerLast(I e) {
		boolean ret = super.offerLast(e);
		if (ret)
			mDirty.set(true);
		return ret;
	}

	@Override
	public I poll() {
		I ret = super.poll();
		mDirty.set(true);
		return ret;
	}

	@Override
	public I pollFirst() {
		I ret = super.pollFirst();
		mDirty.set(true);
		return ret;
	}

	@Override
	public I pollLast() {
		I ret = super.pollLast();
		mDirty.set(true);
		return ret;
	}

	@Override
	public I pop() {
		I ret = super.pop();
		mDirty.set(true);
		return ret;
	}

	@Override
	public void push(I e) {
		super.push(e);
		mDirty.set(true);
	}

	@Override
	public I remove() {
		I ret = super.remove();
		mDirty.set(true);
		return ret;
	}

	@Override
	public I remove(int index) {
		I ret = super.remove(index);
		mDirty.set(true);
		return ret;
	}

	@Override
	public boolean remove(Object o) {
		boolean ret = super.remove(o);
		if (ret)
			mDirty.set(true);
		return ret;
	}

	@Override
	public I removeFirst() {
		I ret = super.removeFirst();
		mDirty.set(true);
		return ret;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		boolean ret = super.removeFirstOccurrence(o);
		if (ret)
			mDirty.set(true);
		return ret;
	}

	@Override
	public I removeLast() {
		I ret = super.removeLast();
		mDirty.set(true);
		return ret;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		boolean ret = super.removeLastOccurrence(o);
		if (ret)
			mDirty.set(true);
		return ret;
	}

	@Override
	public I set(int index, I element) {
		I ret = super.set(index, element);
		mDirty.set(true);
		return ret;
	}

}
