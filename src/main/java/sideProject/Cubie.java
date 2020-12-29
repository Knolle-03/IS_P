package sideProject;

import processing.core.PApplet;
import processing.core.PVector;

public class Cubie {

    PApplet sketch;
    PVector pos;
    float length;
    public Cubie(PApplet sketch, float x, float y, float z, float length) {
        pos = new PVector(x, y, z);
        this.length = length;
        this.sketch = sketch;
    }

    void show() {
        sketch.fill(255);
        sketch.stroke(0);
        sketch.strokeWeight(8);
        sketch.pushMatrix();
        sketch.translate(pos.x, pos.y, pos.z);
        sketch.box(length);
        sketch.popMatrix();
    }
}
