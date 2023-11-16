package cache;

import java.security.InvalidKeyException;

import excepciones.EmptyListException;
import excepciones.InvalidPositionException;
import todo.DoubleLinkedList;
import todo.Map;
import todo.MapeoConHASH;
import todo.Position;
import todo.PositionList;

public class LRUCacheMap<K, V> implements LRUCache<K, V> {

	protected static int capacidad;
	protected Map<K, V> cache;
	protected PositionList<K> lista;
	protected Map<K,Position<K>> guardar; 

	public LRUCacheMap(int capacity) {
		capacidad = capacity;
		cache = new MapeoConHASH<K, V>();
		lista = new DoubleLinkedList<K>();
		guardar= new MapeoConHASH<K,Position<K>>();
	}

	@Override
	public V get(K key) {
		V devolver = null;
		try {
			devolver=cache.get(key);
			if(devolver!=null) {
				lista.addLast(key);
				lista.remove(guardar.get(key));
				guardar.put(key,lista.last());
			}
		} catch (InvalidKeyException | InvalidPositionException | EmptyListException e) {
			e.getMessage();
		}
		return devolver;
	}
	
	@Override
	public void put(K key, V value) {
			try {
				if (key != null ) { 
				if(cache.get(key)!=null) { // Existia la clave.
					cache.put(key, value);
					lista.remove(guardar.get(key));
					lista.addLast(key);
					guardar.put(key, lista.last());
				}
				else { 
					if (cache.size() >= capacidad) {
						cache.put(key, value);
						lista.addLast(key);
						guardar.put(key, lista.last());
						
						K lru = lista.first().element(); //elemento a eliminar PREGUNTAR si es lo mismo usar this.remove(lru)
						lista.remove(lista.first());
						cache.remove(lru);
						guardar.remove(lru);
					}
					else {
						cache.put(key, value);
						lista.addLast(key);
						guardar.put(key, lista.last());
					}
				}
			}
			} catch (InvalidKeyException | InvalidPositionException | EmptyListException e) {
				e.printStackTrace();
			}
		
	}

	@Override
	public void remove(K key) {
		try {
			cache.remove(key);
			lista.remove(guardar.get(key));
			guardar.remove(key);
			
		}catch (InvalidKeyException | InvalidPositionException e) {
			e.getMessage();
		}
	}

	@Override
	public void clear() {
		if (!cache.isEmpty())
		try {
			for (K clave : lista) {
				cache.remove(clave);
				guardar.remove(clave);
			}
			while(!lista.isEmpty())
				lista.remove(lista.first());
		}catch (InvalidKeyException | InvalidPositionException | EmptyListException e) {
			e.getMessage();
		}
	}

	@Override
	public int size() {
		return (cache.size());
	}

}
