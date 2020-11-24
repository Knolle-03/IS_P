package p2;

import processing.core.PApplet;

import java.util.Stack;

public class MazeSolverSketch extends PApplet {

    int rows;
    int cols;
    int  background_color = color (50);
    int c_size;
    int squareSize = 10;
    Maze maze;
    Cell current;
    Stack<Cell> visitedCells = new Stack<>();



    public void settings() {
        size(600, 400);

    }

    @Override
    public void setup() {
        frameRate(120);
        maze = new Maze(this, width, height, squareSize);

        // STEP 1
        current = maze.cells.get(0);
        current.visited = true;
    }

    public void draw() {
        background(background_color);

        for (int i = 0; i < maze.cells.size(); i++) {
            maze.cells.get(i).show();
        }


        Cell next = maze.getRandomNeighbour(current);
        //STEP 2.1.1
        if (next != null) {
            // STEP 2.1.2
            visitedCells.push(current);
            // STEP 2.1.3
            maze.removeWall(current, next);
            // STEP 2.1.4
            current = next;
            current.visited = true;
        // STEP 2.2.0
        } else if (!visitedCells.empty()) {
            // 2.2.1 + 2.2.2
            current = visitedCells.pop();
        }
        current.highlight();

    }

    public static void main(String[] args){
        String[] processingArgs = {"MySketch"};
        MazeSolverSketch mazeSolverSketch = new MazeSolverSketch();
        PApplet.runSketch(processingArgs, mazeSolverSketch);
    }

}
