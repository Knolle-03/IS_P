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
    int bound = 1;
    int result;
    int FOUND = -42;
    int calcNextStepCalls = 0;
    Stack<Cell> path = new Stack<>();



    public IterativeDeepeningAStar(Maze maze) {
        this.maze = maze;
        start = maze.getCells().get(0);
        target = maze.getCells().get(maze.getCells().size() - 1);
        bound = start.getEstimatedTotalCost();
        maxDepthBound = start.getEstimatedTotalCost() * 10;
        current = start;
        path.push(start);
        openList.addAll(maze.getReachableNeighbours(start));
    }


    @Override
    public void calcNextStep() {

//        for (Cell cell : openList) {
//            if (cell.getCostOfReach() <= bound) {
//                List<Cell> neighbours = maze.getReachableNeighbours(cell);
//                for (Cell neighbour : neighbours) {
//                    if (neighbour == target) maze.setSolved(true);
//                    if (!openList.contains(neighbour)) openList.add(neighbour);
//
//                }
//            }
//        }
//        openList.sort(Comparator.comparingInt(Cell::getCostOfReach));
//
//        bound = openList.get(0).getCostOfReach();
//
//
//















        result = search(path, 0, bound);
        if (result == FOUND) {
            for (Cell cell : path) cell.setPartOfPath(true);
            maze.setSolved(true);
        }
        if (result == Integer.MAX_VALUE) System.out.println("No Solution found");
        bound = result;
    }

    public int search(Stack<Cell> path, int g, int bound) {
        calcNextStepCalls++;
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

    @Override
    public Maze getMaze() {
        return maze;
    }

    public String getName() {
        return "Iterative Deepening A*";
    }

    public String getInfo() {
        return getName() + "\n\n" +
                "Total cell count: " + maze.getCells().size() + "\n" +
                "calcNextStep calls: " + calcNextStepCalls + "\n" +
                "Open List Size: " + openList.size() + "\n" +
                "Closed List Size: " + closedList.size() + "\n" +
                "Found Path length: " + path.size();
    }
}
