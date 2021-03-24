package pathfinder;

import graph.Edge;
import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *  This class contains a method which builds the
 *  shortest path based on cost between n start and
 *  an end on a given Graph using Dijkstra's algorithm
 */
public class ShortestPath {
    // No Rep Invariant
    // No Abstraction Function

    /**
     *
     * @param campusMap the given map to search through
     * for shortest path. Nodes can be any type, but the
     * edges must be double.
     * @param start the starting position from which
     * my path starts
     * @param end the end position, which my path ends at
     * @param <T> the generic type declaration.
     * @throws IllegalArgumentException if either of the
     * given nodes to query are null, or don't exist in graph.
     * @return the a Path which contains the shortest path
     * from start to end.
     */
    public static <T> Path<T> findShortestPath(Graph< T, Double> campusMap, T start, T end ) {
        if (start == null ) {
            throw new IllegalArgumentException("Start is null");
        } else if (end == null) {
            throw new IllegalArgumentException("End is null");
        }
        if (!campusMap.containsNode(start)) {
            throw new IllegalArgumentException("ShortName " + start + " is not in map" );
        }
        else if (!campusMap.containsNode(end)) {
            throw new IllegalArgumentException("ShortName " + end + " is not in map" );
        }

        Comparator<Path<T>> pathComparator = Comparator.comparingDouble(Path::getCost);

        Path<T> diPath = new Path<>(start);
        Set<T> finished = new HashSet<>();

        PriorityQueue<Path<T>> costPriority = new PriorityQueue<>(pathComparator);

        costPriority.add(diPath);
        // followed Pseudo-code
        do {
            Path<T> minPath = costPriority.remove();
            T minDest = minPath.getEnd();

            if (minDest.equals(end)) {
                return minPath;
            }

            if (finished.contains(minDest)) {
                continue;
            }

            Set<Edge<T,Double>> minDestChildren = campusMap.childrenOf(minDest);
            for(Edge<T,Double> e : minDestChildren) {
                if (!finished.contains(e.getDest())) {
                    Path<T> newPath = minPath.extend(e.getDest(),e.getLabel());
                    costPriority.add(newPath);
                }
            }

            finished.add(minDest);

        }while(!costPriority.isEmpty());
            return diPath;

    }

}
