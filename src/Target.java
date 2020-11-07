import processing.core.PApplet;
import processing.core.PVector;

public class Target {

  private final PApplet sketch;
  private final PVector targetPos;


  public Target (PApplet sketch, PVector targetPos) {
    this.targetPos = targetPos;
    this.sketch = sketch;
  }


  public void show() {
    sketch.ellipse(sketch.width / 2f, 50, 50, 50);
  }

  public PVector getPos() {
    return targetPos;
  }
}
