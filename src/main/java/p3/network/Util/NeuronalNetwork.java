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

    public void backPropagation(float[] inputsArr, float[] targetsArr) {

        // feed forward part
        Matrix inputs = new Matrix(inputsArr);
        Matrix hidden = inputHiddenWeights.matrixProduct(inputs);
        hidden.matrixAddProduct(this.biasHidden);
        hidden.sigmoid();

        Matrix outputs = hiddenOutputWeights.matrixProduct(hidden);
        outputs.matrixAddProduct(this.biasOutput);
        outputs.sigmoid();

        // converts targets array to matrix
        Matrix targets = new Matrix(targetsArr);

        // calc hidden to output errors
        Matrix outputErrors = Matrix.subtract(targets, outputs);

        // calc gradients (outputs) * (1 - outputs)
        Matrix gradients = Matrix.derivativeSigmoid(outputs);
        gradients = gradients.matrixMultiplyElementProduct(outputErrors);
        gradients.scalarMultiplyProduct(learningRate);

        // calc deltas of the weights from hidden to output.
        Matrix hiddenTranspose = Matrix.transpose(hidden);
        Matrix weightsHiddenToOutputDeltas = gradients.matrixProduct(hiddenTranspose);

        // adjust hidden to output weights
        hiddenOutputWeights.matrixAddProduct(weightsHiddenToOutputDeltas);

        // adjust output bias
        biasOutput.matrixAddProduct(gradients);

        // calc hidden layer error
        Matrix hiddenOutputWeightsTransposed = Matrix.transpose(hiddenOutputWeights);
        Matrix hiddenErrors = hiddenOutputWeightsTransposed.matrixProduct(outputErrors);

        // calc hidden gradient
        Matrix hiddenGradient = Matrix.derivativeSigmoid(hidden);
        hiddenGradient = hiddenGradient.matrixMultiplyElementProduct(hiddenErrors);
        hiddenGradient.scalarMultiplyProduct(learningRate);

        // calc input to hidden deltas
        Matrix inputsTranspose = Matrix.transpose(inputs);
        Matrix weightsInputsToHiddenDeltas = hiddenGradient.matrixProduct(inputsTranspose);

        // adjust input to hidden layer
        inputHiddenWeights.matrixAddProduct(weightsInputsToHiddenDeltas);

        // adjust hidden bias
        biasHidden.matrixAddProduct(hiddenGradient);

    }
}
