package p2;

import p2.algorithms.Algorithm;
import p2.algorithms.BestFirstSearch;
import p2.algorithms.BreadthFirstSearch;
import p2.algorithms.IterateDeepSearch;

public class AlgorithmFactory {

    public static Algorithm getAlgorithm(String name, Maze maze) {
        if (name == null) return null;
        else if (name.equals("BestFS")) return new BestFirstSearch(maze);
        else if (name.equals("BreadthFS")) return new BreadthFirstSearch(maze);
        else if (name.equals("IDS")) return new IterateDeepSearch(maze);
        else if (name.equals("IDSWR")) return new IterateDeepSearch(maze);

        return null;
    }
}
