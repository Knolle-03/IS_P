package p2;

import java.util.ArrayList;
import java.util.List;

public class Solver {

  Maze maze;
  List<Cell> start;
  Cell target;
  List<Cell> current;
  Algorithm algorithm;
  List<Cell> closedList = new ArrayList<>();
  List<Cell> openList = new ArrayList<>();
  List<Cell> next;
  boolean solved = false;
  int step = 0;



  public Solver(Maze maze, Algorithm algorithm, List<Cell> start, Cell target) {
    this.maze = maze;
    this.algorithm = algorithm;
    this.start = start;
    this.current = start;
    this.target = target;
    for (Cell cell : current) {
      cell.isExplored = true;
    }
  }

  public void calcNextStep() {
    step++;
    System.out.println("-----------" + step + "----------");
    System.out.println("Current: " + current.get(0).get2dIndex());
    for (Cell cell : current) {
      openList.addAll(maze.getUnexploredReachableNeighbours(cell));
    }
    for (Cell cell : openList) {
      System.out.println("OpenList: " + cell.get2dIndex());
    }
    for (Cell cell : closedList) {
      System.out.println("OpenList: " + cell.get2dIndex());
    }
    System.out.println("----------------------");
    next = algorithm.calcNextStep(openList, current, target);
    current = next;
    current.forEach(cell -> cell.isExplored = true);

    openList.removeAll(next);
    closedList.addAll(openList);
    if (current.contains(target)) {
      System.out.println("Maze solved!");
      maze.solved = true;
    }
  }
}
