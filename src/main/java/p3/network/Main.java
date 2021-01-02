package p3.network;

import p3.network.Util.ImgReader;
import p3.network.Util.NeuronalNetwork;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends PApplet {
    int background_color = color (255);
    int WIDTH = 500;
    int HEIGHT = 500;
    PImage pImage;
    NeuronalNetwork neuronalNetwork;
    int[] percentages;

    public static void main(String[] args){
        String[] processingArgs = {"Neuronal Network"};
        Main main = new Main();
        PApplet.runSketch(processingArgs, main);
    }

    @Override
    public void setup() {
        percentages = new int[10];
        for (int i = 0; i < percentages.length; i++) {
            percentages[i] = 0;
        }
        neuronalNetwork = new NeuronalNetwork(1024, 1024, 10);
        frameRate(5);
        background(background_color);
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void draw() {
        noStroke();
        fill(255);
        rect(400, 0, 500, 500);
        for (int i = 0; i < percentages.length; i++) {
            textAlign(LEFT);
            noStroke();
            fill(0);
            textSize(20);
            text(i + ":" + percentages[i], 400, 25 + (i * 25));
        }
        textAlign(LEFT);
        noStroke();
        fill(0);
        textSize(20);
        text("Press f to get results", 100, 100);
        text("Press r to rest img", 100, 150);

        fill(0);
        stroke(0);
        line(33, 0, 33, 33);
        line(0, 33, 33, 33);
        line(34, 0, 34, 34);
        line(0, 34, 34, 34);
        line(35, 0, 35, 35);
        line(0, 35, 35, 35);
    }

    @Override
    public void mouseDragged() {
        /*PImage img = createImage(32, 32, RGB);
        img.loadPixels();
        for (int i = 0; i<img.pixels.length; i++) {
            System.out.println(img.pixels[i]);
        }

        fill(0);
         */
        if (mouseX < 33 && mouseY < 33) {
            noStroke();
            ellipse(mouseX, mouseY, 4, 4);
            fill(0);
        }
    }


    @Override
    public void keyPressed() {
        if (key == 'r') {
            noStroke();
            fill(255);
            rect(0,0,32,32);
            for (int i = 0; i < percentages.length; i++) {
                percentages[i] = 0;
            }
        }
        if (key == 'f') {
            pImage = get(0,0,32,32);
            float[] floats = new float[pImage.pixels.length];
            for (int i = 0; i < pImage.pixels.length; i++) {
                floats[i] = (float) pImage.pixels[i];
            }
            float[] retVal = neuronalNetwork.feedForward(floats);
            for (int i = 0; i < retVal.length; i++) {
                percentages[i] = (int) (retVal[i] * 100);
            }
        }
        redraw();
    }

}
