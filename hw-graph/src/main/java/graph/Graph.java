package graph;

import java.util.*;


/**
 * <b>Graph</b> represents a mutable, directed graph.
 * <p>
 * @spec.specfield nodes : The nodes (vertices) of the graph
 * <p>
 * @spec.specfield edges : The edges from one node to another with label
 */

public class Graph<T, E> {

    /*
        Rep invariant: Graph != null && nodes != null && edges != null

        Abstraction function:
        graph = {} if empty
        graph = {n=[]} if n is a node but no outgoing edges
        graph = {n=[a(x), b(y),...], b=[...], ...} if n is node connected to node a and b by edge x and y
     */

    // check variable
    private final static boolean CHECKER = false;

    // The graph
    private Map<T, HashSet<Edge<T,E>>> graph;

    /**
     * Creates an empty directed graph with no name
     *
     * @spec.effects constructs an empty directed graph
     */
    public Graph() {
        graph = new HashMap<>();
        checkRep();
    }

    /**
     * Adds node n to the graph
     *
     * @param n node to be added
     * @return true if this graph did not already contain node n,
     * else false
     * @throws IllegalArgumentException if node null or already present
     * @spec.requires n != null and n not in this.nodes
     * @spec.modifies this.nodes
     * @spec.effects adds node n to this.nodes if
     * it is not already present
     */
    public boolean addNode(T n) {
        checkRep();
        if (graph.containsKey(n)) {
            throw new IllegalArgumentException("Node already present");
        }
        if (n == null) {
            throw new IllegalArgumentException("Given node is null");
        }
        graph.put(n, new HashSet<>());
        checkRep();
        return true;
    }

    /**
     * Remove node n from the graph
     *
     * @param n node to be removed
     * @return true if node successfully removed, else false
     * @throws IllegalArgumentException if node null or not in this.nodes
     * @spec.modifies this.nodes
     * @spec.requires n != null and n present in this.nodes
     * @spec.effects removes node n from this.nodes if
     * it is present
     */
    public boolean removeNode(T n) {
        checkRep();
        if (!graph.containsKey(n)) {
            throw new IllegalArgumentException("Input node not in graph");
        }
        if (n == null) {
            throw new IllegalArgumentException("Given node is null");
        }
        graph.remove(n);
        checkRep();
        return true;
    }

    /**
     * Return the set of nodes in the graph.
     *
     * @return a set of nodes
     */
    public Set<T> getNodes() {
        checkRep();
        return new HashSet<>(graph.keySet());
    }

    /**
     * Adds an edge from node from to node to
     * if both nodes exist in graph and the edge does
     * not already exist
     *
     * @param from  origin of the edge
     * @param to    destination of the edge
     * @param label label of the edge
     * @return true if from and to present in graph,
     * and this graph did not already contain edge from
     * <var>from</var> to <var>to</var> with label <var>label</var>
     * otherwise, return false
     * @throws IllegalArgumentException if inputs null, or from and to
     *                                  not present in graph
     * @spec.requires from, to, label != null
     * @spec.modifies this.outgoing_edges
     * @spec.effects Adds edge from <var>from</var> to <var>to</var> with label
     * <var>label</var> to the graph if the same edge is not already
     * present in the graph.
     */
    public boolean addEdge(T from, T to, E label) {
        checkRep();
        if (!graph.containsKey(from)) {
            throw new IllegalArgumentException("Input from not in graph");
        }
        if (!graph.containsKey(to)) {
            throw new IllegalArgumentException("Input to not in graph");
        }
        if (from == null || to == null || label == null) {
            throw new IllegalArgumentException("Some given node is null");
        }

        HashSet<Edge<T,E>> outEdge = graph.get(from);
        Edge<T,E> directEdge = new Edge<>(to, label);
        if (!outEdge.contains(directEdge)) {
            outEdge.add(directEdge);
            checkRep();
            return true;
        }
        return false;
    }

