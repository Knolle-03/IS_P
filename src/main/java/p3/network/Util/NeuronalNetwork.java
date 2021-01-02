package p3.network.Util;


import lombok.Data;
import p3.network.matrix.Matrix ;
@Data
public class NeuronalNetwork {
    private int input;
    private final Matrix inputHiddenWeights;
    private final Matrix biasHidden;
    private int hidden;
    private final Matrix hiddenOutputWeights;
    private final Matrix biasOutput;
    private int output;
    private float learningRate = 0.05f;

    public NeuronalNetwork(int input, int hidden, int output) {
        this.input = input;
        this.hidden = hidden;
        this.output = output;

        this.inputHiddenWeights = new Matrix(hidden, input);
        this.hiddenOutputWeights = new Matrix(output, hidden);

        this.inputHiddenWeights.fillWithRandomValues();
        this.hiddenOutputWeights.fillWithRandomValues();

        this.biasHidden = new Matrix(hidden, 1);
        this.biasOutput = new Matrix(output, 1);

        this.biasHidden.fillWithRandomValues();
        this.biasOutput.fillWithRandomValues();

    }

    public float[] feedForward(float[] input) {
        Matrix inputMatrix = new Matrix(input);
        Matrix hidden = inputHiddenWeights.matrixProduct(inputMatrix);
        hidden.matrixAddProduct(this.biasHidden);
        hidden.sigmoid();

        Matrix output = hiddenOutputWeights.matrixProduct(hidden);
        output.matrixAddProduct(this.biasOutput);
        output.sigmoid();

        return output.toArray();
    }

    public void train(float[] inputsArr, float[] targetsArr) {

        Matrix inputs = new Matrix(inputsArr);
        Matrix hidden = inputHiddenWeights.matrixProduct(inputs);
        hidden.matrixAddProduct(this.biasHidden);
        hidden.sigmoid();

        Matrix outputs = hiddenOutputWeights.matrixProduct(hidden);
        outputs.matrixAddProduct(this.biasOutput);
        outputs.sigmoid();

        Matrix targets = new Matrix(targetsArr);

        // calc hidden to output errors
        Matrix outputErrors = Matrix.subtract(targets, outputs);

        Matrix gradients = Matrix.derivativeSigmoid(outputs);
        gradients = gradients.matrixMultiplyElementProduct(outputErrors);
        gradients.scalarMultiplyProduct(learningRate);


        Matrix hiddenTranspose = Matrix.transpose(hidden);
        Matrix weightsHiddenToOutputDeltas = gradients.matrixProduct(hiddenTranspose);

        hiddenOutputWeights.matrixAddProduct(weightsHiddenToOutputDeltas);

        biasOutput.matrixAddProduct(gradients);


        //
        Matrix hiddenOutputWeightsTransposed = Matrix.transpose(hiddenOutputWeights);
        Matrix hiddenErrors = hiddenOutputWeightsTransposed.matrixProduct(outputErrors);

        Matrix hiddenGradient = Matrix.derivativeSigmoid(hidden);
        hiddenGradient = hiddenGradient.matrixMultiplyElementProduct(hiddenErrors);
        hiddenGradient.scalarMultiplyProduct(learningRate);

        Matrix inputsTranspose = Matrix.transpose(inputs);
        Matrix weightsInputsToHiddenDeltas = hiddenGradient.matrixProduct(inputsTranspose);

        inputHiddenWeights.matrixAddProduct(weightsInputsToHiddenDeltas);
        biasHidden.matrixAddProduct(hiddenGradient);

    }
}
