package marvel;
import graph.Graph;
import graph.Edge;

import java.util.*;

/**
 * This class contains a method which builds a
 * graph from given file, and a method which
 * finds the shortest past in said graph between
 * two nodes
 */

public class MarvelPaths {
    // Should take the data from marvel.tvs (filename) and create a new graph with it
    // Contain a method which finds the shortest path from one character to another
    // Write a main method
    /*
        There is no Rep invariants, abstraction functions
        or check reps for this class
     */

    /**
     * Builds graph using data from given file
     *
     * @param filename file which graph is build from
     * @spec.requires filename != null
     * @throws IllegalArgumentException if file == null
     * @return the built graph
     */
    public static Graph<String,String> buildGraph(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }
        Iterator<ConnectionModel> csvUserIterator = MarvelParser.parseData(filename);
        Graph<String,String> marvelGraph = new Graph<>();
        HashSet<String> characters = new HashSet<>();
        HashMap<String, List<String>> books = new HashMap<>();

        // run through the entire file by each line
        while (csvUserIterator.hasNext()) {

            ConnectionModel connection = csvUserIterator.next();
            // add the nodes to a set called characters
            characters.add(connection.getCharacter());

            // add each book as a KEY in the map books and has an ArrayList
            // which stores all the characters connected to said book

            if (!books.containsKey(connection.getBook())) {
                // If the book doesn't exist in my map of books
                // add it with an empty ArrayList as my values
                books.put(connection.getBook(), new ArrayList<>());
            }
            // add the hero to respective books list. The ArrayList to which
            // the hero is added to represents ALL the heroes that share that
            // specific book
            books.get(connection.getBook()).add(connection.getCharacter());
        }
        // at the end of this while loop, I will have a set of characters with
        // all the heroes and a hash map containing all the books along with every
        // hero referenced in said book.

        // add every character in the set of characters as individual nodes to my
        // marvelGraph
        for (String character : characters) {
            marvelGraph.addNode(character);
        }

