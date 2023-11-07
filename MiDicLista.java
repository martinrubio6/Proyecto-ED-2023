package todo;

import java.security.InvalidKeyException;
import java.util.Iterator;
import excepciones.InvalidEntryException;
import excepciones.InvalidPositionException;

public class MiDicLista<K,V> implements Dictionary<K,V> {

	protected DoubleLinkedList<Entry<K,V>> lista;
	
	public MiDicLista() {
		lista=new DoubleLinkedList<Entry<K,V>>();
	}
	@Override
	public int size() {
		return this.size();
	}

	@Override
	public boolean isEmpty() {
		return this.size()==0;
	}

	@Override
	public Entry<K, V> find(K key) throws InvalidKeyException {
		Entry<K,V> devolver=null;
		if(key==null)
			throw new InvalidKeyException("Clave nula");
		else {
			Iterator<Entry<K,V>> it=lista.iterator();
			if(it.hasNext()) {
				Entry<K,V> cursor=it.next();
				while(cursor!=null && devolver==null) {
					if(cursor.getKey().equals(key))
						devolver=cursor;
					else
						cursor=it.next();
				}
			}
		}
		return devolver;
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		DoubleLinkedList<Entry<K,V>> devolver=new DoubleLinkedList<Entry<K,V>>();
		if(key==null)
			throw new InvalidKeyException("Clave Nula");
		for(Entry<K,V> entrada:lista) {
			if(entrada.getKey().equals(key))
			devolver.addLast(entrada);	
		}
		return devolver;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		Entry<K,V> devolver=null;
		if(key==null)
			throw new InvalidKeyException("Clave nula");
		devolver=new Entrada<K, V>(key,value);
		lista.addLast(devolver);
		return devolver;
	}

	@Override
	public Entry<K, V> remove(Entry<K, V> entrada) throws InvalidEntryException {
		try {
		if(entrada==null)
			throw new InvalidEntryException("Entrada invalida");
		else {
				boolean encontre=false;
				Iterator<Position<Entry<K, V>>> it= lista.positions().iterator();
				Position<Entry<K, V>> cursor=null;
				while(it.hasNext() && !encontre ) {
					cursor=it.next();
					if(cursor.element().equals(entrada)) {
						encontre=true;
						lista.remove(cursor);
					}
				}
			}
		
		}catch (InvalidPositionException e){
			e.getMessage();
		}
		return entrada;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		DoubleLinkedList<Entry<K,V>> entradas=new DoubleLinkedList<Entry<K,V>>();
		for(Entry<K,V> e:lista)
			entradas.addLast(e);
		return entradas;
	}

}
