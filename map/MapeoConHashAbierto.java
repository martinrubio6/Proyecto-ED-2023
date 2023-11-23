package map;

import list.*;

public class MapeoConHashAbierto<K,V> implements Map<K,V> {
	
	protected PositionList<Entrada<K,V>>[] arreglo;
	protected int cantEntradas;
	protected int NPrimo=13;
	protected final float FactorDeCarga = 0.75f;// Lo pongo en 0.5?
	
	@SuppressWarnings("unchecked")
	public MapeoConHashAbierto() {
		arreglo = (PositionList<Entrada<K, V>>[]) new PositionList[NPrimo];
		for (int i = 0; i < NPrimo; i++) {
			arreglo[i] = new ListaDobleEnlazada<Entrada<K, V>>();
		}
		cantEntradas = 0;
	}
	
	protected int hash(K clave) {
		return Math.abs(clave.hashCode() % NPrimo);
	}

	@Override
	public int size() {
		return cantEntradas;
	}

	@Override
	public boolean isEmpty() {
		return cantEntradas == 0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		V retorno = null;
		Position<Entrada<K,V>> puntero;
		CheckKey(key);
		PositionList<Entrada<K,V>> listaBucket = arreglo[hash(key)];
		try {
			puntero = listaBucket.isEmpty() ? null : listaBucket.first();
				while(puntero != null && retorno == null) {
					if(puntero.element().getKey().equals(key))
						retorno=puntero.element().getValue();
					if(puntero == listaBucket.last())
						puntero = null;
					else
						puntero = listaBucket.next(puntero);
				}
			
		}catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}
		
		return retorno;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		CheckKey(key);
		if(cantEntradas / arreglo.length >= FactorDeCarga)
			redimensionar();
		PositionList<Entrada<K, V>> listaBucket = arreglo[hash(key)];
		Position<Entrada<K, V>> puntero;
		V retorno = null;
		try {
			puntero = listaBucket.isEmpty() ? null : listaBucket.first();
				while(puntero != null && retorno == null) {
					if(puntero.element().getKey().equals(key)) {
						retorno = puntero.element().getValue();
						puntero.element().setValue(value);
					}
					if(puntero == listaBucket.last())
						puntero = null;
					else
						puntero = listaBucket.next(puntero);
				}
				if(retorno == null) {
					listaBucket.addLast(new Entrada<K,V>(key,value));
					cantEntradas++;
				}
			
		}catch(InvalidPositionException | BoundaryViolationException | EmptyListException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		CheckKey(key);
		PositionList<Entrada<K, V>> listaBucket = arreglo[hash(key)];
		Position<Entrada<K, V>> puntero;
		V retorno = null;
		try {
			puntero = listaBucket.isEmpty() ? null : listaBucket.first();
				while(puntero != null && retorno == null) {
					if(puntero.element().getKey().equals(key)) {
						retorno = puntero.element().getValue();
						listaBucket.remove(puntero);
						cantEntradas--;
					}
					if(retorno != null || listaBucket.isEmpty() || puntero == listaBucket.last()) //El problema es este, cambiar por el otro codigo, pero pensar que pasa
						puntero = null;
					else
						puntero = listaBucket.next(puntero);
				}
			
		}catch(InvalidPositionException | BoundaryViolationException | EmptyListException e) {
			e.printStackTrace();
		}
		
		return retorno;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> listaRetorno = new ListaDobleEnlazada<K>();
		Position<Entrada<K,V>> puntero;
		PositionList<Entrada<K,V>> listaBucketActual;
		try {
			for(int i = 0; i< arreglo.length;i++) {
				listaBucketActual = arreglo[i];
				if(!listaBucketActual.isEmpty()) {
					puntero = listaBucketActual.first();
					while(puntero != null) {
						listaRetorno.addLast(puntero.element().getKey());
						if(puntero == listaBucketActual.last())
							puntero = null;
						else
							puntero = listaBucketActual.next(puntero);
					}
				}
				
			}
		}catch(InvalidPositionException | BoundaryViolationException | EmptyListException e) {
			e.printStackTrace();
		}
		
		return listaRetorno;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> listaRetorno = new ListaDobleEnlazada<V>();
		PositionList<Entrada<K,V>> listaBucketActual;
		Position<Entrada<K,V>> puntero;
		try {
			for(int i=0;i<arreglo.length;i++) {
				listaBucketActual = arreglo[i];
				if(!listaBucketActual.isEmpty()) {
					puntero = listaBucketActual.first();
					while(puntero != null) {
						listaRetorno.addLast(puntero.element().getValue());
						if(puntero == listaBucketActual.last())
							puntero = null;
						else
							puntero = listaBucketActual.next(puntero);	
					}
				}
			}
		}catch(InvalidPositionException | BoundaryViolationException | EmptyListException e) {
			e.printStackTrace();
		}
		
		return listaRetorno;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> listaRetorno = new ListaDobleEnlazada<Entry<K,V>>();
		PositionList<Entrada<K,V>> listaBucketActual;
		Position<Entrada<K,V>> puntero;
		try {
			for(int i=0;i<arreglo.length;i++) {
				listaBucketActual = arreglo[i];
				if(!listaBucketActual.isEmpty()) {
					puntero = listaBucketActual.first();
					while(puntero != null) {
						listaRetorno.addLast(puntero.element());
						if(puntero == listaBucketActual.last())
							puntero = null;
						else
							puntero = listaBucketActual.next(puntero);	
					}
				}
			}
		}catch(InvalidPositionException | BoundaryViolationException | EmptyListException e) {
			e.printStackTrace();
		}
		
		return listaRetorno;
	}
	
	@SuppressWarnings("unchecked")
	private void redimensionar() {
		PositionList<Entrada<K, V>>[] ArregloAnterior = arreglo;
		PositionList<Entrada<K, V>> ListaAnterior;
		PositionList<Entrada<K, V>> ListaNueva;
		Entrada<K, V> Entrada;
		int Bucket;
		int NuevaDimension = proximo_primo(cantEntradas * 2);
		arreglo = (PositionList<Entrada<K, V>>[]) new PositionList[NuevaDimension];
		for (int i = 0; i < arreglo.length; i++)
			arreglo[i] = new ListaDobleEnlazada<Entrada<K, V>>();
		try {
			for (int i = 0; i < ArregloAnterior.length; i++) {
				ListaAnterior = ArregloAnterior[i];
				while (!ListaAnterior.isEmpty()) {
					Entrada = ListaAnterior.remove(ListaAnterior.last());
					Bucket = hash(Entrada.getKey());
					ListaNueva = arreglo[Bucket];
					ListaNueva.addLast(Entrada);
				}
			}
		} catch (EmptyListException | InvalidPositionException e) {
			e.printStackTrace();
		}
	}
	
	private int proximo_primo(int n) {
		boolean es = false;
		int primo = n - 1;
		while (!es) {
			primo = primo + 1;
			es = es_primo(primo);
		}
		return primo;
	}
	
	private boolean es_primo(int num) {
		boolean es = true;
		int cont = 2;
		while (es && cont != num) {
			if (num % cont == 0)
				es = false;
			cont++;
		}
		return es;
	}
	
	private void CheckKey(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("La clave ingresada es invalida");
	}

}