        // connect the characters to other characters who share books
        // for each book in the set of keys
        // ie, for each String book, from the Set of String books
        for (String book : books.keySet()) {
            // create a list of chars equal to the list under each key
            // chars represents all characters under said book
            List<String> chars = books.get(book);
            int ticker = 1;

            // for each character char1 in the list of chars
            for (String charStart : chars) {
                // create a list containing all the characters ahead of
                // the charStart up to chars size
                List<String> chars_sub = chars.subList(ticker, chars.size());
                // for each charEnd in the sublist
                for (String charEnd : chars_sub) {
                    // check to see if they are the same hero
                    if (!(charStart.equals(charEnd))) {
                        // otherwise, create 2 edges, one going from charStart to charEnd
                        // and another going from charEnd to start, both with same book label
                        marvelGraph.addEdge(charStart, charEnd, book);
                        marvelGraph.addEdge(charEnd, charStart, book);
                    }
                }
                // once all the edges are created, increase the ticker, which makes sure that
                // the sublist is created from all characters AHEAD of the charStart
                ticker++;
            }
        }
        // by now all edges and nodes should be present under all the correct
        // labels, etc.
        // So now return the created graph
        return marvelGraph;
    }


    /**
     * Finds shortest path between 2 characters using BFS
     *
     * @param graph graph to be used to find shortest path
     * @param start a character
     * @param end a character
     * @spec.requires graph, start and dest != null
     * @return the shortest path, and nothing if
     * no paths exists
     * @throws IllegalArgumentException if graph, start or dest == null
     * or if start or dest not in graph
     */
    public static List<Edge<String,String>> BFSearch(Graph<String,String> graph, String start, String end) {

        // make sure all the nulls and contains are taken care of
        if (graph == null)
            throw new IllegalArgumentException("graph cannot be null");

        if (start == null || end == null)
            throw new IllegalArgumentException("start or dest cannot be null");

        if (!(graph.containsNode(start)))
            throw new IllegalArgumentException("Character " + start +
                    " is not in the graph.");

        if (!(graph.containsNode(end)))
            throw new IllegalArgumentException("Character " + end +
                    " is not in the graph.");

        // nodes to visit: initially empty
        LinkedList<String> workList = new LinkedList<>();

        // maps from nodes to paths: initially empty
        HashMap<String, List<Edge<String,String>>> paths = new HashMap<>();

        paths.put(start, new ArrayList<>());
        // add pathStart to the queue "workList"
        workList.add(start);
        // add pathStart with empty ArrayList to map

        // while workList (queue) isn't empty
        while(!workList.isEmpty()) {

            // dequeue the next node (character)
            String character = workList.removeFirst();

            // if character is equal to the destination
            if (character.equals(end) ) {
                // get the path associated with character in paths
                List<Edge<String,String>> path = paths.get(character);
                // copy it
                // create the comparator which I will use to sort
                // sort it to return lexicographically least path
                return new ArrayList<>(path);
            }
            // set in which all the children edges will be held
            Comparator<Edge<String,String>> edgeComparator = (o1, o2) -> {
                if (!o1.getDest().equals(o2.getDest())) {
                    return o1.getDest().compareTo(o2.getDest());
                } else if (!o1.getLabel().equals(o2.getLabel())){
                    return o1.getLabel().compareTo(o2.getLabel());
                } return 0;
            };
            Set<Edge<String, String>> edges = new TreeSet<>(edgeComparator);
            edges.addAll(graph.childrenOf(character));
            for (Edge<String,String> e : edges) {
                String destination = e.getDest();

                // checks to see if destination has been visited
                if (!(paths.containsKey(destination))) {
                    // let path be the path character maps to in paths
                    List<Edge<String,String>> path = paths.get(character);
                    // create post path, which includes previous path
                    List<Edge<String,String>> post_path = new ArrayList<>(path);
                    // now add e to post path, which makes post path
                    // the path formed by appending e to original path
                    post_path.add(e);
                    // add key dest with values post_path to map
                    // which means the path to dest is written in
                    // post path
                    paths.put(destination,post_path);
                    // add the dest (m) back into workList
                    workList.add(destination);

                }
            }
        }
        return null;

    }

    /**
     * Allows user to find the shortest path between two
     * characters that they input
     *
     * @param args the args
     */

    public static void main(String[] args) {


        Graph<String,String> marvelGraph = MarvelPaths.buildGraph("marvel.tsv");
        // reader which reads what is input
        Scanner reader = new Scanner(System.in);
        String start, end;
        boolean continueChecker;
        System.out.println("Find the shortest path between 2 marvel heroes\n" +
                " ********************************************");
        do {
            System.out.println("Type in first hero: ");
            start = reader.nextLine();
            System.out.println("Type in second hero: ");
            end = reader.nextLine();

            if (!((marvelGraph.containsNode(start)) || (marvelGraph.containsNode(end)))){
                System.out.println("Character " + start +" doesn't exist in provided Graph");
                System.out.println("Character " + end +" doesn't exist in provided Graph");
            }else if (!(marvelGraph.containsNode(start))) {
                System.out.println("Character " + start + " doesn't exist in provided Graph");
            }else if (!(marvelGraph.containsNode(end))) {
                System.out.println("Character " + end + " doesn't exist in provided Graph");
            }else if (marvelGraph.containsNode(start) && marvelGraph.containsNode(end)) {
                System.out.println(" ********************************************\nGreat choice of heroes!!");
                String currentNode = start;
                StringBuilder result = new StringBuilder("path from " + start + " to " + end + ":");
                List<Edge<String,String>> path = MarvelPaths.BFSearch(marvelGraph,start,end);

                if (path == null) {
                    result.append("\n" + "no path found");
                } else {
                    for (Edge<String,String> Edge : path) {
                        result.append("\n").append(currentNode).append(" to ").append(Edge.getDest()).append(" via ").append(Edge.getLabel());
                        currentNode = Edge.getDest();
                    }
                }
                System.out.println(result);

            }
            // repeating to see if they want to check another program
            System.out.println("Would you like to check another? ");
            String answer = reader.nextLine();
            answer = answer.toLowerCase();
            continueChecker = answer.length() != 0 && answer.charAt(0) == 'y';

        } while(continueChecker);

        System.out.println(" ********************************************\nExiting program, have a nice day :)");
        reader.close();

    }
}
