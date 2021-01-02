package p3.network.Util;


import p3.network.matrix.Matrix;

public class NeuronalNetwork {
    private int input;
    private Matrix input_hidden_weights;
    private Matrix bias_hidden;
    private int hidden;
    private Matrix hidden_output_weights;
    private Matrix bias_output;
    private int output;

    public NeuronalNetwork(int input, int hidden, int output) {
        this.input = input;
        this.input_hidden_weights = new Matrix(input, hidden);
        this.input_hidden_weights.fillWithRandomValues();
        this.bias_hidden = new Matrix(hidden, 1);
        this.bias_hidden.fillWithRandomValues();
        this.hidden = hidden;
        this.hidden_output_weights = new Matrix(hidden, output);
        this.hidden_output_weights.fillWithRandomValues();
        this.bias_output = new Matrix(hidden, 1);
        this.bias_output.fillWithRandomValues();
        this.output = output;
    }

    public float[] feedForward(float[] input) {
        Matrix inputMatrix = new Matrix(input);
        Matrix input_hidden_weights2 = input_hidden_weights.transpose();
        Matrix outputHidden = input_hidden_weights2.matrixProduct(inputMatrix);
        outputHidden.matrixAddProduct(this.bias_hidden);
        outputHidden.sigmoid();
        Matrix hidden_output_weights2 = hidden_output_weights.transpose();
        Matrix outputOutput = hidden_output_weights2.matrixProduct(outputHidden);
        outputOutput.matrixAddProduct(this.bias_output);
        outputOutput.sigmoid();
        return outputOutput.toArray();
    }

    public void train(float[] input, float[] target) {

    }
}
