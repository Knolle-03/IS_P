package p2.algorithms;

import p2.Cell;
import p2.Maze;

import java.util.*;

public class BreadthFirstSearch implements Algorithm{

    private Maze maze;
    private List<Cell> current = new ArrayList<>();
    private List<Cell> openList = new ArrayList<>();
    private List<Cell> closedList = new ArrayList<>();
    private Queue<Cell> queue;
    private Cell start;
    private Cell target;

    public BreadthFirstSearch(Maze maze) {
        this.maze = maze;
        start = maze.getCells().get(0);
        current.add(start);
        target = maze.getCells().get(maze.getCells().size() - 1);
        queue = new PriorityQueue<>(maze.getCells().size(), (o1, o2) -> 0);
        queue.addAll(current);
        start.setCurrent(true);
        openList.addAll(current);
        start.setInOpenList(true);
    }

    @Override
    public void calcNextStep() {
        Cell next = queue.poll();
        if (next == target)  {
            System.out.println("MAZE SOLVED!!!");
            maze.setSolved(true);
            return;
        }

        List<Cell> successors = maze.getReachableNeighbours(next);
        for (Cell successor : successors) {
            if (!closedList.contains(successor)) {
                queue.add(successor);
                closedList.add(successor);
                successor.setInClosedList(true);
            }
        }

    }

    @Override
    public List<Cell> getCurrentCells() {
        return current;
    }
}
