package p2;

import processing.core.PApplet;

import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class MazeSolverSketch extends PApplet {

    int  background_color = color (50);
    int squareSize = 50;
    Maze maze;
    Cell current;
    Stack<Cell> visitedCells = new Stack<>();
    boolean generationCompleted = false;
    Cell start;
    Cell target;

    Solver solver;
    Algorithm algorithm = AlgorithmFactory.getAlgorithm("GBFS");



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
        start = current;
        target = maze.cells.get(ThreadLocalRandom.current().nextInt(maze.cells.size()));
    }

    public void draw() {
        background(background_color);

        for (Cell cell : maze.cells) {
            cell.show();
        }

    if (!generationCompleted) {
        List<Cell> neighbours = maze.getNeighbours(current);
        Cell next = null;
        if (neighbours.size() > 0 ) next = neighbours.get(ThreadLocalRandom.current().nextInt(neighbours.size()));
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
        } else  {
            generationCompleted = true;
            solver = new Solver(maze, algorithm, start, target);
            for (Cell cell : maze.cells) {
                cell.calcManhattanDistance(target);
            }
        }
    }

        current.highlight();
    if (generationCompleted && !maze.solved) {
        target.highlight();
        solver.calcNextStep();
        System.out.println("log");
    }



    }

    public static void main(String[] args){
        String[] processingArgs = {"MySketch"};
        MazeSolverSketch mazeSolverSketch = new MazeSolverSketch();
        PApplet.runSketch(processingArgs, mazeSolverSketch);
    }

}
