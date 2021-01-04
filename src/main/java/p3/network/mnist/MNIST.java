package p3.network.mnist;



import p3.network.Util.NeuronalNetwork;
import p3.network.mnist.util.CsvFileReader;
import p3.network.mnist.util.TestData;
import p3.network.mnist.util.TrainingData;
import processing.core.PApplet;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MNIST extends PApplet {

    NeuronalNetwork neuronalNetwork;
    CsvFileReader trainingSet = new CsvFileReader("src/main/java/p3/network/mnist/data/mnist_train.csv", false);
    CsvFileReader testSet = new CsvFileReader("src/main/java/p3/network/mnist/data/mnist_test.csv", true);

    List<TrainingData> trainingData;
    List<TestData> testData;

    int counter = 0;
    List<Float[][]> testNumbers;
    List<TestData> incorrectTestGuesses;
    List<TrainingData> incorrectTrainingGuesses;

    @Override
    public void settings() {
        size(480,280);

    }

    @Override
    public void setup() {
        trainingSet.readData();
        testSet.readData();
        trainingData = trainingSet.getTrainingData();
        testData = testSet.getTestData();
        testNumbers = testSet.getNumbers();

        for (int l = 2; l <= 1050; l *= 2) {
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            for (int k = 1; k < 10_000_000 ; k *= 2) {
                System.out.println("network with: " + l + " hidden nodes");
                System.out.println("training items: " + k);

                neuronalNetwork = new NeuronalNetwork(784, l ,10);


                for (int i = 0; i < k ; i++) {
                    if (i % 1_000_000 == 0 && i != 0) System.out.println(i);
                    int pick = ThreadLocalRandom.current().nextInt(0, trainingData.size());
                    neuronalNetwork.backPropagation(trainingData.get(pick).getInputs(), trainingData.get(pick).getTargets());

                }


                incorrectTrainingGuesses = new ArrayList<>();
                incorrectTestGuesses = new ArrayList<>();
                int correct = 0;
                int incorrect = 0;

                for (TrainingData data : trainingData) {
                    float[] targets = neuronalNetwork.feedForward(data.getInputs());
                    int[] labelAndGuess = getLabelAndGuess(targets, data.getTargets());
                    if (labelAndGuess[0] == labelAndGuess[1]) correct++;
                    else{
                        incorrectTrainingGuesses.add(data);
                        incorrect++;
                    }
                }

                float ratio = 100 * correct / (float) (incorrect + correct);

                System.out.println("correct: " + correct);
                System.out.println("incorrect: " + incorrect);
                System.out.println("correct percentage: " + ratio);

                correct = 0;
                incorrect = 0;

                for (TestData data : testData) {
                    float[] targets = neuronalNetwork.feedForward(data.getInputs());
                    int[] labelAndGuess = getLabelAndGuess(targets, data.getTargets());
                    if (labelAndGuess[0] == labelAndGuess[1]) correct++;
                    else{
                        incorrectTestGuesses.add(data);
                        incorrect++;
                    }
                }

                ratio = 100 * correct / (float) (incorrect + correct);

                System.out.println("correct: " + correct);
                System.out.println("incorrect: " + incorrect);
                System.out.println("correct percentage: " + ratio);
                System.out.println("=======================================================");
            }
        }




    }


    @Override
    public void draw() {
        float res = 10;

        TestData dataPoint = incorrectTestGuesses.get(counter);
        float[] pixelVals = dataPoint.getInputs();
        int row;
        int col;
        for (int i = 0; i < 784; i++) {
            row = i % 28;
            col = i / 28;
            noStroke();
            fill(pixelVals[i] * 255);
            rect(row * res, col * res, res, res);
        }

        float[] targets = neuronalNetwork.feedForward(dataPoint.getInputs());
        int[] labelAndGuess = getLabelAndGuess(targets, dataPoint.getTargets());
        System.out.println("Label: " + labelAndGuess[0]);
        System.out.println("Guess: " + labelAndGuess[1]);

        noLoop();

    }

    public static void main(String[] args){
        String[] processingArgs = {"Neuronal Network"};
        MNIST MNIST = new MNIST();
        PApplet.runSketch(processingArgs, MNIST);
    }


    @Override
    public void mousePressed() {
        counter++;
        System.out.println("counter: " + counter);
        loop();
    }

    private int[] getLabelAndGuess(float[] targets, float[] labels) {
        int[] labelAndGuess = new int[2];
        for (int i = 0; i < labels.length; i++) {
            if (labels[i] == 1){
                labelAndGuess[0] = i;
                break;
            }
        }
        int indexOfMax = 0;
        for (int i = 1; i < targets.length; i++) {
            if (targets[i] > targets[indexOfMax]) indexOfMax = i;
        }
        labelAndGuess[1] = indexOfMax;

        return labelAndGuess;
    }

}
