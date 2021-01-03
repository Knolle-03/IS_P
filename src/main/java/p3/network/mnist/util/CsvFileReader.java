package p3.network.mnist.util;

import p3.network.TrainingData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReader {

    List<Float[][]> numbers = new ArrayList<>();
    List<TrainingData> trainingData;
    List<TestData> testData;
    String path;
    boolean isTestData;
    public CsvFileReader(String path, boolean isTestData) {
        this.isTestData = isTestData;
        this.path = path;

        if (isTestData) testData = new ArrayList<>();
        else trainingData = new ArrayList<>();
    }

    public void readData() {

        try (BufferedReader csvReader = new BufferedReader(new FileReader(path) )) {
            String row;

            while ((row = csvReader.readLine()) != null) {
                String[] csvData = row.split(",");
                float[] data = new float[28 * 28];
                float[] labels = new float[10];
                Float[][] number = new Float[28][28];


                for (int i = 0; i < csvData.length - 1; i++) {
                    int row_ = i / 28;
                    int col_ = i % 28;
                    // normalize
                    data[i] = Integer.parseInt(csvData[i + 1]) / 255f;
                    number[col_][row_] = Float.parseFloat(csvData[i + 1]);
                }
                int label = Integer.parseInt(csvData[0]);
                labels[label] = 1;

                if (isTestData) testData.add(new TestData(data, labels));
                else trainingData.add(new TrainingData(data, labels));
                numbers.add(number);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("File not found");
        }


    }

    public List<Float[][]> getNumbers() {
        return numbers;
    }

    public List<TrainingData> getTrainingData() {
        return trainingData;
    }

    public List<TestData> getTestData() {
        return testData;
    }
}
