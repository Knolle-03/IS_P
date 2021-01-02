package p3.network.Util;

import lombok.var;
import p2.MazeSolverSketch;
import p3.network.TrainingData;
import processing.core.PApplet;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class XOR extends PApplet {

    NeuronalNetwork neuronalNetwork;
    List<TrainingData> trainingData = XOR_TrainingData.getTrainingData();


    @Override
    public void settings() {
        size(400,400);

    }

    @Override
    public void setup() {
        neuronalNetwork = new NeuronalNetwork(2,4,1);

    }


    @Override
    public void draw() {
        background(41);


        for (int i = 0; i < 1000 ; i++) {
            int pick = ThreadLocalRandom.current().nextInt(0, trainingData.size());
            neuronalNetwork.train(trainingData.get(pick).getInputs(), trainingData.get(pick).getTargets());
        }




        float res = 10;
        float cols = width / res;
        float rows = height / res;

        for (float i = 0; i < cols; i++) {
            for (float j = 0; j < rows; j++) {
                float x1 = i / cols;
                float x2 = j / rows;
                float[] inputs = {x1, x2};
                float[] guess = neuronalNetwork.feedForward(inputs);
                noStroke();
                fill(guess[0] * 255);
                rect(i * res, j * res, res, res);
            }
        }

    }

    public static void main(String[] args) {
        String[] processingArgs = {"MySketch"};
        XOR sketch = new XOR();
        PApplet.runSketch(processingArgs, sketch);
    }

    @Override
    public void keyPressed() {
        if (key == '+' && neuronalNetwork.getLearningRate() + 0.01 < 0.5) {
            neuronalNetwork.setLearningRate(neuronalNetwork.getLearningRate() + 0.01f);
            System.out.println(neuronalNetwork.getLearningRate());
        }
        if (key == '-' && neuronalNetwork.getLearningRate() - 0.01 > 0.01) {
            neuronalNetwork.setLearningRate(neuronalNetwork.getLearningRate() - 0.01f);
            System.out.println(neuronalNetwork.getLearningRate());
        }
    }
}
