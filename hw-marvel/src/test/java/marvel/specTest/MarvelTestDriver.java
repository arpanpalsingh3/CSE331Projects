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

package marvel.specTest;

import graph.Graph;
import graph.Edge;
import marvel.MarvelPaths;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts from
 * files for testing Graph, the Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {

    public static void main(String[] args) {
        try {
            if(args.length > 1) {
                printUsage();
                return;
            }

            MarvelTestDriver td;

            if(args.length == 0) {

                td = new MarvelTestDriver(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
                System.out.println("Running in interactive mode.");
                System.out.println("Type a line in the spec testing language to see the output.");

            } else {

                String fileName = args[0];
                File tests = new File(fileName);

                System.out.println("Reading from the provided file.");
                System.out.println("Writing the output from running those tests to standard out.");
                if(tests.exists() || tests.canRead()) {
                    td = new MarvelTestDriver(new FileReader(tests), new OutputStreamWriter(System.out));
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
    private final Map<String, Graph<String,String>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public MarvelTestDriver(Reader r, Writer w) {
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
                case "LoadGraph":
                    loadGraph(arguments);
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
        Graph<String,String> test = graphs.get(graphName);
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
        Graph<String,String> test = graphs.get(graphName);
        test.addEdge(parentName,childName,edgeLabel);
        output.println("added edge " + edgeLabel + " from " + parentName +
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
        Graph<String,String> test = graphs.get(graphName);
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
        Graph<String,String> test = graphs.get(graphName);
        // get the proper graph
        StringBuilder out = new StringBuilder("the children of " + parentName + " in " + graphName + " are:");
        // the output string
        // the set of edges parent, which represents all the children
        // of the given parent name

        // create a list of edges from the set of edges
        List<Edge<String,String>> edges = new ArrayList<>(test.childrenOf(parentName));
        // create a comparator which compares 2 edges
        Comparator<Edge<String,String>> edgeComparator = (o1, o2) -> {
            if (!o1.getDest().equals(o2.getDest())) {
                return o1.getDest().compareTo(o2.getDest());
            } else if (!o1.getLabel().equals(o2.getLabel())){
                return o1.getLabel().compareTo(o2.getLabel());
            } return 0;
        };
        // sort using the comparator which I had created
        // which properly distinguishes how to sort using
        // destinations and labels
        edges.sort(edgeComparator);
        // add the edges to my output
        for (Edge<String,String> edge : edges) {
            out.append(" ").append(edge);
        }
        // print it
        output.println(out);

        // ___ = graphs.get(graphName);
        // output.println(...);

    }

    private void loadGraph(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to loadGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String filename = arguments.get(1);

        loadGraph(graphName, filename);
    }

    private void loadGraph(String graphName, String filename) {
        graphs.put(graphName, MarvelPaths.buildGraph(filename));
        output.println("loaded graph " + graphName);
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

    private void findPath (String graphName,String start, String dest) {
        Graph<String,String> pathGraph = graphs.get(graphName);
        if ((!pathGraph.containsNode(start)) && (!pathGraph.containsNode(dest))) {
            output.println("unknown character " + start);
            output.println("unknown character " + dest);
        } else if (!(pathGraph.containsNode(start))) {
            output.println("unknown character " + start);
        } else if (!(pathGraph.containsNode(dest))) {
            output.println("unknown character " + dest);
        } else {
            String currentNode = start;
            StringBuilder result = new StringBuilder("path from " + start + " to " + dest + ":");
            List<Edge<String,String>> path = MarvelPaths.BFSearch(pathGraph, start, dest);

            if (path == null) {
                result.append("\n" + "no path found");
            } else {
                for( Edge<String,String> edge : path) {
                    result.append("\n").append(currentNode).append(" to ").append(edge.getDest()).append(" via ").append(edge.getLabel());
                    currentNode = edge.getDest();
                }
            }

            output.println(result);

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

