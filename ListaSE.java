package practica;

import java.util.Iterator;
import excepciones.BoundaryViolationException;
import excepciones.EmptyListException;
import excepciones.InvalidPositionException;

public class ListaSE<E> implements PositionList<E> {
	protected Node<E> head;
	protected int cant;

	public ListaSE() {
		cant=0;
		head=null;
	}
	
	public void eliminarEnElMedio(E e1,E e2) {
		if(!this.isEmpty() && this.cant >=3) {
			Node<E> pos= this.head;
			pos =dameUnaTerna(pos,e1,e2);//dada una posicion que representa mi principio de la terna
			while( pos!= null) {
				eliminarTerna(pos);//mi pos pasa a ser el e2 de la terna
				pos=dameUnaTerna(pos,e1,e2);
			}
		}
	}
	private void eliminarTerna(Node<E> pos) {
			Node<E> siguiente=pos.getSiguiente();
			pos.setSiguiente(siguiente.getSiguiente());
			pos= pos.getSiguiente();
	}
	private Node<E> dameUnaTerna(Node<E> pos,E e1,E e2) {
		Node<E> resultado=pos;
		if(cant>=3)
		while(pos!=e1) {
			if(pos==e1) {
				if(pos.getSiguiente().getSiguiente().getSiguiente()==e2)
				pos=pos.getSiguiente();
			}
		}	
		return resultado;}
	
	@Override
	public int size() {
		return cant;
	}
	@Override
	public boolean isEmpty() {
		return (cant==0);
	}

	@Override
	public Position<E> first() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("La lista vacía no tiene primero");
		return head;
	}
	@Override
	public Position<E> last() throws EmptyListException {
		Node<E> ultimo=head;
		if(isEmpty())
			throw new EmptyListException("Listav vacia no tiene ultimo");
		while(ultimo.getSiguiente()!=null)
			ultimo=ultimo.getSiguiente();
		return ultimo;
	}
	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Node<E> sig=checkPosition(p);
		try{
			if(sig==last())
				throw new BoundaryViolationException("El ultimo no tiene siguiente");
			sig=sig.getSiguiente();
			}catch (EmptyListException e) {
				throw new InvalidPositionException("Lista Vacia");
			}
		return sig;
	}
	private Node<E> checkPosition(Position<E> p) throws InvalidPositionException{
		Node<E> resultado=null;
		if(p==null)
			throw new InvalidPositionException("Posicion nula");
		if(isEmpty())
			throw new InvalidPositionException("Lista vacia");
		try {
			resultado= (Node<E>) p;
		}catch (ClassCastException e) {
			throw new InvalidPositionException("Error de casteo");
		}
		return resultado;
	}
	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Node<E> ant= checkPosition(p);
			if(ant==head)
				throw new BoundaryViolationException("El primero no tiene anterior");
		Node<E> cursor=head;
		while(cursor.getSiguiente()!=ant) {
			cursor=cursor.getSiguiente();
		}
		return cursor;
	}
	@Override
	public void addFirst(E element) {
		Node<E> primero= new Node<E>(element,head);
		head=primero;
		cant++;
	}
	@Override
	public void addLast(E element) {
		Node<E> ultimo=new Node<E>(element,null);
		if(isEmpty())
			head=ultimo;
		else {
			Node<E> cursor=head;
			while(cursor.getSiguiente()!=null)
				cursor=cursor.getSiguiente();
			cursor.setSiguiente(ultimo);
		}
		cant++;
	}
	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		Node<E> anterior=checkPosition(p);
		Node<E> agregar=new Node<E>(element,anterior.getSiguiente());
		anterior.setSiguiente(agregar);
		cant++;
	}
	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		Node<E> siguiente=checkPosition(p);
		Node<E> agregar=new Node<E>(element,siguiente);
		if(siguiente==head)
			head=agregar;
		else {
			Node<E> cursor=head;
		while(cursor.getSiguiente()!=siguiente)
			cursor=cursor.getSiguiente();
		cursor.setSiguiente(agregar);
		}
		cant++;
	}
	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		Node<E> anterior= checkPosition(p);
		if(anterior==head)
			head=head.getSiguiente();
		else {
			Node<E> cursor=head;
			while(cursor.getSiguiente()!=anterior)
				cursor=cursor.getSiguiente();
			cursor.setSiguiente(cursor.getSiguiente());	
			cant--;
		}
		return anterior.element();
	}
	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		Node<E> anterior=checkPosition(p);
		E devolver=anterior.element();
		anterior.setElement(element);
		return devolver;
	}
	@Override
	public Iterator<E> iterator() {
		ElementIterator<E> lista=new ElementIterator<E>(this);
		return lista;
	}
	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> lista=new ListaSE<Position<E>>();
		Node<E> cursor=head;
		if(!this.isEmpty())
			while(cursor.getSiguiente()!=null) {
			lista.addLast(cursor);
			cursor=cursor.getSiguiente();
			}
		lista.addLast(cursor);
		return lista;
	}
	public void removeAll(E elem) {

		Node<E> cursor= head;
		
		while(cursor.getSiguiente()!=null) {
			if(cursor.getSiguiente().element().equals(elem)) {
				cursor.setSiguiente(cursor.getSiguiente().getSiguiente());
				cant--;
			}
				cursor=cursor.getSiguiente();
		}
	}
	
}
