package graph;

/**
 * <b>edge</b> represents a immutable edge
 */
public class Edge<T,E> {

    /* Rep invariant:
         dest != null && label != null
     */

    //check variable
    private final static boolean CHECKER = false;

    private final T dest;
    private final E label;

    /**
     * Creates an edge
     *
     * @param dest destination of the edge
     * @param label label of the edge
     * @spec.requires d != null and l != null
     * @spec.effects constructs an edge with
     * dest and label
     */
    public Edge(T dest, E label) {
        if (dest == null || label == null) {
            throw new IllegalArgumentException("Given parameters are null");
        }
        this.dest = dest;
        this.label = label;
        checkRep();
    }

    /**
     * Returns the destination of this edge.
     *
     * @return the destination of this edge
     */
    public T getDest() {
        checkRep();
        return dest;
    }

    /**
     * Returns the label of this edge.
     *
     * @return the label of this edge
     */
    public E getLabel() {
        checkRep();
        return label;
    }

    /**
     * Checks if representation invariant holds.
     */
    public void checkRep() {
        assert !CHECKER || (dest != null && label != null);
    }

    // equals override
    @Override
    public boolean equals( Object o) {
        if (!(o instanceof graph.Edge))
            return false;

        graph.Edge y = (graph.Edge) o;
        return dest.equals(y.dest) && label.equals(y.label);
    }

    // hashcode override
    @Override
    public int hashCode() {
        return 31 + dest.hashCode() + label.hashCode();
    }


    @Override
    public String toString() {
        checkRep();
        String result = dest + "(" + label + ")";
        checkRep();
        return result;
    }


}

