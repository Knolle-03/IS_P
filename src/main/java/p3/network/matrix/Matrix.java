package p3.network.matrix;

import java.util.Arrays;
import java.util.Random;

public class Matrix {
    private int rows;
    private int cols;
    private float[][] data;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new float[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = 0;
            }
        }
    }

    public Matrix(float[] array) {
        this.rows = array.length;
        this.cols = 1;
        this.data = new float[rows][cols];
        for (int i = 0; i < array.length; i++) {
            data[i][0] = array[i];
        }
    }

    public void fillWithRandomValues() {
        Random random = new Random();
        float rangeMin = -1.0f;
        float rangeMax = 1.0f;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = rangeMin + (rangeMax - rangeMin) * random.nextFloat();
            }
        }
    }

    public Matrix transpose() {
        Matrix newMatrix = new Matrix(cols, rows);
        float[][] newData = new float[cols][rows];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                newData[col][row] = data[row][col];
            }
        }
        newMatrix.setData(newData);
        return newMatrix;
    }

    public Matrix matrixProduct(Matrix matrix) {
        if (this.cols != matrix.rows) {
            throw new RuntimeException();
        }
        Matrix newMatrix = new Matrix(this.rows, matrix.cols);
        float[][] newData = new float[this.rows][matrix.cols];
        for (int row = 0; row < newMatrix.rows; row++) {
            for (int col = 0; col < newMatrix.cols; col++) {
                for (int i = 0; i < cols; i++) {
                    newData[row][col] += data[row][i] * matrix.data[i][col];
                }
            }
        }
        newMatrix.setData(newData);
        return newMatrix;
    }

    public void sigmoid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = (float) (1 / (1 + Math.exp(-data[row][col])));
            }
        }
    }

    public void matrixMultiplyElementProduct(Matrix matrix) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = data[row][col] * matrix.data[row][col];
            }
        }
    }

    public void matrixAddProduct(Matrix matrix) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = data[row][col] + matrix.data[row][col];
            }
        }
    }

    public void scalarMultiplyProduct(int x) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = data[row][col] * x;
            }
        }
    }

    public void scalarAddProduct(int x) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = data[row][col] + x;
            }
        }
    }

    public float[] toArray() {
        float[] retVal = new float[rows * cols];
        int i = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                retVal[i] = data[row][col];
                i++;
            }
        }
        return retVal;
    }

    public void sout() {
        for (int row = 0; row < rows; row++) {
            System.out.println(Arrays.toString(data[row]));
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public float[][] getData() {
        return data;
    }

    public void setData(float[][] data) {
        this.data = data;
    }
}
