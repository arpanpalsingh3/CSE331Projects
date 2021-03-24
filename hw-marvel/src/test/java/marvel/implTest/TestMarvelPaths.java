package marvel.implTest;

import graph.Edge;
import graph.Graph;
import marvel.ConnectionModel;
import marvel.MarvelParser;
import marvel.MarvelPaths;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestMarvelPaths {

    @Rule public Timeout GLOBAL_TIMEOUT = Timeout.seconds(10); // 10 seconds max per method tested

    private Graph<String,String> testGraph;
    private Graph<String,String> testMarvelGraph;
    // initial set of my variables
    private final String ernst = "Ernst-the-Bicycling-Wizard";
    private final String notkin = "Notkin-of-the-Superhuman-Beard";
    private final String perkins = "Perkins-the-Magical-Singing-Instructor";
    private final String grossman = "Grossman-the-Youngest-of-them-all";
    private final String notInGraph = "Not-In-Graph";

    private final Edge<String,String> e_perk = new Edge<>(perkins,"CSE331");
    private final Edge<String,String> e_gross = new Edge<>(grossman,"CSE331");
    private final Edge<String,String> e_not_403 = new Edge<>(notkin, "CSE403");


    @Before
    public void setUp() {
        testGraph = MarvelPaths.buildGraph("staffSuperheroes.tsv");
        testMarvelGraph = MarvelPaths.buildGraph("marvel.tsv");
    }


    @Test (expected = IllegalArgumentException.class)
    public void testNullBuildGraph() {
        MarvelPaths.buildGraph(null);
    }

    @Test
    public void testGraphBuildStaffSuperheroes() {
        Set<Edge<String,String>> childrenOfErnst = new HashSet<>();

        childrenOfErnst.add(e_perk);
        childrenOfErnst.add(e_gross);
        childrenOfErnst.add(e_not_403);

        Set<Edge<String,String>> testChildrenOfErnst = testGraph.childrenOf(ernst);
        assertEquals(testChildrenOfErnst,childrenOfErnst);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBFSearchWithNullGraph() {
        MarvelPaths.BFSearch(null, ernst, perkins);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBFSearchWithNullStart() {
        MarvelPaths.BFSearch(testGraph, null, perkins);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBFSearchWithNullDest() {
        MarvelPaths.BFSearch(testGraph, ernst, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBFSearchStartInputNotInGraph() {
        MarvelPaths.BFSearch(testGraph, notInGraph, perkins);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBFSearchDestInputNotInGraph() {
        MarvelPaths.BFSearch(testGraph, ernst, notInGraph);
    }

    @Test
    public void testBFSearchOneHop() {
        Edge<String,String> testEdge = Objects.requireNonNull(MarvelPaths.BFSearch(testGraph, ernst, perkins)).get(0);
        assertEquals(testEdge, e_perk);
    }

    @Test
    public void testBFSearchWithTwoHopPath() {
        List<Edge<String,String>> testPath = MarvelPaths.BFSearch(testGraph,perkins,notkin);
        Edge<String,String> perkins_ernst = new Edge<>(ernst,"CSE331");
        Edge<String,String> ernst_notkin = new Edge<>(notkin, "CSE403");
        assert testPath != null;
        assertTrue(testPath.contains(perkins_ernst));
        assertTrue(testPath.contains(ernst_notkin));
        // checks the ordering is correct
        assertEquals(testPath.get(0),perkins_ernst);
        assertEquals(testPath.get(1),ernst_notkin);

    }

    @Test
    public void testBFSearch() {
        assertTrue(testMarvelGraph.containsNode("G'RATH"));
        Iterator<ConnectionModel> userIterator = MarvelParser.parseData("marvel.tsv");
        HashSet<String> characters = new HashSet<>();
        while(userIterator.hasNext()) {
            ConnectionModel connection = userIterator.next();
            // add the nodes to a set called characters
            characters.add(connection.getCharacter());
        }

        for(String chars : characters) {
            assertTrue(testMarvelGraph.containsNode(chars));
        }
    }

    @Test
    public void testBFSearchCorrectOutput() {
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        String e = "e";
        String f = "f";
        String boom = "boom";
        Edge<String,String> ac = new Edge<>(c,boom);
        Edge<String,String> cf = new Edge<>(f,boom);
        List<Edge<String,String>> afPath = new ArrayList<>();
        afPath.add(ac);
        afPath.add(cf);
        Graph<String,String> testGraph = new Graph<>();
        testGraph.addNode(a); testGraph.addNode(b); testGraph.addNode(c); testGraph.addNode(d); testGraph.addNode(e); testGraph.addNode(f);
        testGraph.addEdge(a,b,boom);testGraph.addEdge(b,c,boom);testGraph.addEdge(c,d,boom);testGraph.addEdge(d,e,boom);testGraph.addEdge(e,f,boom);
        testGraph.addEdge(a,c,boom);testGraph.addEdge(c,f,boom);
        assertEquals(afPath,MarvelPaths.BFSearch(testGraph,a,f));


    }

}
