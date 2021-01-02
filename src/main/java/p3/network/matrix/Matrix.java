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

    public Matrix(Matrix matrix) {
        this.rows = matrix.rows;
        this.cols = matrix.cols;
        this.data = matrix.data;
    }

    public Matrix(float[] array) {
        this.rows = array.length;
        this.cols = 1;
        this.data = new float[rows][cols];
        for (int i = 0; i < array.length; i++) {
            data[i][0] = array[i];
        }
    }

    public static Matrix subtract(Matrix a, Matrix b) {
//        b.scalarMultiplyProduct(-1);
//        a.matrixAddProduct(b);
//        return a;
        Matrix result = new Matrix(a.rows, a.cols);
        for (int row = 0; row < a.rows; row++) {
            for (int col = 0; col < a.cols; col++) {
                result.data[row][col] = a.data[row][col] - b.data[row][col];
            }
        }
        return result;
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

    public static Matrix transpose(Matrix matrix) {
        Matrix newMatrix = new Matrix(matrix.cols, matrix.rows);
        float[][] newData = new float[matrix.cols][matrix.rows];
        for (int row = 0; row < matrix.rows; row++) {
            for (int col = 0; col < matrix.cols; col++) {
                newData[col][row] = matrix.data[row][col];
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

    public void derivativeSigmoid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = (data[row][col] * (1 - data[row][col]));
            }
        }
    }

    public static Matrix derivativeSigmoid(Matrix matrix) {
        Matrix result = new Matrix(matrix.rows, matrix.cols);
        for (int row = 0; row < matrix.rows; row++) {
            for (int col = 0; col < matrix.cols; col++) {
                result.data[row][col] = (matrix.data[row][col] * (1 - matrix.data[row][col]));
            }
        }
        return result;
    }

    public void matrixMultiplyElementProduct(Matrix matrix) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = data[row][col] * matrix.data[row][col];
            }
        }
    }

    public void matrixAddProduct(Matrix matrix) {
        System.out.println("Rows: " + rows + " Cols: " + cols);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = data[row][col] + matrix.data[row][col];
                System.out.println(data[row][col]);

            }
        }
    }

    public void scalarMultiplyProduct(float x) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = data[row][col] * x;
            }
        }
    }

    public void scalarAddProduct(float x) {
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
