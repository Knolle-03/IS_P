package p3.network;

import p3.network.Util.ImgReader;
import p3.network.Util.NeuronalNetwork;
import p3.network.mnist.util.CsvFileReader;
import p3.network.mnist.util.TestData;
import p3.network.mnist.util.TrainingData;
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

    CsvFileReader trainingSet = new CsvFileReader("src/main/java/p3/network/mnist/data/mnist_train.csv", false);
    CsvFileReader testSet = new CsvFileReader("src/main/java/p3/network/mnist/data/mnist_test.csv", true);

    List<TrainingData> trainingData;
    List<TestData> testData;

    List<Float[][]> testNumbers;


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
        Arrays.fill(percentages, 0);

        trainingSet.readData();
        testSet.readData();
        trainingData = trainingSet.getTrainingData();
        testData = testSet.getTestData();
        testNumbers = testSet.getNumbers();
        neuronalNetwork = new NeuronalNetwork(784, 256, 10);




        frameRate(50);
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
        if (mouseX < 272 && mouseY < 272) {
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
            Arrays.fill(percentages, 0);
        }
        if (key == 't') {
            System.err.println("Train started");
            train();
            System.err.println("Train finished");

        }
        if (key == 'f') {

            noStroke();
            fill(0);
            rect(400,0,520,250);


            PImage image = get(0,0,280,280);
            image.resize(28,28);
            image.save("src/main/resources/drawnNumber.jpg");
            image.loadPixels();
            int[] pixelArr = image.pixels;
            System.out.println(Arrays.toString(pixelArr));
            float[] pixelVals = new float[784];
            int counter = 0;
            System.out.println(pixelArr.length);
            for (int y = 0; y < 28; y++) {
                for (int x = 0; x < 28; x++) {
                    int loc = x + y * 28;

                    // The functions red(), green(), and blue() pull out the 3 color components from a pixel.
                    float r = red(image.pixels[loc]) * 0.3f;
                    float g = green(image.pixels[loc]) * 0.59f;
                    float b = blue(image.pixels[loc]) * 0.11f;
                    float gray = r + g + b;

                    pixelVals[counter] = gray / 255;
                    counter++;
                }
            }

            PImage resized = loadImage("src/main/resources/drawnNumber.jpg");
            image(resized, 0,300);

            System.out.println(Arrays.toString(pixelVals));
            float[] retVal = neuronalNetwork.feedForward(pixelVals);
            for (int i = 0; i < retVal.length; i++) {
                percentages[i] = (int) (retVal[i] * 100);
            }
            System.out.println(Arrays.toString(retVal));
        }

        redraw();
    }
    private void train() {
        for (int i = 0; i < 50_000 ; i++) {
            if (i % 1000 == 0) System.out.println(i);
            int pick = ThreadLocalRandom.current().nextInt(0, trainingData.size());
            neuronalNetwork.backPropagation(trainingData.get(pick).getInputs(), trainingData.get(pick).getTargets());

        }
    }
}
