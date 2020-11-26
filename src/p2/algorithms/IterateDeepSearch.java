package p2.algorithms;

import p2.Cell;
import p2.Maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IterateDeepSearch implements Algorithm  {

    Maze maze;
    List<Cell> openList = new ArrayList<>();
    List<Cell> closedList = new ArrayList<>();
    Cell current;
    Cell start;
    Cell target;
    Map<Cell, Cell> nextToPrevMap = new HashMap<>();
    int round;
    boolean clear;

    public IterateDeepSearch(Maze maze) {
        this.maze = maze;
        current = maze.getCells().get(0);
        target = maze.getCells().get(maze.getCells().size() - 1);
        start = current;
        start.setCostOfReach(0);
        start.setInOpenList(true);
        start.setCurrent(true);
        round = 1;
        clear = false;
        openList.add(start);
    }

    @Override
    public void calcNextStep() {
                for (Cell cell : openList) {
                    System.out.println("open list: " + cell.get2dIndex());
                }
                for (Cell cell : closedList) {
                    System.out.println("closed list: " + cell.get2dIndex());
                }
                List<Cell> newOpenList = new ArrayList<>();
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
                        }
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
        for (Cell cellOfOpenList : openList) {
            cellOfOpenList.setExplored(false);
            cellOfOpenList.setInClosedList(false);
            cellOfOpenList.setInOpenList(false);
        }
        for (Cell cellOfClosedList : closedList) {
            cellOfClosedList.setExplored(false);
            cellOfClosedList.setInClosedList(false);
            cellOfClosedList.setInOpenList(false);
        }
        closedList = new ArrayList<>();
        openList = new ArrayList<>();
        openList.add(start);
        return null;
    }
}
