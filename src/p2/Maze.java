package p2;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static processing.core.PApplet.floor;

public class Maze {
  PApplet sketch;
  int rows;
  int cols;
  List<Cell> cells = new ArrayList<>();


  public Maze(PApplet sketch, float width, float height, int squareSize) {
    this.sketch = sketch;
    cols = floor(width / squareSize);
    rows = floor(height / squareSize);

    for (int j = 0; j < rows; j++) {
      for (int i = 0; i < cols; i++) {
        cells.add(new Cell(sketch, i, j, squareSize));
      }
    }
  }

  // index = i + j *cols
  public Cell getRandomNeighbour(Cell current) {
    List<Cell> neighbours = new ArrayList<>();
    int topIndex = getIndex(current.row, current.col - 1);
    int rightIndex = getIndex(current.row + 1, current.col);
    int bottomIndex = getIndex(current.row, current.col + 1);
    int leftIndex = getIndex(current.row - 1, current.col);

    if (topIndex != -1 && !cells.get(topIndex).visited) neighbours.add(cells.get(topIndex));
    if (rightIndex != -1 && !cells.get(rightIndex).visited) neighbours.add(cells.get(rightIndex));
    if (bottomIndex != -1 && !cells.get(bottomIndex).visited) neighbours.add(cells.get(bottomIndex));
    if (leftIndex != -1 && !cells.get(leftIndex).visited) neighbours.add(cells.get(leftIndex));

    if (neighbours.size() > 0 ) return neighbours.get(ThreadLocalRandom.current().nextInt(neighbours.size()));
    return null;
  }


  private int getIndex(int i, int j) {
    if (i < 0 || j < 0 || i > cols - 1 || j > rows - 1) return -1;
    return i + j * cols;
  }

  public void removeWall(Cell current, Cell next) {
    int positioning = current.row - next.row;
    if (positioning == -1) {
      current.walls[1] = false;
      next.walls[3] = false;
    } else if (positioning == 1) {
      current.walls[3] = false;
      next.walls[1] = false;
    } else {
      positioning = current.col - next.col;
      if (positioning == -1 ) {
        current.walls[2] = false;
        next.walls[0] = false;
      } else {
        current.walls[0] = false;
        next.walls[2] = false;
      }
    }
  }



}
