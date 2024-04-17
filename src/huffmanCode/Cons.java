package huffmanCode;

/**
 * A class representing the cons cell object which is based on the same
 * object from Lisp-based programming languages.
 * @param <E>
 * @param <F>
 */
public class Cons<E, F> {
    private E car;
    private F cdr;

    /**
     * A constructor for the cons cell.
     * @param car
     * @param cdr
     */
    public Cons(E car, F cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    /**
     * Takes the 'car' of the cons cell.
     * @return
     */
    public E car() {
        return car;
    }

    /**
     * Takes the 'cdr' of the cons cell.
     * @return
     */
    public F cdr() {
        return cdr;
    }

    /**
     * Returns the representation of the cons cell
     * using the dotted pair notation from Lisp.
     * @return
     */
    public String toString() {
        return String.format("(%s . %s)", car.toString(), cdr.toString());
    }
}
