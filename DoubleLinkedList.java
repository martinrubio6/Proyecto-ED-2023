package todo;

import java.util.Iterator;

import excepciones.EmptyListException;
import excepciones.InvalidPositionException;
import excepciones.BoundaryViolationException;


public class DoubleLinkedList<E> implements PositionList<E>{
	
	protected int cant;
	protected DNodo<E> head,tail;
	
	public DoubleLinkedList() {
		
		head= new DNodo<E>(null,null,null);
		tail= new DNodo<E>(null,null,null);
		head.setSiguiente(tail);
		tail.setAnterior(head);
		cant=0;}
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
		DNodo<E> posi=null;
		if(this.isEmpty())
			throw new EmptyListException("Lista Vacia");
		posi= head.getSiguiente();
		return posi;
	}

	@Override
	public Position<E> last() throws EmptyListException {
		DNodo<E> posi=null;
		if(this.isEmpty())
			throw new EmptyListException("Lista Vacia");
		posi= tail.getAnterior();
		return posi;
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNodo<E> posi=checkPosition(p);
		if(posi.getSiguiente()==tail)
			throw new BoundaryViolationException("No hay siguiente");
		posi=posi.getSiguiente();
		return posi;
	}
	private DNodo<E> checkPosition(Position<E> pos) throws InvalidPositionException{
		DNodo<E> resultado=null;
		if(pos==null)
			throw new InvalidPositionException("Posicion nula");
		if(pos==head || pos==tail)
			throw new InvalidPositionException("Posicion invalida");
		try {
			DNodo<E> aux= (DNodo<E>) pos;
			resultado=aux;
		} catch (ClassCastException e) {//agarra el haber hecho casting para que no tire error
			throw new InvalidPositionException("Posicion de un tipo equivocado");
		}
		return resultado;
	}

	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNodo<E> posi=checkPosition(p);
		if(posi.getAnterior()==head)
			throw new BoundaryViolationException("No hay anterior");
		posi=posi.getAnterior();
		return posi;
	}

	@Override
	public void addFirst(E element) {
		DNodo<E> nuevo;
		if(this.isEmpty()) {
			nuevo= new DNodo<E>(element,tail,head);
			tail.setAnterior(nuevo);
			head.setSiguiente(nuevo);
		}
		else {
		nuevo= new DNodo<E>(element,head.getSiguiente(),head);
		head.getSiguiente().setAnterior(nuevo);
		head.setSiguiente(nuevo);}
		cant++;

	}

	@Override
	public void addLast(E element) {
		DNodo<E> nuevo= new DNodo<E>(element,tail,tail.getAnterior());
		tail.getAnterior().setSiguiente(nuevo);
		tail.setAnterior(nuevo);
		cant++;
	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		DNodo<E> pos= checkPosition(p);
		if(this.isEmpty())
			throw new InvalidPositionException("Lista vacia");
		DNodo<E> nuevo= new DNodo<E>(element,pos.getSiguiente(),pos);
		pos.getSiguiente().setAnterior(nuevo);
		pos.setSiguiente(nuevo);
		cant++;
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		DNodo<E> pos= checkPosition(p);
		if(this.isEmpty())
			throw new InvalidPositionException("Lista vacia");
		DNodo<E> nuevo= new DNodo<E>(element,pos,pos.getAnterior());
		pos.getAnterior().setSiguiente(nuevo);
		pos.setAnterior(nuevo);
		cant++;
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		DNodo<E> pos= checkPosition(p);
		E devolver=pos.element();
		if(this.isEmpty())
			throw new InvalidPositionException("Lista vacia");
		pos.getAnterior().setSiguiente(pos.getSiguiente());
		pos.getSiguiente().setAnterior(pos.getAnterior());
		cant--;
		return devolver;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		// TODO Auto-generated method stub
		DNodo<E> pos= checkPosition(p);
		if(this.isEmpty())
			throw new InvalidPositionException("Lista vacia");
		E anterior= pos.element();
		pos.setElemento(element);
		
		return anterior;
	}

	@Override
	public Iterator<E> iterator() {
		
		return new ElementIterator<E>(this);
	}

	@Override
	public Iterable<Position<E>> positions() {
		DoubleLinkedList<Position<E>> nueva= new DoubleLinkedList<Position<E>>();
		DNodo<E> nodo=head.getSiguiente();
		while(nodo!=tail) {
		nueva.addLast(nodo);
		nodo=nodo.getSiguiente();
		}
		return nueva;
	}
	public void punto2(E e1,E e2){//podria tirar alguna excepcion??? o si no me lo pide no
		if(this.size()>1) {//si es = a 1???
		DNodo<E> segundo= new DNodo<E>(e1,head.getSiguiente().getSiguiente(),head.getSiguiente());
		head.getSiguiente().setSiguiente(segundo);
		head.getSiguiente().getSiguiente().setAnterior(segundo);
		cant++;
		DNodo<E> antUlt= new DNodo<E>(e2,tail.getAnterior(),tail.getAnterior().getAnterior());
		tail.getAnterior().setAnterior(antUlt);
		tail.getAnterior().getAnterior().setSiguiente(antUlt);
		cant++;}
		else {
			DNodo<E> antUlt= new DNodo<E>(e2,tail,head);
			head.setSiguiente(antUlt);
			tail.setAnterior(antUlt);
			cant++;
			DNodo<E> segundo= new DNodo<E>(e1,head.getSiguiente().getSiguiente(),head.getSiguiente());
			head.getSiguiente().setSiguiente(segundo);
			head.getSiguiente().getSiguiente().setAnterior(segundo);
			cant++;
		}
	}
	public boolean punto3(PositionList<E> l,E e1) {
		boolean resultado=false;
		if(!l.isEmpty()) {
		Iterator<E> it=l.iterator();
		while(!resultado && it.hasNext()) {
			resultado= (it.next()).equals(e1);
		}
		}
		return resultado;
	}
	public PositionList<E> punto4(PositionList<E> l){
		PositionList<E> lista=new DoubleLinkedList<E>();
		if(!l.isEmpty())
		for(E elemento:l){
			lista.addLast(elemento);
			lista.addLast(elemento);
		}
		return lista;
	}
	
	public void eliminar(E e1) throws InvalidPositionException {
		if(!this.isEmpty()) {
			DNodo<E> cursor=tail.getAnterior();
			if(cursor==head)
				throw new InvalidPositionException("Lista vacia");
			while(cursor!=head) {
				if(cursor.equals(e1))
					this.remove(cursor);
				cursor=cursor.getAnterior();
			}
		}
	}
	public PositionList<E> capicua(PositionList<E> l) throws EmptyListException{
		if(l.isEmpty())
			throw new EmptyListException("Lista vacia");
		DNodo<E> cursor=tail.getAnterior();
		while(cursor!=head) {
			l.addLast(cursor.element());
			cursor=cursor.getAnterior();
		}
		return l;
	}
	public PositionList<Position<String>> eliminar5(PositionList<String> l,String s){
		PositionList<Position<String>> nueva= new DoubleLinkedList<Position<String>>();
try {
		for(Position<String> p : l.positions()) {
			if(p.element().equals(s)) {
				nueva.addLast(p);
				l.remove(p);		
				}
		}
}catch (InvalidPositionException e) {
	e.getMessage();
}
return nueva;
	}
}
