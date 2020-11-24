package p2;

import processing.core.*;

public class Cell {

  PApplet sketch;
  boolean[] walls = {true, true, true, true};
  boolean visited = false;
  boolean isExplored = false;


  int manhattanDistance = -1;


  int row;
  int col;
  int size;
  public Cell(PApplet sketch, int row, int col, int size) {
    this.sketch = sketch;
    this.row = row;
    this.col = col;
    this.size = size;
  }

  public void show() {
    int x = row * size;
    int y = col * size;
    //System.out.println("Cell's x: " + x + "    Cells y: " + y);
    sketch.stroke(255);

    // top
    if (walls[0]) sketch.line(x, y, x + size, y);
    //right
    if (walls[1]) sketch.line(x + size, y, x + size, y + size);
    //bottom
    if (walls[2]) sketch.line(x, y + size, x + size, y + size);
    // left
    if (walls[3]) sketch.line(x, y, x, y + size);



    if (visited) {
      sketch.noStroke();
      sketch.fill(255, 100);
      sketch.rect(x, y, size, size);
    }

    if (manhattanDistance != -1) {
      sketch.stroke(255, 0, 0, 255);
      sketch.textAlign(PConstants.CENTER, PConstants.CENTER);
      sketch.text(manhattanDistance, x + size / 2f, y + size / 2f);
    }

    if (isExplored) {
      sketch.noStroke();
      sketch.fill(0, 100, 0, 100);
      sketch.rect(x, y, size, size);
    }



  }

  public void highlightTarget() {
    int x = row * size;
    int y = col * size;
    sketch.fill(0,0,0);
    sketch.rect(x, y, size, size);
  }


  public void highlight() {
    int x = row * size;
    int y = col * size;
    sketch.noStroke();
    sketch.fill(255,0,0, 100);
    sketch.rect(x, y, size, size);

  }

  public void calcManhattanDistance(Cell target) {
    int vertical = Math.abs(this.row - target.row);
    int horizontal = Math.abs(this.col - target.col);
    manhattanDistance = vertical + horizontal;
  }
}
