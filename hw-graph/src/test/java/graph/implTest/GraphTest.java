package graph.implTest;

import graph.Graph;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import graph.Edge;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * This class contains a set of test cases to test
 * the implementation of the Graph class.
 */

public final class GraphTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    // these declarations are all the possible Edges between 2 nodes, INCLUDING same Edge with different name
    private final String node_a = "a";
    private final String node_b = "b";
    private final String edge_aa = "aa";
    private final String edge_ab = "ab";
    private final String edge_ba = "ba";
    private final String edge_bb = "bb";
    private final String edge_aax = "aax";
    private final String edge_abx = "abx";
    private final String edge_bax = "bax";
    private final String edge_bbx = "bbx";
    private final Edge<String,String> aa = new Edge<>("a", "aa");
    private final Edge<String, String> ab = new Edge<>("b", "ab");
    private final Edge<String,String> bb = new Edge<>("b", "bb");
    private final Edge<String,String> ba = new Edge<>("a", "ba");
    // Duplicate Edges to same locations, but with different labels
    private final Edge<String,String> aax = new Edge<>("a", "aax");
    private final Edge<String,String> abx = new Edge<>("b", "abx");
    private final Edge<String,String> bbx = new Edge<>("b", "bbx");
    private final Edge<String,String> bax = new Edge<>("a", "bax");
    private final Set<String> emptyNodeSet = new HashSet<>();
    private final Set<String> oneNodeSetA = new HashSet<>();
    private final Set<String> oneNodeSetB = new HashSet<>();
    private final Set<String> twoNodeSet = new HashSet<>();
    private final Set<Edge<String,String>> emptyEdgeSet = new HashSet<>();
    private final Set<Edge<String,String>> oneEdgeSet = new HashSet<>();
    private final Set<Edge<String,String>> twoEdgeSet = new HashSet<>();
    private Graph<String,String> graph = new Graph<>();

    @Before
    public void setUpNodeSets() {
        oneNodeSetA.add(node_a);
        oneNodeSetB.add(node_b);
        twoNodeSet.add(node_a);
        twoNodeSet.add(node_b);

    }

    @Before
    public void setUpEdgeSets() {
        oneEdgeSet.add(ab);
        twoEdgeSet.add(ab);
        twoEdgeSet.add(abx);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Constructor Tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGraph() {
        Graph<String,String> graph = new Graph<>();
        assertEquals(graph.size(), 0);
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Add Nodes tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAddNodeOnlyA() {
        assertTrue(graph.addNode(node_a));

    }

    @Test
    public void testAddNodeOnlyB() {
        assertTrue(graph.addNode(node_b));
    }

    @Test
    public void testAddMultipleNodes() {
        assertTrue(graph.addNode(node_a));
        assertTrue(graph.addNode(node_b));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateAddNodes() {
        graph.addNode(node_a);
        graph.addNode(node_a);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateAddNodesWithTwoNodesA() {
        graph.addNode(node_a);
        graph.addNode(node_b);
        graph.addNode(node_a);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateAddNodesWithTwoNodesB() {
        graph.addNode(node_a);
        graph.addNode(node_b);
        graph.addNode(node_b);
    }

    @Test
    public void testAddedNodesExist() {
        graph.addNode(node_a);
        assertTrue(graph.containsNode(node_a));
        graph.addNode(node_b);
        assertTrue(graph.containsNode(node_a) && graph.containsNode(node_b));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullNodesEmptyGraph() {
        graph.addNode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullNodesNonEmptyGraph() {
        graph.addNode(node_a);
        graph.addNode(null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Remove Nodes Tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testRemoveNodeA() {
        testAddMultipleNodes();
        assertTrue(graph.removeNode(node_a));
    }

    @Test
    public void testRemoveNodeB() {
        testAddMultipleNodes();
        assertTrue(graph.removeNode(node_b));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonExistNodeA() {
        testRemoveNodeA();
        graph.removeNode(node_a);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonExistNodeB() {
        testRemoveNodeB();
        graph.removeNode(node_b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullNode() {
        graph.removeNode(null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Get Nodes Tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetNodesWhenEmpty() {
        assertEquals(emptyNodeSet, graph.getNodes());
    }

    @Test
    public void testGetNodesWhenOneNodeA() {
        setUpNodeSets();
        graph.addNode(node_a);
        assertEquals(oneNodeSetA, graph.getNodes());
    }

    @Test
    public void testGetNodesWhenOneNodeB() {
        setUpNodeSets();
        graph.addNode(node_b);
        assertEquals(oneNodeSetB, graph.getNodes());
    }

    @Test
    public void testGetMultipleNodes() {
        setUpNodeSets();
        testAddMultipleNodes();
        assertEquals(twoNodeSet, graph.getNodes());
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Add Edges Tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAddEdge() {
        testAddMultipleNodes();
        assertTrue(graph.addEdge(node_a, node_b, edge_ab));
    }

    @Test
    public void testAddMultipleEdges() {
        testAddMultipleNodes();
        assertTrue(graph.addEdge(node_a, node_b, edge_ab));
        assertTrue(graph.addEdge(node_b, node_a, edge_ba));
        assertTrue(graph.addEdge(node_a, node_a, edge_aa));
        assertTrue(graph.addEdge(node_b, node_b, edge_bb));

    }

    @Test
    public void testAddMultipleEdgesWithDifferentLabel() {
        testAddMultipleEdges();
        assertTrue(graph.addEdge(node_a, node_b, edge_abx));
        assertTrue(graph.addEdge(node_b, node_a, edge_bax));
        assertTrue(graph.addEdge(node_a, node_a, edge_aax));
        assertTrue(graph.addEdge(node_b, node_b, edge_bbx));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullEdges() {
        testAddMultipleNodes();
        graph.addEdge(node_a, node_b, null);
        graph.addEdge(node_a, null, edge_ab);
        graph.addEdge(null, node_b, edge_ab);
        graph.addEdge(node_a, null, null);
        graph.addEdge(null, node_b, null);
        graph.addEdge(null, null, edge_ab);
        graph.addEdge(null, null, null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Remove Edges tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonExistEdge() {
        graph.removeEdge(node_a, node_b, edge_ab);
    }

    @Test
    public void testRemoveEdges() {
        testAddMultipleEdgesWithDifferentLabel();
        assertEquals(ab, graph.removeEdge(node_a, node_b, edge_ab));
        assertEquals(aa, graph.removeEdge(node_a, node_a, edge_aa));
        assertEquals(ba, graph.removeEdge(node_b, node_a, edge_ba));
        assertEquals(bb, graph.removeEdge(node_b, node_b, edge_bb));
        assertEquals(abx, graph.removeEdge(node_a, node_b, edge_abx));
        assertEquals(aax, graph.removeEdge(node_a, node_a, edge_aax));
        assertEquals(bax, graph.removeEdge(node_b, node_a, edge_bax));
        assertEquals(bbx, graph.removeEdge(node_b, node_b, edge_bbx));
        // graph should now have 0 Edges
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Number of Edges tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testNumberOfEdges() {
        // kinda fit all my tests into as single one

        // tests number of Edges between 2 nodes
        testAddMultipleEdgesWithDifferentLabel();

        // Edges between a and b
        assertEquals(2, graph.numberOfEdges(node_a, node_b));
        graph.removeEdge(node_a, node_b, edge_ab);
        assertEquals(1, graph.numberOfEdges(node_a, node_b));
        graph.removeEdge(node_a, node_b, edge_abx);
        assertEquals(0, graph.numberOfEdges(node_a, node_b));

        // Edges between b and a
        assertEquals(2, graph.numberOfEdges(node_b, node_a));
        graph.removeEdge(node_b, node_a, edge_ba);
        assertEquals(1, graph.numberOfEdges(node_b, node_a));
        graph.removeEdge(node_b, node_a, edge_bax);
        assertEquals(0, graph.numberOfEdges(node_b, node_a));

        // reflexive Edges a a
        assertEquals(2, graph.numberOfEdges(node_a, node_a));
        graph.removeEdge(node_a, node_a, edge_aa);
        assertEquals(1, graph.numberOfEdges(node_a, node_a));
        graph.removeEdge(node_a, node_a, edge_aax);
        assertEquals(0, graph.numberOfEdges(node_a, node_a));

        // reflexive Edges bb
        assertEquals(2, graph.numberOfEdges(node_b, node_b));
        graph.removeEdge(node_b, node_b, edge_bb);
        assertEquals(1, graph.numberOfEdges(node_b, node_b));
        graph.removeEdge(node_b, node_b, edge_bbx);
        assertEquals(0, graph.numberOfEdges(node_b, node_b));

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Contains Tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testContainsNodeWhenEmpty() {
        assertFalse(graph.containsNode(node_a));
        assertFalse(graph.containsNode(node_b));
    }

    @Test
    public void testContainsNodeWhenA() {
        assertFalse(graph.containsNode(node_a));
        graph.addNode(node_a);
        assertTrue(graph.containsNode(node_a));
    }

    @Test
    public void testContainsNodeWhenB() {
        assertFalse(graph.containsNode(node_b));
        graph.addNode(node_b);
        assertTrue(graph.containsNode(node_b));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Size Tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testSizeEmpty() {
        assertEquals(0, graph.size());
    }

    @Test
    public void testSizeOneNode() {
        graph.addNode(node_a);
        assertEquals(1, graph.size());
    }

    @Test
    public void testSizeTwoNode() {
        graph.addNode(node_a);
        assertEquals(1, graph.size());
        graph.addNode(node_b);
        assertEquals(2, graph.size());
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Empty Tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testIsEmptyWhenEmpty() {
        assertTrue(graph.isEmpty());
    }

    @Test
    public void testIsEmptyWhenNotEmptyA() {
        graph.addNode(node_a);
        assertFalse(graph.isEmpty());
    }

    @Test
    public void testIsEmptyWhenNotEmptyB() {
        graph.addNode(node_b);
        assertFalse(graph.isEmpty());
    }

    @Test
    public void testIsEmptyWhenNotEmptyAB() {
        testAddMultipleNodes();
        assertFalse(graph.isEmpty());
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Children Tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void testChildrenOfNullNode() {
        graph.addNode(null);
    }

    @Test
    public void testChildrenOfNodeWithNoEdgesA() {
        testAddNodeOnlyA();
        assertEquals(emptyEdgeSet, graph.childrenOf(node_a));
    }

    @Test
    public void testChildrenOfNodeWithNoEdgesB() {
        testAddNodeOnlyB();
        assertEquals(emptyEdgeSet, graph.childrenOf(node_b));
    }

    @Test
    public void testChildrenOfWithOneEdge() {
        setUpEdgeSets();
        testAddEdge();
        assertEquals(oneEdgeSet, graph.childrenOf(node_a));
    }

    @Test
    public void testChildrenWithTwoEdge() {
        setUpEdgeSets();
        testAddEdge();
        graph.addEdge(node_a, node_b, edge_abx);
        assertEquals(twoEdgeSet, graph.childrenOf(node_a));
    }

}

