package todo;
public class Entrada<K,V> implements Entry<K,V> {
	private K clave;
	private V valor;
// Constructor
public Entrada(K clave, V valor) { 
	this.clave = clave; 
	this.valor = valor; 
	}

public K getKey() {
	return clave;
} // Getters
public V getValue() {
	return valor;
}
public void setKey( K key ) {
	clave=key;
} // Setters
public void setValue(V value) {
	valor=value;
}
public String toString( ) { // Para mostrar entradas
	return (""+ getKey() +", " + getValue()); }
}
