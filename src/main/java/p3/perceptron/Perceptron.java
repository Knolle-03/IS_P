package p3.perceptron;

public class Perceptron {

    float[] weights;

    PerceptronSketch sketch;
    private final float learningRate;

    public Perceptron(PerceptronSketch sketch, int n, float learningRate) {
        this.learningRate = learningRate;
        weights = new float[n];
        this.sketch = sketch;
        for (int i = 0; i < weights.length; i++) {
            weights[i] = sketch.random(-1, 1);
        }

    }

    public int guess(float[] inputs) {
        float sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i] * weights[i];
        }
        return sign(sum);
    }

    public void train(float[] inputs, int target) {
        int guess = guess(inputs);
        int error = target - guess;
        if (error != 0) System.out.println("still error");
        for (int i = 0; i < weights.length; i++) {
            weights[i] += error * inputs[i] * learningRate;
        }
    }


    // activation function
    private int sign(float n) {
        return n < 0 ? -1 : 1;
    }

    float guessY(float x) {
        return -(weights[2] / weights[1]) - (weights[0]/weights[1]) * x;
    }





}
