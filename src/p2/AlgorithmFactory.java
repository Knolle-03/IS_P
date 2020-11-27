package p2;

import p2.algorithms.*;

public class AlgorithmFactory {

    public static Algorithm getAlgorithm(String name, Maze maze) {
        if (name == null) return null;
        else if (name.equals("BFS")) return new BreadthFirstSearch(maze);
        else if (name.equals("GBFS")) return new GreedyBestFirstSearch(maze);
        else if (name.equals("DFS")) return new DepthFirstSearch(maze);
        else if (name.equals("IDA*")) return new IterativeDeepeningAStar(maze);
        else if (name.equals("A*")) return new AStar(maze);

        return null;
    }
}
