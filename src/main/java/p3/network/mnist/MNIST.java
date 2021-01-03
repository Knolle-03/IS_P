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
        neuronalNetwork = new NeuronalNetwork(784, 256, 10);
//
//
//        for (int i = 0; i < 1000 ; i++) {
//            if (i % 1000 == 0) System.out.println(i);
//            int pick = ThreadLocalRandom.current().nextInt(0, trainingData.size());
//            neuronalNetwork.backPropagation(trainingData.get(pick).getInputs(), trainingData.get(pick).getTargets());
//
//        }
//
//        Map<Integer, float[]> incorrectGuesses = new HashMap<>();
//        int correct = 0;
//        int incorrect = 0;
//
//        for (TrainingData data : trainingData) {
//            float[] targets = neuronalNetwork.feedForward(data.getInputs());
//            int[] labelAndGuess = getLabelAndGuess(targets, data.getTargets());
//            if (labelAndGuess[0] == labelAndGuess[1]) correct++;
//            else{
//                incorrectGuesses.put(labelAndGuess[0], data.getInputs());
//                incorrect++;
//            }
//        }
//
//        float ratio = 100 * correct / (float) (incorrect + correct);
//
//        System.out.println("correct: " + correct);
//        System.out.println("incorrect: " + incorrect);
//        System.out.println("correct percentage: " + ratio);
//
//        correct = 0;
//        incorrect = 0;
//
//        for (TestData data : testData) {
//            float[] targets = neuronalNetwork.feedForward(data.getInputs());
//            int[] labelAndGuess = getLabelAndGuess(targets, data.getTargets());
//            if (labelAndGuess[0] == labelAndGuess[1]) correct++;
//            else{
//                incorrectGuesses.put(labelAndGuess[0], data.getInputs());
//                incorrect++;
//            }
//        }
//
//        ratio = 100 * correct / (float) (incorrect + correct);
//
//        System.out.println("correct: " + correct);
//        System.out.println("incorrect: " + incorrect);
//        System.out.println("correct percentage: " + ratio);

    }


    @Override
    public void draw() {
        float res = 10;
        float cols = width / res;
        float rows = height / res;
        System.out.println("in draw");

        for (int i = 0; i < rows; i++) {
            //System.out.println(Arrays.toString(firstNum[i]));
            for (int j = 0; j < cols; j++) {
                noStroke();
                fill(testNumbers.get(counter)[i][j]);
                rect(i * res, j * res, res, res);
            }
        }

        TestData dataPoint = testData.get(counter);
        float[] targets = neuronalNetwork.feedForward(dataPoint.getInputs());
        int[] labelAndGuess = getLabelAndGuess(targets, dataPoint.getTargets());
        System.out.println("Label: " + labelAndGuess[0]);
        System.out.println("Guess: " + labelAndGuess[1]);



//        int index = 0;
//        for (int i = 0; i < trainingData.get(counter).getTargets().length; i++) {
//            if (trainingData.get(counter).getTargets()[i] == 1){
//                index = i;
//                break;
//            }
//
//        }
//        System.out.println("Target: " + index);
//        float[] guess = neuronalNetwork.feedForward(trainingData.get(counter).getInputs());
//        int indexOfMax = 0;
//        for (int i = 1; i < guess.length; i++) {
//            if (guess[i] > guess[indexOfMax]) indexOfMax = i;
//        }
//        System.out.println("Guess: " + indexOfMax);
//        System.out.println(Arrays.toString(guess));
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
