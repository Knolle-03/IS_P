package p3.network;


import p3.network.Util.NeuronalNetwork;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        NeuronalNetwork neuronalNetwork = new NeuronalNetwork(2, 4, 1);
        float[] input = {1f, 0.5424245f};
        float[] erg = neuronalNetwork.feedForward(input);
        System.out.println(Arrays.toString(erg));
    }
}
