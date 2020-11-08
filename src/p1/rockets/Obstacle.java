package p1.rockets;

import processing.core.PApplet;

public class Obstacle {

  PApplet sketch;
  float x;
  float y;
  float width;
  float height;

  public Obstacle(PApplet sketch, float x, float y, float width, float height) {
    this.sketch = sketch;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public void show() {
    sketch.rect(x, y, width, height);
  }
}
