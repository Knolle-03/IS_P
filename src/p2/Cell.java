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

  public void show() {
    int x = col * size;
    int y = row * size;
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
      //sketch.textSize();
      sketch.text(manhattanDistance, x + size / 2f, y + size / 2f);
    }

    if (costOfReach != -1) {
      sketch.stroke(0,0 , 255, 255);
      sketch.textAlign(PConstants.CENTER, PConstants.CENTER);
      //sketch.textSize();
      sketch.text(costOfReach, x + size / 1.3f, y + size / 1.3f);
    }

    if (isCurrent) {
      sketch.stroke(0);
      sketch.fill(0);
      sketch.circle(x + size / 2f,y + size / 2f,size / 2f);
      System.out.println("Size in isCurrent draw(): " + size + "\nCol: " + x + " | Row: " + y);
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
      sketch.fill(0, 0, 255, 50);
      sketch.rect(x, y, size, size);
    }

    if (isPartOfPath) {
      sketch.noStroke();
      sketch.fill(0, 255, 0, 255);
      sketch.rect(x + size / 10f, y + size / 10f, size - 2 * (size / 10f), size - 2 * (size / 10f));
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
            ", manhattanDistance=" + manhattanDistance +
            ", col=" + col +
            ", row=" + row +
            '}';
  }

  //############# Getters and Setters ###################


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
