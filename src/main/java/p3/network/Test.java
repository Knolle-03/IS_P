package p3.network;


import p3.network.Util.NeuronalNetwork;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        NeuronalNetwork neuronalNetwork = new NeuronalNetwork(2, 2, 1);
        float[] inputs = {1, 0};
        float[] targets = {1};
        //float[] erg = neuronalNetwork.feedForward(input);

        neuronalNetwork.train(inputs, targets);

        //System.out.println(Arrays.toString(erg));
    }
}
