package sideProject;

import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Cube {
    PApplet sketch;
    int dim = 3;

    // colors : UP, DOWN, RIGHT, LEFT, FRONT, BACK
    List<Color> colorList = new ArrayList<>();

    Cubie[][][] cube = new Cubie[dim][dim][dim];


    public Cube(PApplet sketch, int dim) {
        this.sketch = sketch;
        //colorList.add();
        this.dim = dim;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                for (int k = 0; k < dim; k++) {
                    float length = 50;
                    float offSet = (dim - 1 ) * length * 0.5f;
                    float x = length * i- offSet;
                    float y = length * j- offSet;
                    float z = length * k- offSet;
                    cube[i][j][k] = new Cubie(sketch, x, y, z, length);
                }
            }
        }
    }


    public void show() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                for (int k = 0; k < dim; k++) {
                    cube[i][j][k].show();
                }
            }
        }
    }


}
