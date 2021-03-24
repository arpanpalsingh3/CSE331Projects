/*
 * Copyright (C) 2020 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder;

import graph.Edge;
import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;

import java.util.*;

import static pathfinder.parser.CampusPathsParser.parseCampusBuildings;
import static pathfinder.parser.CampusPathsParser.parseCampusPaths;

/**
 * CampusMap is an immutable representation of the UW CampusMap through
 * the uses of a directed Graph ADT
 *
 * @spec.specfield nodes are represented with points on the map
 *
 * @spec.specfield edges are weighted distances between points
 */
public class CampusMap implements ModelAPI {

    private final static boolean CHECKER = false;

    /*
        Rep Invariant: Graph != null && Nodes != null
        && edges != null

        Abstraction Function:
            AF(this) = a Graph, graph, such that
            graph = {} if empty
            graph = {n=[]} if n is a node but no outgoing edges
            graph = {n=[a(x), b(y),...], b=[...], ...} if n is node connected to node a and b by edge x and y

        Essentially, it's a GraphADT, but with only observer methods
     */

    final static List<CampusBuilding> buildsSet =  parseCampusBuildings("campus_buildings.tsv");
    Graph<Point, Double> campusMap = new Graph<>();

    /**
     * Creates a CampusMap which contains all nodes and paths
     * on the UW campus
     *
     * @spec.effects creates a completed Graph of UW Campus
     */
    public CampusMap() {
        List<CampusPath> paths = parseCampusPaths("campus_paths.tsv");
        // Go through every single path and create the graph
        for (CampusPath path : paths) {
            Point start = new Point(path.getX1(),path.getY1());
            Point end = new Point(path.getX2(),path.getY2());
            if (!campusMap.containsNode(start)) {
                campusMap.addNode(start);
            }
            if (!campusMap.containsNode(end)) {
                campusMap.addNode(end);
            }

            campusMap.addEdge(start,end,path.getDistance());
            campusMap.addEdge(end,start,path.getDistance());
        }
        checkRep();
    }

    /**
     * Private helper method which
     * return a pointer to the graph ADT
     *
     * @return the CampusMap graph object
     */
    // this is only here for the Main method I have at the bottom
    private Graph<Point,Double> getGraph() {
        return this.campusMap;
    }

    @Override
    public boolean shortNameExists(String shortName) {
        for (CampusBuilding b : buildsSet) {

            if (b.getShortName().equals(shortName)) {
                return true;
            }

        }
        return false;
    }

    @Override
    public String longNameForShort(String shortName) {

        for (CampusBuilding b : buildsSet) {

            if (b.getShortName().equals(shortName)) {
                return b.getLongName();
            }

        }throw new IllegalArgumentException("Provided name does not exist");
    }

    @Override
    public Map<String, String> buildingNames() {

        Map<String,String> nameSet = new HashMap<>();

        for(CampusBuilding b : buildsSet) {
            nameSet.put(b.getShortName(),b.getLongName());
        }

        return nameSet;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if (!(this.shortNameExists(startShortName) && this.shortNameExists(endShortName))) {
            for (CampusBuilding b : buildsSet) {

                if (b.getLongName().equals(startShortName)) {
                    startShortName = b.getShortName();
                }
                if (b.getLongName().equals(endShortName)) {
                    endShortName = b.getShortName();
                }
            }
        }

        if (startShortName == null ) {
            throw new IllegalArgumentException("StartShortName is null");
        } else if (endShortName == null) {
            throw new IllegalArgumentException("EndShortName is null");
        }
        if (!shortNameExists(startShortName)) {
            throw new IllegalArgumentException("ShortName " + startShortName + " is not in map" );
        }
        else if (!shortNameExists(endShortName)) {
            throw new IllegalArgumentException("ShortName " + endShortName + " is not in map" );
        }

        double x1=  0;
        double y1 = 0;
        double x2 = 0;
        double y2 = 0;
        // acquire the points of given shortName buildings
        for(CampusBuilding b : buildsSet) {
            if(b.getShortName().equals(startShortName)) {
                x1 = b.getX();
                y1 = b.getY();
            }
            if(b.getShortName().equals(endShortName)) {
                x2 = b.getX();
                y2 = b.getY();
            }
            if (x1 != 0 && x2 != 0 && y1 != 0 && y2 != 0) {
                break;
            }
        }

        Point start = new Point(x1,y1);
        Point end = new Point(x2,y2);
        // call the ShortestPath class method
        return ShortestPath.findShortestPath(campusMap,start,end);

    }

