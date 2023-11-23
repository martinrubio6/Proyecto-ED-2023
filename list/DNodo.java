package list;

public class DNodo<E> implements Position<E> {
	
	protected E element;
	protected DNodo<E> next,prev;
	
	public DNodo(DNodo<E> pre, DNodo<E> nex, E elem) {
		
		element = elem;
		prev = pre;
		next = nex;
	}

	@Override
	public E element() {
		return element;
	}
	
	public void setElemento(E elem) {
		element = elem;
	}

	public void setNext(DNodo<E> nex) {
		next = nex;
	}

	public void setPrev(DNodo<E> pre) {
		prev = pre;
	}

	public DNodo<E> getNext() {
		return next;
	}

	public DNodo<E> getPrev() {
		return prev;
	}

}
