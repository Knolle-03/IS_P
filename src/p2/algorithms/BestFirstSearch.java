package p2.algorithms;


import p2.Cell;
import p2.Maze;

import java.util.*;

public class BestFirstSearch implements Algorithm {

    Maze maze;
    List<Cell> openList = new ArrayList<>();
    List<Cell> closedList = new ArrayList<>();
    Cell current;
    Cell start;
    Cell target;
    Map<Cell, Cell> nextToPrevMap = new HashMap<>();
    Cell minCostCell;

    public BestFirstSearch(Maze maze) {
        this.maze = maze;
        current = maze.getCells().get(0);
        target = maze.getCells().get(maze.getCells().size() - 1);
        start = current;
        start.setCostOfReach(0);
        start.setInOpenList(true);
        start.setCurrent(true);
        openList.add(start);
        minCostCell = start;
    }


    @Override
    public void calcNextStep() {
        minCostCell.setCurrent(false);
        // STEP 1
        openList.sort(Comparator.comparingInt(Cell::getManhattanDistance));
        minCostCell = openList.remove(0);
        minCostCell.setInOpenList(false);
        minCostCell.setInClosedList(true);
        minCostCell.setCurrent(true);
        closedList.add(minCostCell);
        // STEP 2
        if (minCostCell == target) {
            maze.setSolved(true);
            for (Cell cell : openList) {
                System.out.println("open list: " + cell.get2dIndex());
            }
            for (Cell cell : closedList) {
                System.out.println("closed list: " + cell.get2dIndex());
            }
            for (Map.Entry<Cell, Cell> entry : nextToPrevMap.entrySet()) {
                System.out.println("prevToNextMap: " + entry.getKey().get2dIndex() + " <==  " + entry.getValue().get2dIndex());
            }
            System.out.println("Current: " + current.get2dIndex());
            System.out.println("MAP: " + nextToPrevMap.toString());
            System.out.println("MAZE SOLVED!!!");
            Cell curr = target;
            while (curr != start) {
                System.out.println(curr.get2dIndex());
                curr.setPartOfPath(true);
                curr = nextToPrevMap.get(curr);
            }
            curr.setPartOfPath(true);
            return;
        }
        // STEP 3
        List<Cell> neighbours = maze.getReachableNeighbours(minCostCell);

        // STEP 4
        //for every neighbour
        for (Cell neighbour : neighbours) {
            // if not already in closed list
            if (!closedList.contains(neighbour)) {
                // add to open list
                openList.add(neighbour);
                neighbour.setInOpenList(true);
                // add cost of reach
                neighbour.setCostOfReach(minCostCell.getCostOfReach() + 1 );
                // and keep track of predecessor
                nextToPrevMap.put(neighbour, minCostCell);
            }
            else if(neighbour.getCostOfReach() > minCostCell.getCostOfReach() + 1) {
                nextToPrevMap.put(minCostCell, neighbour);
            }
        }
    }

    @Override
    public List<Cell> getCurrentCells() {
        List<Cell> current = new ArrayList<>();
        current.add(this.current);
        return current;
    }




}
