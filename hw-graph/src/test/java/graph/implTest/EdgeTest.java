package graph.implTest;

import graph.Edge;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class EdgeTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    // these declarations are all the possible Edges between 2 nodes, INCLUDING same Edge with different name
    private final String node_b = "b";
    private final String edge_ab = "ab";
    private final Edge<String,String> ab = new Edge<>("b", "ab");
    // Generic declaration
    private final int node_int = 1;
    private final String edge_1 = "int";
    private final Edge<Integer,String> edge_int = new Edge<>(1, "int");




    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Edge Class Tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testEdgeConstructor() {
        Edge<String,String> test = new Edge<>(node_b,edge_ab);
        assertEquals(ab,test);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNullDestEdgeConstructor() {
        Edge<String,String> test = new Edge<>(node_b,null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNullLabelEdgeConstructor() {
        Edge<String,String> test = new Edge<>(null,edge_ab);
    }

    @Test
    public void testGetDest() {
        Edge<String,String> test = new Edge<>(node_b,edge_ab);
        assertEquals(node_b,test.getDest());
    }

    @Test
    public void testGetLabel() {
        Edge<String,String> test = new Edge<>(node_b,edge_ab);
        assertEquals(edge_ab,test.getLabel());
    }

    @Test
    public void testGenericValuesWork() {
        Edge<Integer,String> test = new Edge<>(node_int,edge_1);
        assertEquals(edge_int,test);
    }

    @Test
    public void testGenericToStringComparison() {
        Edge<Integer,String> test = new Edge<>(node_int,edge_1);
        Edge<String,String> test2 = new Edge<>("1","int");
        assertNotEquals(test, test2);
    }
}


