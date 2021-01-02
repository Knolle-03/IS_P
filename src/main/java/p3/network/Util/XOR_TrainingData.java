package p3.network.Util;

import p3.network.TrainingData;

import java.util.ArrayList;
import java.util.List;

public class XOR_TrainingData {


    public static List<TrainingData> getTrainingData() {
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

        return trainingData;
    }

}
