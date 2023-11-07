package todo;

public class DNodo<E> implements Position<E>{
	
	protected E elem;
	protected DNodo<E> sig;
	protected DNodo<E> ant;
	
	public DNodo(E e, DNodo<E> s, DNodo<E> a){
		elem=e;
		sig=s;
		ant=a;
	}
	public DNodo(E e){
		this(e,null,null); //o elem=e, sig=null, ant=null
	}
	public void setElemento(E e) {
		elem=e;
	}
	public void setSiguiente(DNodo<E>s) {
		sig=s;
	}
	public void setAnterior(DNodo<E>a) {
		ant=a;
	}
	public E element() {
		return elem;
	}
	public DNodo<E> getSiguiente(){
		return sig;
	}
	public DNodo<E> getAnterior(){
		return ant;
	}
}
