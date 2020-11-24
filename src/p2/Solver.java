package p2;

import java.util.ArrayList;
import java.util.List;

public class Solver {

  private Maze maze;
  private List<Cell> start;
  private Cell target;
  private List<Cell> current;
  Algorithm algorithm;
  List<Cell> closedList = new ArrayList<>();
  List<Cell> openList;
  List<Cell> next;
  boolean solved = false;



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
    openList = new ArrayList<>();
    for (Cell cell : current) {
      openList.addAll(maze.getReachableNeighbours(cell));
    }
    next = algorithm.calcNextStep(openList, current, target);
    current = next;
    openList.removeAll(next);
    closedList.addAll(openList);


    //return null;
  }
}
