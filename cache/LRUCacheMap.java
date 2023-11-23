package cache;

import map.*;
import list.*;

public class LRUCacheMap<K, V> implements LRUCache<K, V> {

	protected static int capacidadMaxCache;
	protected Map<K, V> mapeoCache;
	protected PositionList<K> listaPosiciones;
	protected Map<K, Position<K>> mapeoPosiciones;

	public LRUCacheMap(int capacity) {
		capacidadMaxCache = capacity;
		mapeoCache = new MapeoConHashAbierto<K, V>();	
		listaPosiciones = new ListaDobleEnlazada<K>();
		mapeoPosiciones = new MapeoConHashAbierto<K, Position<K>>();
	}

	@Override
	public V get(K key) {
		V valorRetorno = null;
		try {
			valorRetorno = mapeoCache.get(key);
			if (valorRetorno != null) {
				listaPosiciones.addLast(key);
				listaPosiciones.remove(mapeoPosiciones.get(key));
				mapeoPosiciones.put(key, listaPosiciones.last());
			}
		} catch (InvalidKeyException | InvalidPositionException | EmptyListException e) {
			e.printStackTrace();
		}
		return valorRetorno;
	}

	@Override
	public void put(K key, V value) {
		try {
			if (key != null) {
				if (mapeoCache.get(key) != null) {
					mapeoCache.put(key, value);
					listaPosiciones.remove(mapeoPosiciones.get(key));
					listaPosiciones.addLast(key);
					mapeoPosiciones.put(key, listaPosiciones.last());
				} else {
					if (mapeoCache.size() >= capacidadMaxCache) {
						K lru = listaPosiciones.first().element();
						this.remove(lru);
					}
					mapeoCache.put(key, value);
					listaPosiciones.addLast(key);
					mapeoPosiciones.put(key, listaPosiciones.last());
				}
			}
		} catch (InvalidKeyException | InvalidPositionException | EmptyListException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void remove(K key) {
		try {
			if (mapeoCache.get(key) != null) {
				mapeoCache.remove(key);
				listaPosiciones.remove(mapeoPosiciones.get(key));
				mapeoPosiciones.remove(key);
			}

		} catch (InvalidKeyException | InvalidPositionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clear() {
		if (!mapeoCache.isEmpty())
			try {
				for (K clave : listaPosiciones) {
					mapeoCache.remove(clave);
					mapeoPosiciones.remove(clave);
				}
				while (!listaPosiciones.isEmpty())
					listaPosiciones.remove(listaPosiciones.first());
			} catch (InvalidKeyException | InvalidPositionException | EmptyListException e) {
				e.printStackTrace();
			}
	}

	@Override
	public int size() {
		return (mapeoCache.size());
	}

}