    /**
     * Removes an edge from <var>from</var> to <var>to</var>
     * with label <var>label</var> from the graph and returns
     * the edge. Returns null if the specified edge doesn't exist.
     *
     * @param from  origin of the edge
     * @param to    destination of the edge
     * @param label label of the edge
     * @return the edge removed from the graph, or null
     * if the specified edge doesn't exist or given params are null
     * @throws IllegalArgumentException if any input null, or from and to
     *                                  not present in this.nodes
     * @spec.requires from, to, label != null
     * @spec.modifies this.outgoing_edges
     * @spec.effects removes specified edge from this.outgoing_edges
     */
    public Edge<T,E> removeEdge(T from, T to, E label) {
        checkRep();
        if (from == null || to == null || label == null) {
            throw new IllegalArgumentException("Some given node is null");
        }
        if (!graph.containsKey(from)) {
            throw new IllegalArgumentException("Input from not in graph");
        }
        if (!graph.containsKey(to)) {
            throw new IllegalArgumentException("Input to not in graph");
        }
        Set<Edge<T,E>> outEdge = graph.get(from);
        Edge<T,E> directEdge = new Edge<>(to, label);
        // if the Edge exists, remove it and return what you removed
        if (outEdge.contains(directEdge)) {
            outEdge.remove(directEdge);
            checkRep();
            return directEdge;
        }
        // if it doesn't exist in graph, you removed nothing, so you return null (Nothing)
        return null;
    }

    /**
     * Returns number of edges from one node to another node.
     *
     * @param from origin of the edge
     * @param to   destination of the edge
     * @return number of edges from <var>node1</var> to <var>node2</var>,
     * otherwise return null
     * @throws IllegalArgumentException if any parameter null, or
     *                                  if from or to not present in graph
     * @spec.requires from, to != null
     */
    public int numberOfEdges(T from, T to) {
        checkRep();
        if (!graph.containsKey(from)) {
            throw new IllegalArgumentException("Input from not in graph");
        }
        if (!graph.containsKey(to)) {
            throw new IllegalArgumentException("Input to not in graph");
        }
        if (from == null || to == null) {
            throw new IllegalArgumentException("Some given node is null");
        }
        int tick = 0;
        Set<Edge<T,E>> children = graph.get(from);
        for (Edge<T,E> x : children) {
            if (x.getDest().equals(to)) {
                tick++;
            }
        }
        checkRep();
        return tick;
    }

    /**
     * Return true if node <var>n</var> is in the graph.
     *
     * @param n a node
     * @return true if node <var>n</var> is in this.nodes,
     * else return false
     * @throws IllegalArgumentException if n is null
     * @spec.requires n != null
     */
    public boolean containsNode(T n) {
        checkRep();
        if (n == null) {
            throw new IllegalArgumentException("Given node is null");
        }
        return graph.containsKey(n);
    }

    /**
     * Returns number of nodes in the graph.
     *
     * @return number of nodes in the graph
     */
    public int size() {
        checkRep();
        return graph.size();
    }

    /**
     * Returns true if the graph is empty.
     *
     * @return true if the graph is empty
     */
    public boolean isEmpty() {
        checkRep();
        return graph.isEmpty();
    }

    /**
     * Returns a set of outgoing edges of node <var>n</var>.
     *
     * @param n a node
     * @return a set of outgoing edges of node <var>n</var>
     * @throws IllegalArgumentException if node <var>n</var>
     *                                  is not in this.nodes
     * @spec.requires n != null
     */
    public Set<Edge<T,E>> childrenOf(T n) {
        checkRep();
        if (n == null) {
            throw new IllegalArgumentException("Given node is null");
        }
        if (!(containsNode(n))) {
            throw new IllegalArgumentException("Given node not present in graph");
        }
        HashSet<Edge<T,E>> x = graph.get(n);
        checkRep();
        return new HashSet<>(x);
    }

    /**
     * Check to see if Rep Invariant holds
     */
    public void checkRep() {
        if (CHECKER) {
            Set<T> nodes = graph.keySet();
            // check node nullness
            for (T n : nodes) {
                assert (n != null);
                HashSet<Edge<T,E>> nodeEdges = graph.get(n);
                // check Edge nullness
                for (Edge<T,E> x : nodeEdges) {
                    assert x != null;
                    // check destination of Edge exists
                    assert graph.containsKey(x.getDest());
                }
            }
        }
        // check graph nullness
        assert (graph != null);
    }
}



