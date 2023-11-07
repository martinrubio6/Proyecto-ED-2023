package todo;

import java.util.Iterator;
import java.util.NoSuchElementException;

import excepciones.BoundaryViolationException;
import excepciones.EmptyListException;
import excepciones.InvalidPositionException;


/**
* @author C�tedra de Estructuras de Datos, Departamento de Cs. e Ing. de la Computaci�n, UNS.
*/

public class ElementIterator<E> implements Iterator<E>{
    protected PositionList<E> lista;
    protected Position<E> cursor;
    
    /**
     * Crea un nuevo iterador para recorrer los elementos de la lista dada.
     *
     * @param L La lista a recorrer.
     */
    public ElementIterator(PositionList<E> L) {
        lista = L;
        try {
        if(lista.isEmpty())
        	cursor=null;
        else
        	cursor=lista.first();
        } catch (EmptyListException e) {
            e.getMessage();
        }
    }
    
    /**
     * Verifica si hay más elementos en la lista para iterar.
     *
     * @return true si hay más elementos, false de lo contrario.
     */
    public boolean hasNext() {
        return cursor != null;
    }
    
    /**
     * Devuelve el siguiente elemento en la iteración.
     *
     * @return El siguiente elemento en la iteración.
     * @throws NoSuchElementException Si no hay más elementos en la iteración.
     */
    public E next() throws NoSuchElementException{
    	E devolver=null;
        try {
            if (cursor == null)
                throw new NoSuchElementException("cursor nulo");
            E aux = cursor.element();
            if(cursor==lista.last())
            cursor=null;
            else
            	cursor=lista.next(cursor);
            devolver=aux;
        } catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
            e.getMessage();
            devolver=null;
        }
        return devolver;
    }
}