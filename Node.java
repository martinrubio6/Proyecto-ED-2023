package practica;

public class Node<E> implements Position<E> {
	protected E elemento;
	protected Node<E> siguiente;
	
	public Node( E elemento, Node<E> siguiente) {
		this.elemento = elemento;
		this.siguiente = siguiente;
	}
	
	public Node<E> getSiguiente() {
		return this.siguiente;
	}
	
	public void setSiguiente(Node<E> siguiente ) {
		this.siguiente = siguiente;
	}
	public void setElement(E elem) {
		elemento=elem;
	}
	@Override
	public E element() {
		return elemento;
	}
	
}
