package p3.network.Util;


import p3.network.matrix.Matrix ;

public class NeuronalNetwork {
    private int input;
    private final Matrix inputHiddenWeights;
    private final Matrix biasHidden;
    private int hidden;
    private final Matrix hiddenOutputWeights;
    private final Matrix biasOutput;
    private int output;
    private float learningRate = 0.01f;

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
        Matrix outputHidden = inputHiddenWeights.matrixProduct(inputMatrix);
        outputHidden.matrixAddProduct(this.biasHidden);
        outputHidden.sigmoid();

        Matrix outputOutput = hiddenOutputWeights.matrixProduct(outputHidden);
        outputOutput.matrixAddProduct(this.biasOutput);
        outputOutput.sigmoid();
        return outputOutput.toArray();
    }

    public void train(float[] inputsArr, float[] targetsArr) {

        // Generating the Hidden Outputs
        Matrix inputs = new Matrix(inputsArr);
        this.inputHiddenWeights.matrixMultiplyElementProduct(inputs);
        Matrix hidden = new Matrix(inputHiddenWeights);
        hidden.matrixAddProduct(biasHidden);
        // activation function!
        hidden.sigmoid();

        // Generating the output's output!
        this.hiddenOutputWeights.matrixProduct(hidden);
        Matrix outputs = new Matrix(hiddenOutputWeights);
        System.out.println("outputs: ");
        outputs.sout();
        System.out.println("bias output: ");
        biasOutput.sout();
        outputs.matrixAddProduct(biasOutput);
        outputs.sigmoid();

        // Convert array to matrix object
        Matrix targets = new Matrix(targetsArr);

        // Calculate the error
        // ERROR = TARGETS - OUTPUTS
        Matrix output_errors = Matrix.subtract(targets, outputs);

        // let gradient = outputs * (1 - outputs);
        // Calculate gradient
        Matrix gradients = Matrix.derivativeSigmoid(outputs);
        gradients.matrixMultiplyElementProduct(output_errors);
        gradients.scalarMultiplyProduct(this.learningRate);


        // Calculate deltas
        Matrix hidden_T = Matrix.transpose(hidden);
        gradients.matrixMultiplyElementProduct(hidden_T);
        Matrix weight_ho_deltas = new Matrix(gradients);

        // Adjust the weights by deltas
        this.hiddenOutputWeights.matrixAddProduct(weight_ho_deltas);
        // Adjust the bias by its deltas (which is just the gradients)
        this.biasOutput.matrixAddProduct(gradients);

        // Calculate the hidden layer errors
        Matrix who_t = Matrix.transpose(this.hiddenOutputWeights);
        who_t.matrixMultiplyElementProduct(output_errors);
        Matrix hidden_errors = new Matrix(who_t);
        // Calculate hidden gradient
        Matrix hidden_gradient = Matrix.derivativeSigmoid(hidden);
        hidden_gradient.matrixMultiplyElementProduct(hidden_errors);
        hidden_gradient.scalarMultiplyProduct(this.learningRate);

        // Calcuate input->hidden deltas
        Matrix inputs_T = Matrix.transpose(inputs);
        hidden_gradient.matrixMultiplyElementProduct(inputs_T);
        Matrix weight_ih_deltas = new Matrix(hidden_gradient);
        this.inputHiddenWeights.matrixAddProduct(weight_ih_deltas);
        // Adjust the bias by its deltas (which is just the gradients)
        this.biasHidden.matrixAddProduct(hidden_gradient);

        // outputs.print();
        // targets.print();
        // error.print();



//
//        Matrix inputs = new Matrix(inputsArr);
//        Matrix hidden = inputHiddenWeights.matrixProduct(inputs);
//        hidden.matrixAddProduct(this.biasHidden);
//        hidden.sigmoid();
//
//        Matrix outputs = hiddenOutputWeights.matrixProduct(hidden);
//        outputs.matrixAddProduct(this.biasOutput);
//        outputs.sigmoid();
//
//        // convert to matrices
//        Matrix targets = new Matrix(targetsArr);
//
//        // calc error (targets - errors)
//        Matrix outputErrors = Matrix.subtract(targets, outputs);
//
//        // calc gradient
//        Matrix gradients = Matrix.derivativeSigmoid(outputs);
//        gradients.matrixMultiplyElementProduct(outputErrors);
//        gradients.scalarMultiplyProduct(learningRate);
//
//        // calc deltas
//        Matrix hiddenTranspose = Matrix.transpose(hidden);
//        System.out.println("gradients");
//        gradients.sout();
//        System.out.println("----");
//        System.out.println("hiddenTranspose");
//        hiddenTranspose.sout();
//        gradients.matrixMultiplyElementProduct(hiddenTranspose);
//        System.out.println("----");
//        System.out.println("hiddenOutputWeights");
//        hiddenOutputWeights.sout();
//        System.out.println("----");
//        System.out.println("gradients");
//        gradients.sout();
//        hiddenOutputWeights.matrixAddProduct(gradients);
//
//
//        // calc the hidden layer errors
//        Matrix weightsHiddenOutputTranspose = Matrix.transpose(hiddenOutputWeights);
//        Matrix hiddenErrors = weightsHiddenOutputTranspose.matrixProduct(outputErrors);
//
//
//
//        // calc hidden gradient
//        hidden.derivativeSigmoid();
//        hidden.matrixMultiplyElementProduct(hiddenErrors);
//        hidden.scalarMultiplyProduct(learningRate);
//
//        Matrix inputsTranspose = Matrix.transpose(inputs);
//        hidden.matrixMultiplyElementProduct(inputsTranspose);
//        inputHiddenWeights.matrixAddProduct(hidden);
//
//
//
//        outputs.sout();
//        targets.sout();
//        outputErrors.sout();
    }
}
