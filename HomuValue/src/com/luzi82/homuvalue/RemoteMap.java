package com.luzi82.homuvalue;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class RemoteMap<K, V extends AbstractValue, U> extends RemoteGroup<Map<K, U>> implements Map<K, V> {

	public HashMap<K, V> mValueMap;

	public Constractor<V, U> mValueConstractor;

	public RemoteMap(Map<K, U> aV, Constractor<V, U> aValueConstractor) {
		super(aV);
		mValueMap = new HashMap<K, V>();
		this.mValueConstractor = aValueConstractor;
	}

	@Override
	public void clear() {
		if (isEmpty())
			return;
		iV.clear();
		mValueMap.clear();
		markDirty();
	}

	@Override
	public boolean containsKey(Object key) {
		return mValueMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return mValueMap.containsValue(value);
	}

	public boolean remoteContainsValue(Object value) {
		return iV.containsKey(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return mValueMap.entrySet();
	}

	@Override
	public V get(Object key) {
		return mValueMap.get(key);
	}

	public U remoteGet(Object key) {
		return iV.get(key);
	}

	@Override
	public boolean isEmpty() {
		return mValueMap.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return mValueMap.keySet();
	}

	@Override
	public V put(K key, V value) {
		throw new UnsupportedOperationException();
	}

	public U remotePut(K key, U value) {
		remoteRemove(key);
		U ret = iV.put(key, value);
		V v = mValueConstractor.create(value);
		mValueMap.put(key, v);
		addChild(v);
		markDirty();
		notifyMapListenerListAdd(key,v,value);
		return ret;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	public U remoteRemove(K key) {
		if (!containsKey(key))
			return null;
		U ret = iV.remove(key);
		V v = mValueMap.remove(key);
		if (v != null) {
			removeChild(v);
		}
		markDirty();
		notifyMapListenerListRemove(key,v,ret);
		return ret;
	}

	@Override
	public int size() {
		return mValueMap.size();
	}

	@Override
	public Collection<V> values() {
		return mValueMap.values();
	}

	public static interface Constractor<V extends AbstractValue, U> {
		public V create(U u);
	}

	public static interface MapListener<K, V extends AbstractValue, U> {
		public void onMapAdd(RemoteMap<K, V, U> map, K key, V value, U e);

		public void onMapRemove(RemoteMap<K, V, U> map, K key, V value, U e);
	}

	private LinkedList<WeakReference<MapListener<K, V, U>>> list = new LinkedList<WeakReference<MapListener<K, V, U>>>();

	public void addMapListener(MapListener<K, V, U> l) {
		list.add(new WeakReference<RemoteMap.MapListener<K, V, U>>(l));
	}

	public void removeMapListener(MapListener<K, V, U> listener) {
		WeakReference<MapListener<K, V, U>> target = null;
		LinkedList<WeakReference<MapListener<K, V, U>>> deadList = new LinkedList<WeakReference<MapListener<K, V, U>>>();
		for (WeakReference<MapListener<K, V, U>> ref : list) {
			MapListener<K, V, U> l = ref.get();
			if (l == null) {
				deadList.add(ref);
			} else if (l == listener) {
				target = ref;
			}
		}
		list.removeAll(deadList);
		list.remove(target);
	}

	protected void notifyMapListenerListAdd(K key, V value, U v) {
		LinkedList<WeakReference<MapListener<K, V, U>>> deadList = new LinkedList<WeakReference<MapListener<K, V, U>>>();
		for (WeakReference<MapListener<K, V, U>> ref : list) {
			MapListener<K, V, U> l = ref.get();
			if (l == null) {
				deadList.add(ref);
			} else {
				l.onMapAdd(this, key, value, v);
			}
		}
		list.removeAll(deadList);
	}

	protected void notifyMapListenerListRemove(K key, V value, U v) {
		LinkedList<WeakReference<MapListener<K, V, U>>> deadList = new LinkedList<WeakReference<MapListener<K, V, U>>>();
		for (WeakReference<MapListener<K, V, U>> ref : list) {
			MapListener<K, V, U> l = ref.get();
			if (l == null) {
				deadList.add(ref);
			} else {
				l.onMapRemove(this, key, value, v);
			}
		}
		list.removeAll(deadList);
	}
}
