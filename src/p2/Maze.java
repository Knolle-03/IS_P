package p2;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

import static processing.core.PApplet.floor;

public class Maze {

  //generation fields
  Stack<Cell> visitedCells = new Stack<>();
  int additionalWallRemoves;
  boolean generationCompleted = false;


  PApplet sketch;
  int rows;
  int cols;
  boolean solved = false;
  List<Cell> cells = new ArrayList<>();
  Cell current;
  Cell start;
  Cell target;


  public Maze(PApplet sketch, float width, float height, int squareSize, int additionalWallRemoves) {
    this.sketch = sketch;
    cols = floor(width / squareSize);
    rows = floor(height / squareSize);
    this.additionalWallRemoves = additionalWallRemoves;

    for (int j = 0; j < rows; j++) {
      for (int i = 0; i < cols; i++) {
        cells.add(new Cell(sketch, i, j, squareSize));
      }
    }
    //STEP 1
    current = cells.get(0);
    current.visited = true;
    start = cells.get(0);
    target = cells.get(cells.size() - 1);
  }

  public void generate(){
    while (!generationCompleted) {
      nextStep();
    }
  }



  public void nextStep() {
    if (!generationCompleted) {
      current.isCurrent = false;


      List<Cell> neighbours = getNeighbours(current);
      Cell next = null;
      if (neighbours.size() > 0) next = neighbours.get(ThreadLocalRandom.current().nextInt(neighbours.size()));

      //STEP 2.1.1
      if (next != null) {
        // STEP 2.1.2
        visitedCells.push(current);
        // STEP 2.1.3
        removeWall(current, next);
        // STEP 2.1.4
        current = next;
        current.visited = true;
        // STEP 2.2.0
      } else if (!visitedCells.empty()) {
        // 2.2.1 + 2.2.2
        current = visitedCells.pop();

      } else {

        for (int i = 0; i < additionalWallRemoves; i++) {
          int cellIndex = ThreadLocalRandom.current().nextInt(cells.size());
          removeRandomWall(getCells().get(cellIndex));
        }
        generationCompleted = true;

      }
      current.isCurrent = true;
    }
  }

  // index = i + j *cols
  public List<Cell> getNeighbours(Cell current) {
    List<Cell> neighbours = new ArrayList<>();
    int topIndex = getIndex(current.col, current.row - 1);
    int rightIndex = getIndex(current.col + 1, current.row);
    int bottomIndex = getIndex(current.col, current.row + 1);
    int leftIndex = getIndex(current.col - 1, current.row);

    if (topIndex != -1 && !cells.get(topIndex).visited) neighbours.add(cells.get(topIndex));
    if (rightIndex != -1 && !cells.get(rightIndex).visited) neighbours.add(cells.get(rightIndex));
    if (bottomIndex != -1 && !cells.get(bottomIndex).visited) neighbours.add(cells.get(bottomIndex));
    if (leftIndex != -1 && !cells.get(leftIndex).visited) neighbours.add(cells.get(leftIndex));

    return neighbours;
  }

  public List<Cell> getReachableNeighbours(Cell current) {
    List<Cell> reachable = new ArrayList<>();
    //System.out.println("current col + row: " + current.row + " " + current.col + Arrays.toString(current.walls));
      if (!current.walls[0]) reachable.add(cells.get(getIndex(current.col, current.row - 1)));
      if (!current.walls[1]) reachable.add(cells.get(getIndex(current.col + 1, current.row)));
      if (!current.walls[2]) reachable.add(cells.get(getIndex(current.col, current.row + 1)));
      if (!current.walls[3]) reachable.add(cells.get(getIndex(current.col - 1, current.row)));

    return reachable;
  }


  private int getIndex(int i, int j) {
    if (i < 0 || j < 0 || i > cols - 1 || j > rows - 1) return -1;
    return i + j * cols;
  }

  public void removeRandomWall(Cell cell) {
    int neighbourIndex;
    for (int i = 0; i < cell.walls.length; i++) {
      if (cell.walls[i]) {
        if (i == 0) {
          neighbourIndex = getIndex(cell.col, cell.row - 1);
          // has valid top neighbour
          if (neighbourIndex != -1) {
            cell.walls[0] = false;
            cells.get(neighbourIndex).walls[2] = false;
            //System.out.println("Removed wall between: " + cell.get2dIndex() + " and " + cells.get(neighbourIndex).get2dIndex());
            return;
          }
        } else if (i == 1) {
          neighbourIndex = getIndex(cell.col + 1, cell.row);
          // has valid right neighbour
          if (neighbourIndex != -1) {
            cell.walls[1] = false;
            cells.get(neighbourIndex).walls[3] = false;
            //System.out.println("Removed wall between: " + cell.get2dIndex() + " and " + cells.get(neighbourIndex).get2dIndex());
            return;
          }
        }else if (i == 2) {
          neighbourIndex = getIndex(cell.col, cell.row + 1);
          // has valid bottom neighbour
          if (neighbourIndex != -1) {
            cell.walls[2] = false;
            cells.get(neighbourIndex).walls[0] = false;
            //System.out.println("Removed wall between: " + cell.get2dIndex() + " and " + cells.get(neighbourIndex).get2dIndex());
            return;
          }
        }else if (i == 3) {
          neighbourIndex = getIndex(cell.col - 1, cell.row);
          // has valid left neighbour
          if (neighbourIndex != -1) {
            cell.walls[3] = false;
            cells.get(neighbourIndex).walls[1] = false;
            //System.out.println("Removed wall between: " + cell.get2dIndex() + " and " + cells.get(neighbourIndex).get2dIndex());
            return;
          }
        }
      }
    }
  }

  public void removeWall(Cell current, Cell next) {
    int positioning = current.col - next.col;
    if (positioning == -1) {
      current.walls[1] = false;
      next.walls[3] = false;
    } else if (positioning == 1) {
      current.walls[3] = false;
      next.walls[1] = false;
    } else {
      positioning = current.row - next.row;
      if (positioning == -1 ) {
        current.walls[2] = false;
        next.walls[0] = false;
      } else {
        current.walls[0] = false;
        next.walls[2] = false;
      }
    }
  }


  public PApplet getSketch() {
    return sketch;
  }

  public void setSketch(PApplet sketch) {
    this.sketch = sketch;
  }

  public boolean isSolved() {
    return solved;
  }

  public void setSolved(boolean solved) {
    this.solved = solved;
  }

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public int getCols() {
    return cols;
  }

  public void setCols(int cols) {
    this.cols = cols;
  }

  public List<Cell> getCells() {
    return cells;
  }

  public void setCells(List<Cell> cells) {
    this.cells = cells;
  }
}
