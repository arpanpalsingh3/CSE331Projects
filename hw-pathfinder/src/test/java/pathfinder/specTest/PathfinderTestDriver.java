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

package pathfinder.specTest;

import graph.Edge;
import graph.Graph;
import pathfinder.ShortestPath;
import pathfinder.datastructures.Path;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    public static void main(String[] args) {
        try {
            if(args.length > 1) {
                printUsage();
                return;
            }

            PathfinderTestDriver td;

            if(args.length == 0) {

                td = new PathfinderTestDriver(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
                System.out.println("Running in interactive mode.");
                System.out.println("Type a line in the spec testing language to see the output.");

            } else {

                String fileName = args[0];
                File tests = new File(fileName);

                System.out.println("Reading from the provided file.");
                System.out.println("Writing the output from running those tests to standard out.");
                if(tests.exists() || tests.canRead()) {
                    td = new PathfinderTestDriver(new FileReader(tests), new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;

                }
            }

            td.runTests();

        } catch(IOException e) {

            System.err.println(e.toString());
            e.printStackTrace(System.err);

        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("  Run the gradle 'build' task");
        System.err.println("  Open a terminal at hw-marvel/build/classes/java/test");
        System.err.println("  To read from a file: java graph.specTest.MarvelTestDriver <name of input script>");
        System.err.println("  To read from standard in (interactive): java marvel.specTest.MarvelTestDriver");
    }

    // ***************************
    // ***  JUnit Test Driver  ***
    // ***************************

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, Graph<String,Double>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
        // See GraphTestDriver as an example.
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests()
            throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":

                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new Graph<>());
        output.println("created graph " + graphName);

    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph<String,Double> test = graphs.get(graphName);
        test.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);

    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        Graph<String,Double> test = graphs.get(graphName);
        DecimalFormat df = new DecimalFormat("0.000");
        double y = Double.parseDouble(edgeLabel);
        test.addEdge(parentName,childName, y);
        String x = df.format(y);
        output.println("added edge " + x + " from " + parentName +
                " to " + childName + " in " + graphName);

    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        Graph<String,Double> test = graphs.get(graphName);
        StringBuilder out = new StringBuilder(graphName + " contains:");

        List<String> nodes = new ArrayList<>(test.getNodes());
        Collections.sort(nodes);
        for (String node : nodes) {
            out.append(" ").append(node);
        }
        output.println(out);

    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        Graph<String,Double> test = graphs.get(graphName);
        StringBuilder out = new StringBuilder("the children of " + parentName + " in " + graphName + " are:");

        List<Edge<String,Double>> edges = new ArrayList<>(test.childrenOf(parentName));

        Comparator<Edge<String,Double>> edgeComparator = (o1, o2) -> {
            if (!o1.getLabel().equals(o2.getLabel())) {
                return o1.getLabel().compareTo(o2.getLabel());
            } else {
                return o1.getDest().compareTo(o2.getDest());
            }
        };

        edges.sort(edgeComparator);

        for (Edge<String,Double> edge : edges) {
            out.append(" ").append(edge);
        }
        output.println(out);


    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to findPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String start = arguments.get(1).replace("_"," ");
        String dest = arguments.get(2).replace("_"," ");

        findPath(graphName, start, dest);
    }

    private void findPath (String graphName, String start, String dest) {
        DecimalFormat df = new DecimalFormat("0.000");
        Graph<String,Double> pathGraph =graphs.get(graphName);
        if ((!pathGraph.containsNode(start)) && (!pathGraph.containsNode(dest))) {
            output.println("unknown node " + start);
            output.println("unknown node " + dest);
        } else if (!(pathGraph.containsNode(start))) {
            output.println("unknown node " + start);
        } else if (!(pathGraph.containsNode(dest))) {
            output.println("unknown node " + dest);
        } else {
            Path<String> minPath = ShortestPath.findShortestPath(pathGraph,start,dest);
            StringBuilder result = new StringBuilder("path from " + start + " to " + dest + ":");

            for (Path<String>.Segment a : minPath) {
                result.append("\n").append(a.getStart()).append(" to ").append(a.getEnd()).append(" with weight ").append(df.format(a.getCost()));

            }
            output.println(result + "\n" + "total cost: " + df.format(minPath.getCost()));

        }

    }



    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }

}
