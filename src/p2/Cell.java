package p2;

import processing.core.*;

import java.util.Arrays;

public class Cell {

  PApplet sketch;
  boolean[] walls = {true, true, true, true};
  //for generation
  boolean visited = false;
  // for drawing purposes
  boolean isExplored = false;
  boolean inClosedList = false;
  boolean inOpenList = false;
  boolean isCurrent = false;
  boolean isPartOfPath = false;


  int manhattanDistance = -1;
  int costOfReach = -1;


  int col;
  int row;
  int size;


  public Cell(PApplet sketch, int col, int row, int size) {
    this.sketch = sketch;
    this.col = col;
    this.row = row;
    this.size = size;
  }

  // copy constructor
  public Cell(Cell cell) {
    this.sketch = cell.sketch;
    this.walls = Arrays.copyOf(cell.walls, cell.walls.length);
    this.visited = cell.visited;
    this.isExplored = cell.isExplored;
    this.inClosedList = cell.inClosedList;
    this.inOpenList = cell.inOpenList;
    this.isCurrent = cell.isCurrent;
    this.isPartOfPath = cell.isPartOfPath;
    this.manhattanDistance = cell.manhattanDistance;
    this.costOfReach = cell.costOfReach;
    this.col = cell.col;
    this.row = cell.row;
    this.size = cell.size;
  }

  public void show() {
    int x = col * size;
    int y = row * size;
    //System.out.println("Cell's x: " + x + "    Cells y: " + y);
    sketch.stroke(0);
    sketch.strokeWeight(5);

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
      sketch.rectMode(PConstants.CORNER);
      sketch.rect(x, y, size, size);
    }

    if (manhattanDistance != -1) {
      sketch.fill(255);
      sketch.stroke(0, 255);
      sketch.textAlign(PConstants.CENTER, PConstants.CENTER);
      //sketch.textSize();
      sketch.text(manhattanDistance, x + 3 * (size / 4f), y + size / 4f);
    }

    if (costOfReach != -1) {
      sketch.stroke(0, 255);
      sketch.textAlign(PConstants.CENTER, PConstants.CENTER);
      //sketch.textSize();
      sketch.text(costOfReach, x + size / 4f, y + size / 4f);
    }

    if (costOfReach != -1) {
      sketch.stroke(0, 255);
      sketch.textAlign(PConstants.CENTER, PConstants.CENTER);
      //sketch.textSize();
      sketch.text(getEstimatedTotalCost(), x + size / 2f, y + size / 2f);
    }

    if (isCurrent) {
      sketch.stroke(0);
      sketch.fill(0);
      sketch.circle(x + size / 2f,y + size / 2f,size / 2f);
      sketch.noFill();
    }

    if (inClosedList) {
      sketch.stroke(0,0,0,100);
      sketch.line(x, y, x + size, y + size);
      sketch.line(x + size, y, x, y + size);

    }
//
    if (inOpenList) {
      sketch.noStroke();
      sketch.fill(0, 0, 255, 100);
      sketch.rectMode(PConstants.CORNER);
      sketch.rect(x, y, size, size);
    }

    if (isPartOfPath) {
      sketch.noStroke();
      sketch.fill(0, 255, 0, 255);
      sketch.rectMode(PConstants.CENTER);
      sketch.rect(x + size / 2f, y + 3* (size / 4f), size - 4 * (size / 5f), size - 4 * (size / 5f));
    }

  }






  public void highlight() {
    int x = col * size;
    int y = row * size;
    sketch.noStroke();
    sketch.fill(255,0,0, 100);
    sketch.rect(x, y, size, size);

  }

  public void calcManhattanDistance(Cell target) {
    int vertical = Math.abs(this.col - target.col);
    int horizontal = Math.abs(this.row - target.row);
    manhattanDistance = vertical + horizontal;
  }

  @Override
  public String toString() {
    return "Cell{" +
            "walls=" + Arrays.toString(walls) +
            ", visited=" + visited +
            ", isExplored=" + isExplored +
            ", inClosedList=" + inClosedList +
            ", inOpenList=" + inOpenList +
            ", manhattanDistance=" + manhattanDistance +
            ", costOfReach=" + costOfReach +
            ", col=" + col +
            ", row=" + row +
            '}';
  }

  //############# Getters and Setters ###################


  public PApplet getSketch() {
    return sketch;
  }

  public void setSketch(PApplet sketch) {
    this.sketch = sketch;
  }

  public int getEstimatedTotalCost() { return getCostOfReach() + getManhattanDistance(); }

  public boolean isPartOfPath() {
    return isPartOfPath;
  }

  public void setPartOfPath(boolean partOfPath) {
    isPartOfPath = partOfPath;
  }

  public boolean isExplored() {
    return isExplored;
  }

  public void setExplored(boolean explored) {
    isExplored = explored;
  }

  public boolean isInClosedList() {
    return inClosedList;
  }

  public void setInClosedList(boolean inClosedList) {
    this.inClosedList = inClosedList;
  }

  public boolean isInOpenList() {
    return inOpenList;
  }

  public void setInOpenList(boolean inOpenList) {
    this.inOpenList = inOpenList;
  }

  public boolean isCurrent() {
    return isCurrent;
  }

  public void setCurrent(boolean current) {
    isCurrent = current;
  }

  public int getCostOfReach() {
    return costOfReach;
  }

  public void setCostOfReach(int costOfReach) {
    this.costOfReach = costOfReach;
  }

  public String get2dIndex() {
    return String.format("col: %s row: %s", col, row);
  }

  public int getManhattanDistance() {
    return manhattanDistance;
  }
}
