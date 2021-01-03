package p3.network;

import p3.network.Util.ImgReader;
import p3.network.Util.NeuronalNetwork;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import javax.crypto.spec.PSource;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends PApplet {
    int background_color = color (0);
    int WIDTH = 500;
    int HEIGHT = 500;
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
        neuronalNetwork = new NeuronalNetwork(784, 784, 10);
        frameRate(5);
        background(background_color);
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void draw() {
        for (int i = 0; i < percentages.length; i++) {
            textAlign(LEFT);
            noStroke();
            fill(255);
            textSize(20);
            text(i + ":" + percentages[i], 400, 25 + (i * 25));
        }
        textAlign(LEFT);
        noStroke();
        fill(255);
        textSize(20);
        text("Press f to get results", 300, 300);
        text("Press r to rest img", 300, 350);
        text("Press t to train", 300, 400);

        stroke(255);
        fill(255);
        line(280, 0, 280, 280);
        line(0, 280, 280, 280);
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
        if (mouseX < 280 && mouseY < 280) {
            noStroke();
            ellipse(mouseX, mouseY, 16, 16);
            fill(255);
        }
    }


    @Override
    public void keyPressed() {
        System.err.println(key);
        if (key == 'r') {
            noStroke();
            fill(0);
            rect(400,0,500,250);
            noStroke();
            fill(0);
            rect(0,0,279,279);
            for (int i = 0; i < percentages.length; i++) {
                percentages[i] = 0;
            }
        }
        if (key == 't') {
            System.err.println("Train started");
            train();
            System.err.println("Train finished");
        }
        if (key == 'f') {
            noStroke();
            fill(0);
            rect(400,0,500,250);
            float[] floats280 = new float[280*280];
            for (int x = 0; x < 280; x++) {
                for (int y = 0; y < 280; y++) {
                    Color color = new Color(get(x, y));
                    floats280[x + y] = color.getRed() + color.getGreen() + color.getBlue();
                }
            }
            float[] floats = new float[28*28];
            for (int i = 0; i < floats.length; i++) {
                float val = 0;
                for (int x = 0; x < 10; x ++) {
                    val =+ floats280[x + (10 * i)];
                }
                if (val == 0) {
                    floats[i] = 0;
                } else {
                    floats[i] = (val) / (3 * 255);
                }
            }
            System.out.println(Arrays.toString(floats));
            float[] retVal = neuronalNetwork.feedForward(floats);
            for (int i = 0; i < retVal.length; i++) {
                percentages[i] = (int) (retVal[i] * 100);
            }
            System.out.println(Arrays.toString(retVal));
        }
        redraw();
    }
    private void train() {

    }
}
