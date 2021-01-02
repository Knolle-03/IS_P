package p3.network;

public class TrainingData {

    float[] inputs;
    float[] targets;


    public TrainingData(float[] inputs, float[] targets) {
        this.inputs = inputs;
        this.targets = targets;
    }

    public float[] getInputs() {
        return inputs;
    }

    public float[] getTargets() {
        return targets;
    }
}
