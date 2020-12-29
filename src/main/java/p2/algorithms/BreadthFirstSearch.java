package p2.algorithms;

import p2.Cell;
import p2.Maze;

import java.util.*;

public class BreadthFirstSearch implements Algorithm{

    Maze maze;
    Set<Cell> openList = new HashSet<>();
    Set<Cell> closedList = new HashSet<>();
    Cell current;
    Cell start;
    Cell target;
    int calcNextStepCalls = 0;
    Map<Cell, Cell> nextToPrevMap = new HashMap<>();

    public BreadthFirstSearch(Maze maze) {
        this.maze = maze;
        current = maze.getCells().get(0);
        target = maze.getCells().get(maze.getCells().size() - 1);
        start = current;
        start.setCostOfReach(0);
        start.setInOpenList(true);
        start.setCurrent(true);
        openList.add(start);
    }

    @Override
    public void calcNextStep() {
        calcNextStepCalls++;
        for (Cell cell : openList) {
            System.out.println("open list: " + cell.get2dIndex());
        }
        for (Cell cell : closedList) {
            System.out.println("closed list: " + cell.get2dIndex());
            cell.setCurrent(false);
        }
        Set<Cell> newOpenList = new HashSet<>();
        for (Cell cellOfOpenList : openList) {
            if (cellOfOpenList == target) {
                cellOfOpenList.setInOpenList(false);
                cellOfOpenList.setInClosedList(true);
                maze.setSolved(true);
                for (Cell cell : openList) {
                    System.out.println("open list: " + cell.get2dIndex());
                }
                for (Cell cell : closedList) {
                    System.out.println("closed list: " + cell.get2dIndex());
                    cell.setCurrent(false);
                }
                cellOfOpenList.setCurrent(true);
                System.out.println("Current: " + current.get2dIndex());
                System.out.println("MAP: " + nextToPrevMap.toString());
                System.out.println("MAZE SOLVED!!!");
                return;
            }
            System.out.println("Cell:" + cellOfOpenList.toString());
            List<Cell> neighbours = maze.getReachableNeighbours(cellOfOpenList);
            System.out.println("Neighbours:" + neighbours.toString());
            cellOfOpenList.setInOpenList(false);
            cellOfOpenList.setInClosedList(true);
            cellOfOpenList.setCurrent(true);
            closedList.add(cellOfOpenList);
            for (Cell neighbour : neighbours) {
                if (!closedList.contains(neighbour)) {
                    nextToPrevMap.put(cellOfOpenList, neighbour);
                    System.out.println("Add Neighbour to openList:" + neighbours.toString());
                    neighbour.setExplored(true);
                    neighbour.setInOpenList(true);
                    newOpenList.add(neighbour);
                }
            }
        }
        openList = newOpenList;
        for (Cell cell : openList) {
            System.out.println("open list: " + cell.get2dIndex());
        }
        for (Cell cell : closedList) {
            System.out.println("closed list: " + cell.get2dIndex());
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

    @Override
    public String toString() {
        return "BreadthFirstSearch{" + "\n" +
                "maze=" + maze + "\n" +
                ", openList=" + openList + "\n" +
                ", closedList=" + closedList + "\n" +
                ", current=" + current + "\n" +
                ", start=" + start + "\n" +
                ", target=" + target + "\n" +
                ", nextToPrevMap=" + nextToPrevMap + "\n" +
                '}';
    }

    public String getName() {
        return "Breadth First Search";
    }

    public String getInfo() {
        return getName() + "\n\n" +
                "Total cell count: " + maze.getCells().size() + "\n" +
                "calcNextStep calls: " + calcNextStepCalls + "\n" +
                "Open List Size: " + openList.size() + "\n" +
                "Closed List Size: " + closedList.size() + "\n";
    }
}
