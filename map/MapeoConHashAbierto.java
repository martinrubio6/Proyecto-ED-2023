package map;

import java.util.Iterator;
import list.*;

public class MapeoConHashAbierto<K, V> implements Map<K, V> {

	protected static int CUBETAS = 11;
	protected PositionList<Entry<K, V>>[] mapeo;
	protected int cantidad;

	@SuppressWarnings("unchecked")
	public MapeoConHashAbierto() {
		cantidad = 0;
		mapeo = new ListaDobleEnlazada[CUBETAS];
		for (int i = 0; i < CUBETAS; i++)
			mapeo[i] = new ListaDobleEnlazada<Entry<K, V>>();
	}

	protected int hashCode(K clave) {
		return (clave.hashCode() % CUBETAS); // k mod C
	}

	@Override
	public int size() {
		return cantidad;
	}

	@Override
	public boolean isEmpty() {
		return cantidad == 0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		V resultado = null;
		if (key == null)
			throw new InvalidKeyException("Clave nula");
		else {
			int cubeta = hashCode(key);
			if (!mapeo[cubeta].isEmpty()) {
				resultado = buscarEnCubeta(mapeo[cubeta], key);
			}
		}
		return resultado;
	}

	private V buscarEnCubeta(PositionList<Entry<K, V>> lista, K clave) {
		V resultado = null;
		Iterator<Entry<K, V>> it = lista.iterator();
		boolean encontre = false;
		while (it.hasNext() && !encontre) {
			Entry<K, V> entrada = it.next();
			if (entrada.getKey().equals(clave)) {
				encontre = true;
				resultado = entrada.getValue();
			}
		}
		return resultado;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		V resultado = null;
		try {
			if (key == null)
				throw new InvalidKeyException("Clave nula");
			else {
				int cubeta = hashCode(key);
				resultado = buscarEnCubeta(mapeo[cubeta], key);
				if (resultado == null) {
					Entry<K, V> entrada = new Entrada<K, V>(key, value);
					mapeo[cubeta].addLast(entrada);
					cantidad++;
				} else {
					boolean encontre = false;
					Position<Entry<K, V>> cursor = mapeo[cubeta].first();
					while (cursor != null && !encontre) {
						if (cursor.element().getKey().equals(key)) {
							Entry<K, V> entrada = new Entrada<K, V>(key, value);
							mapeo[cubeta].set(cursor, entrada);
							resultado = cursor.element().getValue();
							encontre = true;
							// cantidad++;
						} else if (cursor != mapeo[cubeta].last())
							cursor = mapeo[cubeta].next(cursor);
						else
							cursor = null;
					}
				}
			}
		} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			e.getMessage();
		}
		return resultado;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		V resultado = null;
		if (key == null)
			throw new InvalidKeyException("Clave nula");
		try {
			int cubeta = hashCode(key);
			boolean encontre = false;
			Position<Entry<K, V>> cursor = mapeo[cubeta].first();
			while (cursor != null && !encontre) {
				if (cursor.element().getKey().equals(key)) {
					resultado = cursor.element().getValue();
					mapeo[cubeta].remove(cursor);
					encontre = true;
					cantidad--;
				} else if (cursor != mapeo[cubeta].last())
					cursor = mapeo[cubeta].next(cursor);
				else
					cursor = null;
			}
		} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			e.getMessage();
		}
		return resultado;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> claves = new ListaDobleEnlazada<K>();
		try {
			for (PositionList<Entry<K, V>> cubeta : mapeo) {
				Position<Entry<K, V>> cursor = cubeta.first();
				while (cursor != null) {
					claves.addLast(cursor.element().getKey());
					if (cursor != cubeta.last())
						cursor = cubeta.next(cursor);
					else
						cursor = null;
				}
			}
		} catch (EmptyListException | BoundaryViolationException | InvalidPositionException e) {
			e.getMessage();
		}
		return claves;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> valores = new ListaDobleEnlazada<V>();
		try {
			for (PositionList<Entry<K, V>> cubeta : mapeo) {
				Position<Entry<K, V>> cursor = cubeta.first();
				while (cursor != null) {
					valores.addLast(cursor.element().getValue());
					if (cursor != cubeta.last())
						cursor = cubeta.next(cursor);
					else
						cursor = null;
				}
			}
		} catch (EmptyListException | BoundaryViolationException | InvalidPositionException e) {
			e.getMessage();
		}
		return valores;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> entradas = new ListaDobleEnlazada<Entry<K, V>>();
		try {
			for (PositionList<Entry<K, V>> cubeta : mapeo) {
				Position<Entry<K, V>> cursor = cubeta.first();
				while (cursor != null) {
					entradas.addLast(cursor.element());
					if (cursor != cubeta.last())
						cursor = cubeta.next(cursor);
					else
						cursor = null;
				}
			}
		} catch (EmptyListException | BoundaryViolationException | InvalidPositionException e) {
			e.getMessage();
		}
		return entradas;
	}

}