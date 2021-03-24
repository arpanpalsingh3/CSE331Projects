package pathfinder.implTest;

import graph.Graph;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import pathfinder.ShortestPath;
import pathfinder.datastructures.Path;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestShortestPath {

    @Rule public Timeout GLOBAL_TIMEOUT = Timeout.seconds(10); // 10 seconds max per method tested

    Graph<String,Double> testGraph = new Graph<>();
    HashSet<String> nodes = new HashSet<>();
    // All nodes present in graph
    String A = "a";
    String B = "b";
    String C = "c";
    String D = "d";
    String E = "e";
    String F = "f";
    // All edges present in graph

    @Before
    public void setUp() {
        testGraph.addNode(A);
        testGraph.addNode(B);
        testGraph.addNode(C);
        testGraph.addNode(D);
        testGraph.addNode(E);
        testGraph.addNode(F);
        testGraph.addEdge(A,B,1.23);
        testGraph.addEdge(B,A,1.23);
        testGraph.addEdge(A,D,1.99);
        testGraph.addEdge(D,A,1.99);
        testGraph.addEdge(B,F,2.77);
        testGraph.addEdge(F,B,2.77);
        testGraph.addEdge(B,D,.77);
        testGraph.addEdge(D,B,.77);
        testGraph.addEdge(B,E,.77);
        testGraph.addEdge(E,B,.77);
        testGraph.addEdge(D,E,2.31);
        testGraph.addEdge(E,D,2.31);
        testGraph.addEdge(E,F,1.99);
        testGraph.addEdge(F,E,1.99);
        nodes.add(A);
        nodes.add(B);
        nodes.add(C);
        nodes.add(D);
        nodes.add(E);
        nodes.add(F);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testShortestPathNulls() {
        ShortestPath.findShortestPath(testGraph,null,null);
        ShortestPath.findShortestPath(testGraph,A,null);
        ShortestPath.findShortestPath(testGraph,null,A);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testShortestPathNonExist() {
        String G = "g";
        String H = "h";
        ShortestPath.findShortestPath(testGraph,G,H);
        ShortestPath.findShortestPath(testGraph,A,G);
        ShortestPath.findShortestPath(testGraph,G,A);
    }

    @Test
    public void testShortestPathOneHop() {
        Path<String> PATH_A = new Path<>(A);
        Path<String> PATH_AB = PATH_A.extend(B,1.23);
        assertEquals(PATH_AB,ShortestPath.findShortestPath(testGraph,A,B));
    }

    @Test
    public void testShortestPathOneHopOver2() {
        Path<String> PATH_A = new Path<>(A);
        Path<String> PATH_AD = PATH_A.extend(D,1.99);
        assertEquals(PATH_AD,ShortestPath.findShortestPath(testGraph,A,D));
    }

    @Test
    public void testShortestPathTwoHop() {
        Path<String> PATH_A = new Path<>(A);
        Path<String> PATH_AB = PATH_A.extend(B,1.23);
        Path<String> PATH_AE = PATH_AB.extend(E,.77);
        assertEquals(PATH_AE,ShortestPath.findShortestPath(testGraph,A,E));
    }

    @Test
    public void testShortestPathThreeHop() {
        Path<String> PATH_A = new Path<>(A);
        Path<String> PATH_AB = PATH_A.extend(B,1.23);
        Path<String> PATH_AE = PATH_AB.extend(E,.77);
        Path<String> PATH_AF = PATH_AE.extend(F,1.99);
        assertEquals(PATH_AF,ShortestPath.findShortestPath(testGraph,A,F));
    }

    @Test
    public void testShortestPathNoPath() {
        Path<String> PATH_A = new Path<>(A);
        Path<String> PATH_B = new Path<>(B);
        Path<String> PATH_C = new Path<>(C);
        Path<String> PATH_D = new Path<>(D);
        Path<String> PATH_E = new Path<>(E);
        Path<String> PATH_F = new Path<>(F);

        assertEquals(PATH_A,ShortestPath.findShortestPath(testGraph,A,C));
        assertEquals(PATH_B,ShortestPath.findShortestPath(testGraph,B,C));
        assertEquals(PATH_D,ShortestPath.findShortestPath(testGraph,D,C));
        assertEquals(PATH_E,ShortestPath.findShortestPath(testGraph,E,C));
        assertEquals(PATH_F,ShortestPath.findShortestPath(testGraph,F,C));
        // FROM C TO THE REST
        assertEquals(PATH_C,ShortestPath.findShortestPath(testGraph,C,A));
        assertEquals(PATH_C,ShortestPath.findShortestPath(testGraph,C,B));
        assertEquals(PATH_C,ShortestPath.findShortestPath(testGraph,C,D));
        assertEquals(PATH_C,ShortestPath.findShortestPath(testGraph,C,E));
        assertEquals(PATH_C,ShortestPath.findShortestPath(testGraph,C,F));

    }

    @Test
    public void testShortestPathToItself() {
        Path<String> PATH_A = new Path<>(A);
        Path<String> PATH_B = new Path<>(B);
        Path<String> PATH_C = new Path<>(C);
        Path<String> PATH_D = new Path<>(D);
        Path<String> PATH_E = new Path<>(E);
        Path<String> PATH_F = new Path<>(F);

        assertEquals(PATH_A,ShortestPath.findShortestPath(testGraph,A,A));
        assertEquals(PATH_B,ShortestPath.findShortestPath(testGraph,B,B));
        assertEquals(PATH_C,ShortestPath.findShortestPath(testGraph,C,C));
        assertEquals(PATH_D,ShortestPath.findShortestPath(testGraph,D,D));
        assertEquals(PATH_E,ShortestPath.findShortestPath(testGraph,E,E));
        assertEquals(PATH_F,ShortestPath.findShortestPath(testGraph,F,F));
    }

}
