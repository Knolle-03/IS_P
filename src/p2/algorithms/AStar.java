package p2.algorithms;

import p2.Cell;
import p2.Maze;

import java.util.*;

public class AStar implements Algorithm {

    Maze maze;
    List<Cell> openList = new ArrayList<>();
    List<Cell> closedList = new ArrayList<>();
    Cell current;
    Cell start;
    Cell target;
    Map<Cell, Cell> nextToPrevMap = new HashMap<>();
    Cell minCostCell;

    public AStar(Maze maze) {
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
        openList.sort(Comparator.comparingInt(Cell::getEstimatedTotalCost));
        minCostCell = openList.remove(0);
        minCostCell.setInOpenList(false);
        minCostCell.setInClosedList(true);
        minCostCell.setCurrent(true);
        closedList.add(minCostCell);
        // STEP 2
        if (minCostCell == target) {
            maze.setSolved(true);

            System.out.println("MAZE SOLVED!!!");
            Cell curr = target;
            while (curr != start) {
                System.out.print(curr.get2dIndex() + " <== ");
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
                if (!openList.contains(neighbour)){
                    openList.add(neighbour);
                    neighbour.setInOpenList(true);
                }
                // add cost of reach
                if (neighbour.getCostOfReach() == -1 || neighbour.getCostOfReach() > minCostCell.getCostOfReach() + 1){
                    neighbour.setCostOfReach(minCostCell.getCostOfReach() + 1 );
                }
                // and keep track of predecessor
                nextToPrevMap.put(neighbour, minCostCell);
            }
            else if(neighbour.getCostOfReach() > minCostCell.getCostOfReach() + 1) {
                nextToPrevMap.put(minCostCell, neighbour);
                //neighbour.setCostOfReach(minCostCell.getCostOfReach() + 1);
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
