package map;

public class Entrada<K, V> implements Entry<K, V> {

	protected K clave;
	protected V valor;

	public Entrada(K key, V value) {
		clave = key;
		valor = value;
	}

	public void setKey(K key) {
		clave = key;
	}

	public void setValue(V value) {
		valor = value;
	}

	@Override
	public K getKey() {

		return clave;
	}

	@Override
	public V getValue() {

		return valor;
	}

}