    /**
     * Method for maintaining rep invariant holds
     */
    public void checkRep() {
        assert (campusMap != null);

        if(CHECKER) {
            Set<Point> nodes = campusMap.getNodes();
            for(Point node : nodes) {
                assert (node != null);
                Set<Edge<Point,Double>> edges = campusMap.childrenOf(node);
                for(Edge<Point,Double> edge : edges) {
                    assert(edge != null);
                    assert (edge.getDest() != null);
                    assert(edge.getLabel() != null);
                }

            }
        }

    }

    /*
        These are all print statements I was writing while building my
        CampusMap so that I could get a good idea of what parts of the
        class were working, and what was broken. These are obsolete now
        because I have written test suits, but I have kept them here because
        it doesn't hurt.

        The first few print statements just check to make sure all the bulding
        names are printed out properly

        After which it just prints out 2000+ lines making sure every node is present
        in the map and a total count of all nodes

        Lastly, I have a few system outs for shortest paths.

        ** IF THIS SHOULDN"T BE HERE OR IT SHOULD BE REMOVED, LET ME KNOW **

     */
    public static void main(String[] args) {

        CampusMap uwCampusMap = new CampusMap();
        System.out.println("Check to see uwCampusMap works\n ************************");

        List<CampusBuilding> testBuildings = parseCampusBuildings("campus_buildings.tsv");

        if (uwCampusMap.shortNameExists("BAG")) {
            System.out.println(uwCampusMap.longNameForShort("BAG"));
        }

        if (uwCampusMap.shortNameExists("MGH (SW)")) {
            System.out.println(uwCampusMap.longNameForShort("MGH (SW)"));
        } else {
            System.out.println("MGH (SW) does not exist in map");
        }
        System.out.println("*********************************************");

        for(CampusBuilding test : testBuildings) {
            if (uwCampusMap.shortNameExists(test.getShortName())) {
                System.out.println("Short name " + test.getShortName() + " exists in uwCampusMap" +
                        "and its long name is: " + test.getLongName());
            }
            Point testb = new Point(test.getX(),test.getY());
            if (uwCampusMap.getGraph().containsNode(testb)) {
                System.out.println("Point " + testb + " exists in uwCampusMap");
            } else {
                System.out.println("Point " + testb + " doesn't exist in uwCampusMap");
            }
        }

        Set<Point> massPoints = new HashSet<>();
        List<CampusPath> paths = parseCampusPaths("campus_paths.tsv");
        for(CampusPath p : paths) {
            Point q = new Point(p.getX1(),p.getY1());
            massPoints.add(q);
        }
        int ticker = 1;
        for(Point bam : massPoints) {
            if(uwCampusMap.getGraph().containsNode(bam)) {
                System.out.println(bam + " " + ticker);
                ticker++;
            }
        }
        System.out.println(uwCampusMap.getGraph().size());
        Path<Point> KNE_MGH = uwCampusMap.findShortestPath("KNE", "MGH");
        System.out.println(KNE_MGH.toString());
        System.out.println(KNE_MGH.getCost());
        Path<Point> KNE_MGH_SW = uwCampusMap.findShortestPath("KNE", "MGH (SW)");
        System.out.println(KNE_MGH_SW.toString());
        System.out.println(KNE_MGH_SW.getCost());

        Path<Point> KNE_KNE_E = uwCampusMap.findShortestPath("KNE", "KNE (E)");
        System.out.println(KNE_KNE_E.toString());
        System.out.println(KNE_KNE_E.getCost());

        Path<Point> KNE_KNE_SE = uwCampusMap.findShortestPath("KNE", "KNE (SE)");
        System.out.println(KNE_KNE_SE.toString());
        System.out.println(KNE_KNE_SE.getCost());

        Path<Point> KNE_KNE_S = uwCampusMap.findShortestPath("KNE", "KNE (S)");
        System.out.println(KNE_KNE_S.toString());
        System.out.println(KNE_KNE_S.getCost());

        Path<Point> KNE_KNE_SW = uwCampusMap.findShortestPath("KNE", "KNE (SW)");
        System.out.println(KNE_KNE_SW.toString());
        System.out.println(KNE_KNE_SW.getCost());

        Set<Point> nodes = uwCampusMap.getGraph().getNodes();
        int edgeCounter = 0;
        for (Point node : nodes) {
            Set<Edge<Point, Double>> edges = uwCampusMap.getGraph().childrenOf(node);
            for (Edge<Point, Double> ignored : edges) {
                edgeCounter++;
            }
        }
        System.out.println(edgeCounter);

    }
}

