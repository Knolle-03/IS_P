package p3.perceptron;

import p3.perceptron.training.Point;
import processing.core.PApplet;
import static p3.perceptron.training.Util.*;

public class PerceptronSketch extends PApplet {

    Perceptron perceptron;
    Point[] points = new Point[1000];
    Point linePoint1;
    Point linePoint2;
    Point guessedLinePoint1;
    Point guessedLinePoint2;
    int trainingIndex = 0;

    public void settings() {
        size(1000, 1000/*, P3D*/);
        linePoint1 = new Point(-1, f(-1), this);
        linePoint2 = new Point(1, f(1), this);

    }


    public void setup() {
        perceptron = new Perceptron(this, 3, 0.005f);

        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(this);
        }
    }

    public void draw() {
        background(42);
        stroke(0);
        line(linePoint1.mapX(), linePoint1.mapY(),linePoint2.mapX(), linePoint2.mapY());

        guessedLinePoint1 = new Point(-1, perceptron.guessY(-1), this);
        guessedLinePoint2 = new Point(1, perceptron.guessY(1), this);
        line(guessedLinePoint1.mapX(), guessedLinePoint1.mapY(), guessedLinePoint2.mapX(), guessedLinePoint2.mapY());
        for (Point p : points) {
            p.show();
        }

        for (Point point : points) {
            float[] inputs = {point.getX(), point.getY(), point.getBias()};
            int target = point.getLabel();
            //perceptron.train(inputs, target);
            int guess = perceptron.guess(inputs);
            if (guess == target) fill(0,255,0);
            else fill(255,0,0);
            noStroke();
            ellipse(point.mapX(), point.mapY(), 10,10);
        }


        Point training = points[trainingIndex];
        trainingIndex++;
        if (trainingIndex == points.length) trainingIndex = 0;
        float[] inputs = {training.getX(), training.getY(), training.getBias()};
        perceptron.train(inputs, training.getLabel());
    }


    public static void main(String[] args){
        String[] processingArgs = {"lol"};
        PerceptronSketch perceptronSketch = new PerceptronSketch();
        PApplet.runSketch(processingArgs, perceptronSketch);
    }


    @Override
    public void mousePressed() {
        for (Point point : points) {

        }
    }
}
