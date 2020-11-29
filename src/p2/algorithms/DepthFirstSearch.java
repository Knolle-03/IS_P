package p2.algorithms;

import p2.Cell;
import p2.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DepthFirstSearch implements Algorithm {

    Maze maze;
    List<Cell> openList = new ArrayList<>();
    List<Cell> closedList = new ArrayList<>();
    Cell current;
    Cell lastCurrent;
    Cell start;
    Cell target;
    int calcNextStepCalls = 0;

    public DepthFirstSearch(Maze maze) {
        this.maze = maze;
        current = maze.getCells().get(0);
        target = maze.getCells().get(maze.getCells().size() - 1);
        start = current;
        lastCurrent = current;
        start.setCostOfReach(0);
        start.setInOpenList(true);
        start.setCurrent(true);
        openList.add(start);
    }

    @Override
    public void calcNextStep() {
        calcNextStepCalls++;
        if (current == target) {
            maze.setSolved(true);
            lastCurrent.setCurrent(false);
            current.setCurrent(true);
            current.setInClosedList(true);
            current.setInOpenList(false);
            System.out.println("MAZE SOLVED!!!");
        } else {
            System.out.println("Current:" + current.toString());
            current.setInOpenList(false);
            current.setInClosedList(true);
            current.setCurrent(true);
            lastCurrent.setCurrent(false);
            lastCurrent = current;
            openList.remove(current);
            closedList.add(current);
            List<Cell> neighboursNotChecked = maze.getReachableNeighbours(current);
            List<Cell> neighbours = new ArrayList<>();
            for (Cell neighbour : neighboursNotChecked) {
                if (!neighbour.isInClosedList()) {
                    neighbours.add(neighbour);
                }
            }
            System.out.println("Neighbours:" + neighbours.toString());
            if (neighbours.isEmpty()) {
                current = openList.get(openList.size()-1);
            } else {
                current = neighbours.get((new Random()).nextInt(neighbours.size()));
                neighbours.remove(current);
                current.setInOpenList(true);
                for (Cell neighbour : neighbours) {
                    neighbour.setInOpenList(true);
                    if (!openList.contains(neighbour)) {
                        openList.add(neighbour);
                    }
                }
            }
        }
    }

    @Override
    public List<Cell> getCurrentCells() {
        List<Cell> current = new ArrayList<>();
        current.add(this.current);
        return current;
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
        return "Depth First Search";
    }

    public String getInfo() {
        return getName() + "\n\n" +
                "Total cell count: " + maze.getCells().size() + "\n" +
                "calcNextStep calls: " + calcNextStepCalls + "\n" +
                "Open List Size: " + openList.size() + "\n" +
                "Closed List Size: " + closedList.size() + "\n";
    }
}
