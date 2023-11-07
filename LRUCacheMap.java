package cache;

import java.security.InvalidKeyException;

import excepciones.EmptyListException;
import excepciones.InvalidPositionException;
import todo.DoubleLinkedList;
import todo.Entry;
import todo.Map;
import todo.MapeoConHASH;
import todo.PositionList;

public class LRUCacheMap<K, V> implements LRUCache<K, V> {

	protected static int capacidad;// pienso (ESTA BIEN)
	protected Map<K, V> mapeo;
	protected PositionList<K> lista;

	public LRUCacheMap(int capacity) {
		capacidad = capacity;
		mapeo = new MapeoConHASH<K, V>();
		lista = new DoubleLinkedList<K>();
	}

	@Override
	public V get(K key) {
		V devolver = null;
		try {
			if (key != null && !mapeo.isEmpty())
				devolver = mapeo.get(key);
		} catch (InvalidKeyException e) {
			e.getMessage();
		}
		return devolver;
	}

	@Override
	public void put(K key, V value) {
		if (key != null && !mapeo.isEmpty()) {// pienso, ya lo chequea en el put
			try {
				V resultado = mapeo.put(key, value);
				if (resultado == null)
					if (lista.size() < capacidad)
						lista.addLast(key);
					else {
						lista.remove(lista.first());
						lista.addLast(key);
					}
			} catch (InvalidKeyException | InvalidPositionException | EmptyListException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void remove(K key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		if (!mapeo.isEmpty())
		try {
			for (K clave : lista) {
				mapeo.remove(clave);
			}
			while(!lista.isEmpty())
				lista.remove(lista.first());
		}catch (InvalidKeyException | InvalidPositionException | EmptyListException e) {
			e.getMessage();
		}
	}

	@Override
	public int size() {
		return (mapeo.size());
	}

}
