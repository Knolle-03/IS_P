package p2.algorithms;

import p2.Cell;
import p2.Maze;

import java.util.*;

public class IterativeDeepeningDepthFirstSearch implements Algorithm {


    Maze maze;
    List<Cell> openList = new ArrayList<>();
    List<Cell> closedList = new ArrayList<>();
    Cell current;
    Cell start;
    Cell target;
    Map<Cell, Cell> nextToPrevMap = new HashMap<>();
    Stack<Cell> visitedCells = new Stack<>();
    Cell minCostCell;
    int pathLength;
    int depthBound = 1;
    int calcNextStepCalls = 0;

    public IterativeDeepeningDepthFirstSearch(Maze maze) {
        this.maze = maze;
        this.current = maze.getCells().get(0);
        this.start = current;
        this.target = maze.getCells().get(maze.getCells().size() - 1);
        this.nextToPrevMap = new HashMap<>();
        visitedCells.push(start);
    }



//    public void nextStep() {
//        if (!generationCompleted) {
//            current.isCurrent = false;
//
//
//            List<Cell> neighbours = getNeighbours(current);
//            Cell next = null;
//            if (neighbours.size() > 0) next = neighbours.get(ThreadLocalRandom.current().nextInt(neighbours.size()));
//
//            //STEP 2.1.1
//            if (next != null) {
//                // STEP 2.1.2
//                visitedCells.push(current);
//                // STEP 2.1.3
//                removeWall(current, next);
//                // STEP 2.1.4
//                current = next;
//                current.visited = true;
//                // STEP 2.2.0
//            } else if (!visitedCells.empty()) {
//                // 2.2.1 + 2.2.2
//                current = visitedCells.pop();
//
//            } else {
//
//                for (int i = 0; i < additionalWallRemoves; i++) {
//                    int cellIndex = ThreadLocalRandom.current().nextInt(cells.size());
//                    removeRandomWall(getCells().get(cellIndex));
//                }
//                generationCompleted = true;
//
//            }
//            current.isCurrent = true;
//        }
//    }


    @Override
    public void calcNextStep() {
        visitedCells = new Stack<>();
        current = start;
        List<Cell> neighbours = maze.getReachableNeighbours(start);
        neighbours.forEach(cell ->  {
            if (visitedCells.contains(cell) || cell.getCostOfReach() > depthBound){
                System.out.println("removing: " + cell.get2dIndex());
                neighbours.remove(cell);
            }
        });
        Cell next = null;
        if (neighbours.size() > 0) next = neighbours.get(0);

        if (next != null) {
            visitedCells.push(current);

            current = next;
            current.setExplored(true);
        } else if (!visitedCells.isEmpty()) {
            current = visitedCells.pop();
        } else {
            System.out.println("Target not found for depth bound: " + depthBound);
        }
        depthBound++;
    }



    @Override
    public List<Cell> getCurrentCells() {
        return null;
    }

    @Override
    public void solve() {

    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public String getName() {
        return "Iterative Deepening Depth Fist Search";
    }

    @Override
    public String getInfo() {
        return getName() + "\n\n" +
                "Total cell count: " + maze.getCells().size() + "\n" +
                "calcNextStep calls: " + calcNextStepCalls + "\n" +
                "Open List Size: " + openList.size() + "\n" +
                "Closed List Size: " + closedList.size() + "\n";
    }
}
