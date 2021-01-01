package p3.network;

import p3.network.Util.ImgReader;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.ArrayList;

public class Main extends PApplet {
    int background_color = color (255);
    int WIDTH = 1000;
    int HEIGHT = 1000;



    public static void main(String[] args){
        String[] processingArgs = {"Neuronal Network"};
        Main main = new Main();
        PApplet.runSketch(processingArgs, main);
    }

    @Override
    public void setup() {
        frameRate(5);
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void draw() {
        background(background_color);
        PImage pImage = loadImage("Input.jpg");
        image(pImage,0,0);
    }

    @Override
    public void keyPressed() {
        System.out.println(key);
        if (key == 't') {
            //train();
            ArrayList<Color> colors = ImgReader.getColors();
            for (Color color : colors) {
                System.out.println(color.toString());
            }
        }
        redraw();
    }

}
