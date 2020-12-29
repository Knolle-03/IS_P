package p2.algorithms;

import p2.Cell;
import p2.Maze;
import processing.core.PApplet;

import java.util.*;

public class AStar implements Algorithm {

    Maze maze;
    PApplet sketch;
    List<Cell> openList = new ArrayList<>();
    List<Cell> closedList = new ArrayList<>();
    Cell current;
    Cell start;
    Cell target;
    Map<Cell, Cell> nextToPrevMap = new HashMap<>();
    Cell minCostCell;
    int pathLength;
    int calcNextStepCalls = 0;

    public AStar(Maze maze) {
        this.maze = maze;
        this.sketch = maze.getSketch();
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
        calcNextStepCalls++;
        minCostCell.setCurrent(false);
        // STEP 1
        openList.sort(Comparator.comparingInt(Cell::getEstimatedTotalCost));
        minCostCell = openList.remove(0);
        minCostCell.setInOpenList(false);
        minCostCell.setInClosedList(true);
        minCostCell.setCurrent(true);
        if (!closedList.contains(minCostCell)) closedList.add(minCostCell);
        // STEP 2
        if (minCostCell == target) {
            maze.setSolved(true);

            System.out.println("MAZE SOLVED!!!");
            Cell curr = target;
            pathLength = 1;
            while (curr != start) {
                System.out.print(curr.get2dIndex() + " <== ");
                pathLength++;
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
        return "A*";
    }

    public int getPathLength() {
        if (maze.isSolved()) {
           return pathLength;
        }
        return -1;
    }

    public String getInfo() {
        return getName() + "\n\n" +
                "Total cell count: " + maze.getCells().size() + "\n" +
                "calcNextStep calls: " + calcNextStepCalls + "\n" +
                "Open List size: " + openList.size() + "\n" +
                "Closed List size: " + closedList.size() + "\n" +
                "Found path length: " + getPathLength() + "\n";
    }
}
