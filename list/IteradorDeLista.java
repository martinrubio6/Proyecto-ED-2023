package list;

import java.util.Iterator;


public class IteradorDeLista<E> implements Iterator<E> {

	protected Position<E> actual;
	protected PositionList<E> estructura;

	public IteradorDeLista(PositionList<E> lista) {
		estructura = lista;
		if (!estructura.isEmpty()) {
			try {
				actual = estructura.first();
			} catch (EmptyListException e) {
				e.printStackTrace();

			}
		}
	}

	@Override
	public boolean hasNext() {
		return actual != null;
	}

	@Override
	public E next() {
		E resultado = null;
		if (actual != null) {
			resultado = actual.element();
			try {
				if (actual == estructura.last()) {
					actual = null;
				} else {
					actual = estructura.next(actual);
				}
			} catch (InvalidPositionException | EmptyListException | BoundaryViolationException e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

}
