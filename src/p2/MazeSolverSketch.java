package p2;

import p2.algorithms.Algorithm;
import processing.core.PApplet;

import java.util.*;

import static java.util.Map.*;

public class MazeSolverSketch extends PApplet {

    int background_color = color (50);

    // draw loops per second while generating
    int GENERATION_FPS = 120;
    // draw loops per second during while solving
    int SOLVE_FPS = 20;

    // map to choose an algorithm by id
    Map<Integer, String> algorithms = Map.ofEntries(
            entry(0, "BFS"),
            entry(1, "GBFS"),
            entry(2, "A*"),
            entry(3, "DFS"),
            entry(4, "IDA*"),
            entry(5, "IDS"));

    // name of selected algorithm
    String ALGORITHM_NAME = algorithms.get(1);
    // size of each cell in pixels
    int squareSize = 50;
    // width of window
    int WIDTH = 3400;
    // height of window
    int HEIGHT = 1200;
    // display as fullscreen window
    boolean fullscreen = false;

    Maze maze;
    boolean drawGeneration = false;
    boolean drawAlgorithm = true;
    boolean init = false;
    Cell target;

    Algorithm algorithm;



    public void settings() {
        if (fullscreen) fullScreen(P3D);
        else size(WIDTH, HEIGHT, P3D);
    }

    @Override
    public void setup() {
        frameRate(GENERATION_FPS);

        // calc additional wall to remove (last float equals percentage i.e. 0.1 = 10%)
        int additionalWallRemoves = floor(width / (float) squareSize * height / (float) squareSize * 0.1f);
        // init new maze
        maze = new Maze(this, width, height, squareSize, additionalWallRemoves);
        // set target cell
        target = maze.cells.get(maze.cells.size() - 1);
        // set algorithm to solve the maze
        algorithm = AlgorithmFactory.getAlgorithm(ALGORITHM_NAME, maze);
        // generate whole maze in setup if it should not be drawn
        if (!drawGeneration) {
            maze.generate();
        }
        // solve whole maze in setup if it should not be drawn
        if (!drawAlgorithm) {
            algorithm.solve();
        }

    }

    public void draw() {
        // draw background every frame
        background(background_color);

        // if maze is not fully generated
        if (drawGeneration && !maze.generationCompleted ){
            // generate next cell
            maze.nextStep();

            // if generation should be drawn
            if (drawGeneration) {
                // draw each cell
                for (Cell cell : maze.cells) {
                    cell.show();
                }
            }
        }

        // if maze generation is done (do once)
        if (maze.generationCompleted && !init) {
            init = true;
            // calc manhattan distance of each cell
            for (Cell cell : maze.cells) {
                cell.calcManhattanDistance(target);
            }
            frameRate(SOLVE_FPS);
            System.out.println("Generation done.");
            // stop draw loop in starting position.

        }

        // while maze is being solved
        if (drawAlgorithm && maze.generationCompleted && !maze.solved) {

            // calc next step
            algorithm.calcNextStep();

            // if solving should be drawn
            if (drawAlgorithm) {
                //draw  each cell
                for (Cell cell : maze.cells) {
                    cell.show();
                }
            }

        }

        // when maze is solved
        if (maze.generationCompleted && maze.solved) {
            for (Cell cell : maze.cells) {
                cell.show();
            }
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
