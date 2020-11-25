package p2;

import p2.algorithms.Algorithm;
import p2.algorithms.BestFirstSearch;

public class AlgorithmFactory {

    public static Algorithm getAlgorithm(String name, Maze maze) {
        if (name == null) return null;
        else if (name.equals("GBFS")) return new BestFirstSearch(maze);

        return null;
    }
}
