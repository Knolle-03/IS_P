package p2.algorithms;

import p2.Cell;
import p2.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class IterativeDeepeningAStar implements Algorithm {


    int depthBound;
    int maxDepthBound;
    Maze maze;
    List<Cell> openList = new ArrayList<>();
    List<Cell> closedList = new ArrayList<>();
    Cell current;
    Cell start;
    Cell target;
    int bound;
    int result;
    int FOUND = -42;
    Stack<Cell> path = new Stack<>();



    public IterativeDeepeningAStar(Maze maze) {
        this.maze = maze;
        start = maze.getCells().get(0);
        target = maze.getCells().get(maze.getCells().size() - 1);
        bound = start.getEstimatedTotalCost();
        maxDepthBound = start.getEstimatedTotalCost() * 10;
        current = start;
        path.push(start);

    }


    @Override
    public void calcNextStep() {
        result = search(path, 0, bound);
        if (result == FOUND) {
            for (Cell cell : path) cell.setPartOfPath(true);
            maze.setSolved(true);
        }
        if (result == Integer.MAX_VALUE) System.out.println("No Solution found");
        bound = result;
    }

    public int search(Stack<Cell> path, int g, int bound) {
        current = path.get(path.size() - 1);
        int f = current.getEstimatedTotalCost();
        if (f > bound) return f;
        if (current.equals(target)) return FOUND;
        int min = Integer.MAX_VALUE;
        for (Cell successor : maze.getReachableNeighbours(current)) {
            if (!path.contains(successor)) {
                path.push(successor);
                int t = search(path, g + 1,bound);
                if (t == FOUND) return FOUND;
                if (t < min) min = t;
                path.pop();
            }
        }
        return min;
    }

    @Override
    public List<Cell> getCurrentCells() {
        return null;
    }

    @Override
    public void solve() {
        while (!maze.isSolved()) {
            calcNextStep();
        }
    }
}
