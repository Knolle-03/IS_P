package p3.network;


import p3.network.Util.NeuronalNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Test {
    public static void main(String[] args) {

        float[] stateOne = {0, 0};
        float[] stateTwo = {0, 1};
        float[] stateThree = {1, 0};
        float[] stateFour = {1, 1};

        float[] zero = {0};
        float[] one = {1};

        TrainingData dataPointOne = new TrainingData(stateOne, zero);
        TrainingData dataPointTwo = new TrainingData(stateTwo, one);
        TrainingData dataPointThree = new TrainingData(stateThree, one);
        TrainingData dataPointFour = new TrainingData(stateFour, zero);

        List<TrainingData> trainingData = new ArrayList<>();
        trainingData.add(dataPointOne);
        trainingData.add(dataPointTwo);
        trainingData.add(dataPointThree);
        trainingData.add(dataPointFour);





        NeuronalNetwork neuronalNetwork = new NeuronalNetwork(2, 2, 1);


        for (int i = 0; i < 250000; i++) {
            int rnd = ThreadLocalRandom.current().nextInt(0, trainingData.size());
            neuronalNetwork.train(trainingData.get(rnd).inputs, trainingData.get(rnd).targets);
        }

        System.out.println(Arrays.toString(neuronalNetwork.feedForward(stateOne)));
        System.out.println(Arrays.toString(neuronalNetwork.feedForward(stateTwo)));
        System.out.println(Arrays.toString(neuronalNetwork.feedForward(stateThree)));
        System.out.println(Arrays.toString(neuronalNetwork.feedForward(stateFour)));



//        float[] inputs = {1, 0};
//        float[] targets = {1, 0};
//        float[] erg = neuronalNetwork.feedForward(inputs);
//        neuronalNetwork.train(inputs, targets);
//        System.out.println(Arrays.toString(erg));
    }
}
