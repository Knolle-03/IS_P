package p2;

import p2.algorithms.Algorithm;
import processing.core.PApplet;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Map.*;

public class MazeSolverSketch extends PApplet {

    int GENERATION_FPS = 120;
    int SOLVE_FPS = 120;

    // map to choose an algorithm by id
    Map<Integer, String> algorithms = Map.ofEntries(
            entry(0, "BFS"),
            entry(1, "GBFS"),
            entry(2, "A*"),
            entry(3, "DFS"),
            entry(4, "IDA*"),
            entry(5, "IDS"));

    String ALGORITHM_NAME = algorithms.get(4);
    int background_color = color (50);
    int squareSize = 25;
    int WIDTH = 3400;
    int HEIGHT = 1300;
    Maze maze;
    Cell current;
    List<Cell> start = new ArrayList<>();
    Stack<Cell> visitedCells = new Stack<>();
    boolean generationCompleted = false;
    Cell target;

    Algorithm algorithm;
    int additionalWallRemoves;



    public void settings() {
        size(WIDTH, HEIGHT, P3D);
    }

    @Override
    public void setup() {
        frameRate(GENERATION_FPS);
        maze = new Maze(this, width, height, squareSize);
        additionalWallRemoves = floor(width / (float) squareSize * height / (float) squareSize * 0.1f);

        // STEP 1
        current = maze.cells.get(0);
        current.visited = true;
        start.add(current);
        //target = maze.cells.get(ThreadLocalRandom.current().nextInt(maze.cells.size()));
        target = maze.cells.get(maze.cells.size() - 1);
        noLoop();
    }

    public void draw() {
        background(background_color);

        for (Cell cell : maze.cells) {
            cell.show();
        }

        if (!generationCompleted) {
            current.isCurrent = false;


            List<Cell> neighbours = maze.getNeighbours(current);
            Cell next = null;
            if (neighbours.size() > 0) next = neighbours.get(ThreadLocalRandom.current().nextInt(neighbours.size()));

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

            } else {
                generationCompleted = true;
                frameRate(SOLVE_FPS);
                for (Cell cell : maze.cells) {
                    cell.calcManhattanDistance(target);
                }
                for (int i = 0; i < additionalWallRemoves; i++) {
                    int cellIndex = ThreadLocalRandom.current().nextInt(maze.cells.size());
                    maze.removeRandomWall(maze.getCells().get(cellIndex));
                }
                algorithm = AlgorithmFactory.getAlgorithm(ALGORITHM_NAME, maze);
                System.out.println("Generation done.");
                noLoop();
            }
            current.isCurrent = true;

        }

        if (generationCompleted && !maze.solved) {
            algorithm.calcNextStep();
        }

    }
    @Override
    public void mousePressed() {
        looping = !looping;
    }



    public static void main(String[] args){
        String[] processingArgs = {"MySketch"};
        MazeSolverSketch mazeSolverSketch = new MazeSolverSketch();
        PApplet.runSketch(processingArgs, mazeSolverSketch);
    }

}
