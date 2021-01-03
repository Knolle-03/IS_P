package p3.network.mnist.util;

import java.util.Arrays;

public class TestData {

    float[] inputs;
    float[] targets;


    public TestData(float[] inputs, float[] targets) {
        this.inputs = inputs;
        this.targets = targets;
    }

    public float[] getInputs() {
        return inputs;
    }

    public float[] getTargets() {
        return targets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingData data = (TrainingData) o;
        return Arrays.equals(inputs, data.inputs) && Arrays.equals(targets, data.targets);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(inputs);
        result = 31 * result + Arrays.hashCode(targets);
        return result;
    }
}
