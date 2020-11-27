package sideProject;


import peasy.PeasyCam;
import processing.core.PApplet;


public class RubiksCubeSketch extends PApplet {
    int dim = 3;
    RubiksCube cube;
    PeasyCam cam;

    public void settings() {
        size(500, 500, P3D);
    }

    public void setup() {
        cube = new RubiksCube(this, dim);
        cam = new PeasyCam(this, 500);

    }

    public void draw() {
        background(42);
        cube.show();
    }


    public static void main(String[] args){
        String[] processingArgs = {"MySketch"};
        RubiksCubeSketch rubiksCubeSketch = new RubiksCubeSketch();
        PApplet.runSketch(processingArgs, rubiksCubeSketch);
    }

}
