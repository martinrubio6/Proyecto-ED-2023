package list;

import java.util.Iterator;

public class ListaDobleEnlazada<E> implements PositionList<E> {

	protected DNodo<E> head;
	protected DNodo<E> trail;
	protected int tamaño;

	public ListaDobleEnlazada() {
		head = new DNodo<E>(null, null, null);
		trail = new DNodo<E>(head, null, null);
		head.setNext(trail);
		tamaño = 0;
	}

	@Override
	public int size() {
		return tamaño;
	}

	@Override
	public boolean isEmpty() {
		return tamaño == 0;
	}

	@Override
	public Position<E> first() throws EmptyListException {
		if (tamaño == 0)
			throw new EmptyListException("First: Lista Vacia");
		return head.getNext();
	}

	@Override
	public Position<E> last() throws EmptyListException {
		if (tamaño == 0)
			throw new EmptyListException("Last: Lista Vacia");
		return trail.getPrev();
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNodo<E> posActual;
		if (tamaño == 0)
			throw new InvalidPositionException("Next: Lista Vacia");
		posActual = checkPosition(p);
		if (posActual.getNext() == trail)
			throw new BoundaryViolationException("Next: No existe siguiente del ultimo elemento");
		return posActual.getNext();

	}

	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNodo<E> posActual;
		if (tamaño == 0)
			throw new InvalidPositionException("Prev: Lista Vacia");
		posActual = checkPosition(p);
		if (posActual.getPrev() == head)
			throw new BoundaryViolationException("Prev: No existe anterior al primer elemento");
		return posActual.getPrev();
	}

	@Override
	public void addFirst(E element) {
		DNodo<E> nuevoNodo = new DNodo<E>(head, head.getNext(), element);
		head.getNext().setPrev(nuevoNodo);
		head.setNext(nuevoNodo);
		tamaño++;

	}

	@Override
	public void addLast(E element) {
		DNodo<E> nuevoNodo = new DNodo<E>(trail.getPrev(), trail, element);
		trail.getPrev().setNext(nuevoNodo);
		trail.setPrev(nuevoNodo);
		tamaño++;

	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		DNodo<E> posActual, nuevoNodo;
		if (tamaño == 0)
			throw new InvalidPositionException("addAfter: Lista Vacia");
		posActual = checkPosition(p);
		nuevoNodo = new DNodo<E>(posActual, posActual.getNext(), element);
		posActual.getNext().setPrev(nuevoNodo);
		posActual.setNext(nuevoNodo);
		tamaño++;

	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		DNodo<E> posActual, nuevoNodo;
		if (tamaño == 0)
			throw new InvalidPositionException("addBefore: Lista Vacia");
		posActual = checkPosition(p);
		nuevoNodo = new DNodo<E>(posActual.getPrev(), posActual, element);
		posActual.getPrev().setNext(nuevoNodo);
		posActual.setPrev(nuevoNodo);
		tamaño++;

	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		E retorno;
		DNodo<E> posActual;

		if (tamaño == 0)
			throw new InvalidPositionException("Remove: Lista Vacia");
		posActual = checkPosition(p);
		retorno = posActual.element;

		posActual.getNext().setPrev(posActual.getPrev());
		posActual.getPrev().setNext(posActual.getNext());

		posActual.setElemento(null);
		posActual.setNext(null);
		posActual.setPrev(null);

		tamaño--;

		return retorno;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		E retorno;
		DNodo<E> nodo = checkPosition(p);
		retorno = nodo.element();
		nodo.setElemento(element);
		return retorno;
	}

	@Override
	public Iterator<E> iterator() {
		IteradorDeLista<E> resultado = new IteradorDeLista<E>(this);
		return resultado;
	}

	@Override
	public Iterable<Position<E>> positions() {
		Position<E> puntero;
		PositionList<Position<E>> listaRetorno = new ListaDobleEnlazada<Position<E>>();
		if (!isEmpty()) {
			puntero = head.getNext();
			while (puntero != null) {
				listaRetorno.addLast(puntero);
				try {
					puntero = (puntero == last()) ? null : next(puntero);
				} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
					e.printStackTrace();
				}
			}
		}
		return listaRetorno;

	}

	// ------------------- EJERCICIO 2 TP4 ---------------------//

	public void agregar(E item1, E item2) {

		DNodo<E> nodonuevo1, nodonuevo2;

		if (tamaño == 0) {
			nodonuevo1 = new DNodo<E>(null, null, item1);
			nodonuevo2 = new DNodo<E>(head, nodonuevo1, item2);
			head.setNext(nodonuevo2);
			nodonuevo1.setPrev(nodonuevo2);
			nodonuevo1.setNext(trail);
			trail.setPrev(nodonuevo1);
			tamaño = tamaño + 2;
		} else {
			if (tamaño >= 2) {
				nodonuevo1 = new DNodo<E>(head.getNext(), head.getNext().getNext(), item1);
				head.getNext().getNext().setPrev(nodonuevo1);
				head.getNext().setNext(nodonuevo1);
				nodonuevo2 = new DNodo<E>(trail.getPrev().getPrev(), trail.getPrev(), item2);
				trail.getPrev().getPrev().setNext(nodonuevo2);
				trail.getPrev().setPrev(nodonuevo2);
				tamaño = tamaño + 2;
			}
		}
	}

	// ----------------------------------------------------------//

	private DNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		DNodo<E> n;
		try {
			if (p == null)
				throw new InvalidPositionException("Posicion nula");
			if (p.element() == null)
				throw new InvalidPositionException("La posicion fue eliminada previamente");
			n = (DNodo<E>) p;
		} catch (ClassCastException e) {
			throw new InvalidPositionException("Posicion invalida");
		}
		return n;
	}

}
