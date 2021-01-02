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
    int background_color = color (255);
    int WIDTH = 500;
    int HEIGHT = 500;
    PImage pImage;
    NeuronalNetwork neuronalNetwork;
    int[] percentages;
    PImage img0;
    PImage img1;
    PImage img2;
    PImage img3;
    PImage img4;
    PImage img5;
    PImage img6;
    PImage img7;
    PImage img8;
    PImage img9;

    public static void main(String[] args){
        String[] processingArgs = {"Neuronal Network"};
        Main main = new Main();
        PApplet.runSketch(processingArgs, main);
    }

    @Override
    public void setup() {
        img0 = loadImage("0.jpg");
        img1 = loadImage("1.jpg");
        img2 = loadImage("2.jpg");
        img3 = loadImage("3.jpg");
        img4 = loadImage("4.jpg");
        img5 = loadImage("5.jpg");
        img6 = loadImage("6.jpg");
        img7 = loadImage("7.jpg");
        img8 = loadImage("8.jpg");
        img9 = loadImage("9.jpg");
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
        text("Press t to train", 100, 200);

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
        if (key == 't') {
            System.err.println("Train started");
            train();
            System.err.println("Train finished");
        }
        if (key == 'o') {
            pImage = img0;
            float[] floats = new float[pImage.pixels.length];
            for (int i = 0; i < pImage.pixels.length; i++) {
                floats[i] = (float) pImage.pixels[i];
            }
            float[] retVal = neuronalNetwork.feedForward(floats);
            for (int i = 0; i < retVal.length; i++) {
                percentages[i] = (int) (retVal[i] * 100);
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
    private void train() {
        float[] stateZero = new float[img0.pixels.length];
        for (int i = 0; i < img0.pixels.length; i++) {
            stateZero[i] = (float) img0.pixels[i];
        }
        float[] stateOne = new float[img1.pixels.length];
        for (int i = 0; i < img1.pixels.length; i++) {
            stateOne[i] = (float) img1.pixels[i];
        }
        float[] stateTwo = new float[img2.pixels.length];
        for (int i = 0; i < img2.pixels.length; i++) {
            stateTwo[i] = (float) img2.pixels[i];
        }
        float[] stateThree = new float[img3.pixels.length];
        for (int i = 0; i < img3.pixels.length; i++) {
            stateThree[i] = (float) img3.pixels[i];
        }
        float[] stateFour = new float[img4.pixels.length];
        for (int i = 0; i < img4.pixels.length; i++) {
            stateFour[i] = (float) img4.pixels[i];
        }
        float[] stateFive = new float[img5.pixels.length];
        for (int i = 0; i < img5.pixels.length; i++) {
            stateFive[i] = (float) img5.pixels[i];
        }
        float[] stateSix = new float[img6.pixels.length];
        for (int i = 0; i < img6.pixels.length; i++) {
            stateSix[i] = (float) img6.pixels[i];
        }
        float[] stateSeven = new float[img7.pixels.length];
        for (int i = 0; i < img7.pixels.length; i++) {
            stateSeven[i] = (float) img7.pixels[i];
        }
        float[] stateEight = new float[img8.pixels.length];
        for (int i = 0; i < img8.pixels.length; i++) {
            stateEight[i] = (float) img8.pixels[i];
        }
        float[] stateNine = new float[img9.pixels.length];
        for (int i = 0; i < img9.pixels.length; i++) {
            stateNine[i] = (float) img9.pixels[i];
        }

        float[] zero = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        float[] one = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
        float[] two = {0, 0, 1, 0, 0, 0, 0, 0, 0, 0};
        float[] three = {0, 0, 0, 1, 0, 0, 0, 0, 0, 0};
        float[] four = {0, 0, 0, 0, 1, 0, 0, 0, 0, 0};
        float[] five = {0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
        float[] six = {0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
        float[] seven = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
        float[] eight = {0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
        float[] nine = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1};

        TrainingData dataZero = new TrainingData(stateZero, zero);
        TrainingData dataOne = new TrainingData(stateOne, one);
        TrainingData dataTwo = new TrainingData(stateTwo, two);
        TrainingData dataThree = new TrainingData(stateThree, three);
        TrainingData dataFour = new TrainingData(stateFour, four);
        TrainingData dataFive = new TrainingData(stateFive, five);
        TrainingData dataSix = new TrainingData(stateSix, six);
        TrainingData dataSeven = new TrainingData(stateSeven, seven);
        TrainingData dataEight = new TrainingData(stateEight, eight);
        TrainingData dataNine = new TrainingData(stateNine, nine);

        List<TrainingData> trainingData = new ArrayList<>();
        trainingData.add(dataZero);
        trainingData.add(dataOne);
        trainingData.add(dataTwo);
        trainingData.add(dataThree);
        trainingData.add(dataFour);
        trainingData.add(dataFive);
        trainingData.add(dataSix);
        trainingData.add(dataSeven);
        trainingData.add(dataEight);
        trainingData.add(dataNine);

        for (int i = 0; i < 1000; i++) {
            int rnd = ThreadLocalRandom.current().nextInt(0, trainingData.size());
            neuronalNetwork.train(trainingData.get(rnd).inputs, trainingData.get(rnd).targets);
        }
    }
}
