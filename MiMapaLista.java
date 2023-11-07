package todo;

import java.security.InvalidKeyException;
import java.util.Iterator;

import excepciones.InvalidPositionException;

public class MiMapaLista<K,V> implements Map<K,V> {

	protected DoubleLinkedList<Entrada<K,V>> lista;
	
	public MiMapaLista() {
		lista=new DoubleLinkedList<Entrada<K,V>>();
	}
	public int size() {
		return this.size();
	}

	public boolean isEmpty() {
	
		return this.size()==0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		V resultado=null;
		if(key==null)
			throw new InvalidKeyException("Clave nula");
		else {
			Iterator<Entrada<K,V>> it=lista.iterator();
			while(it.hasNext() && resultado==null) {
				Entry<K,V> cursor=it.next();
				if(cursor.getKey().equals(key))
					resultado=cursor.getValue();
			}
		}
		return resultado;
	}


	public V put(K key, V value) throws InvalidKeyException {
		V resultado=null;
		if(key==null)
			throw new InvalidKeyException("Clave nula");
		else {
			if(!lista.isEmpty()) {
			resultado=this.get(key);
			  if(resultado==null) {
				Entrada<K,V> entrada=new Entrada<K, V>(key,value);
				lista.addLast(entrada);
			  }
			else {
				boolean encontre=false;
				Iterator<Entrada<K,V>> it= lista.iterator();
				while(it.hasNext() && !encontre ) {
					Entrada<K,V> cursor=it.next();
					if(cursor.getKey().equals(key)) {
						encontre=true;
						cursor.setValue(value);
					}
				}
			}	
			}
		}
		return resultado;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		V resultado=null;
		try {
		if(!lista.isEmpty()) {
			if(key==null)
				throw new InvalidKeyException("Clave nula");
			else {
				boolean encontre=false;
				Iterator<Position<Entrada<K,V>>> it= lista.positions().iterator();
				Position<Entrada<K,V>> cursor=null;
				while(it.hasNext() && !encontre ) {
					cursor=it.next();
					if(cursor.element().getKey().equals(key)) {
						encontre=true;
						resultado=cursor.element().getValue();
						lista.remove(cursor);
					}
				}
			}
		}
		}catch (InvalidPositionException e){
			e.getMessage();
		}
		return resultado;
	}

	@Override
	public Iterable<K> keys() {
		DoubleLinkedList<K> devolver=new DoubleLinkedList<K>();
		for(Entrada<K,V> entrada:lista) {
			devolver.addLast(entrada.getKey());
		}
		return devolver;
	}

	@Override
	public Iterable<V> values() {
		DoubleLinkedList<V> devolver=new DoubleLinkedList<V>();
		for(Entrada<K,V> entrada:lista) {
			devolver.addLast(entrada.getValue());
		}
		return devolver;
	}

	@Override
	public Iterable<Entry<K,V>> entries() {
		DoubleLinkedList<Entry<K,V>> devolver=new DoubleLinkedList<Entry<K,V>>();
		for(Entry<K,V> entrada:lista) {
			devolver.addLast(entrada);
		}
		return devolver;
	}

}
