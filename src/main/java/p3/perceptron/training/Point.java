package p3.perceptron.training;

import lombok.Data;
import p3.perceptron.PerceptronSketch;
import processing.core.PApplet;
import static processing.core.PApplet.*;
import static p3.perceptron.training.Util.*;

@Data
public class Point {
    private final float x;
    private final float y;
    private final float bias = 1;
    private final int label;
    private final PerceptronSketch sketch;

    public Point(PerceptronSketch sketch) {
        this.sketch = sketch;
        x = sketch.random(-1 , 1);
        y = sketch.random(-1 , 1);

        label = y > f(x) ? 1 : -1;
    }

    public Point(float x, float y, PerceptronSketch sketch) {
        this.x = x;
        this.y = y;
        label = y > f(x) ? 1 : -1;
        this.sketch = sketch;
    }

    public void show() {
        sketch.stroke(0);

        if (label == 1) sketch.fill(255);
        else sketch.fill(0);

        sketch.ellipse(mapX(), mapY(), 20, 20);
    }

    public float mapX () {
        return map(x, -1, 1, 0, sketch.width);
    }

    public float mapY() {
        return map(y, -1, 1, sketch.height, 0);
    }
}
