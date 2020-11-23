package p2;

import processing.core.PApplet;
import java.util.ArrayList;

public class MySketch extends PApplet {

    int g_size = 10;
    int  background_color = color (80, 80, 220);
    int c_size;
    int gridSize = 20;



    public void settings() {

        size(600, 600);
        c_size = max(sketchWidth()/g_size, sketchHeight()/g_size);

    }

    public void draw() {
        background(background_color);
        float x1 = 0;
        float y1 = 0;
        float x2 = 0;
        float y2 = 0;
        // draw grid
        for (int i = 0; i < width; i +=  width / gridSize) {
            color(2);
            line(0, i, width , i);
            line(i, 0, i, height);
        }
    }

    public static void main(String[] args){
        String[] processingArgs = {"MySketch"};
        MySketch mySketch = new MySketch();
        PApplet.runSketch(processingArgs, mySketch);
    }

}
