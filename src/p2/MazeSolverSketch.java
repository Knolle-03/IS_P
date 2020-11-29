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
    Map<Integer, String> algorithmsMap = Map.ofEntries(
            entry(0, "BFS"),
            entry(1, "GBFS"),
            entry(2, "A*"),
            entry(3, "DFS"),
            entry(4, "IDA*"));
    // size of each cell in pixels
    int squareSize = 50;
    // name of selected algorithm
    String ALGORITHM_NAME = algorithmsMap.get(1);
    // width of window
    int WIDTH = 1800;
    // height of window
    int HEIGHT = 1000;

    int textWindowWidth = WIDTH / 5;




    // display as fullscreen window
    boolean fullscreen = false;

    Maze originalMaze;
    List<Algorithm> algorithms = new ArrayList<>();
    Algorithm currentAlgorithm;
    boolean drawGeneration = false;
    boolean drawAlgorithm = false;
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

        // calc additional wall to remove (last float equals percentage i.e. 0.1f = 10%)
        int additionalWallRemoves = floor(width / (float) squareSize * height / (float) squareSize * 0.1f);
        // init new maze
        originalMaze = new Maze(this, width - textWindowWidth, height, squareSize, additionalWallRemoves);
        // set target cell
        target = originalMaze.cells.get(originalMaze.cells.size() - 1);
        // set algorithm to solve the maze
        // generate whole maze in setup if it should not be drawn
        if (!drawGeneration) {
            originalMaze.generate();
            // calc manhattan distance of each cell
            for (Cell cell : originalMaze.cells) {
                cell.calcManhattanDistance(target);
            }
        }
        // solve whole maze in setup if it should not be drawn
        if (!drawAlgorithm) {
            for (int i = 0; i < algorithmsMap.size(); i++) {
                Algorithm algorithm = AlgorithmFactory.getAlgorithm(algorithmsMap.get(i), originalMaze.duplicate());
                algorithm.solve();
                algorithms.add(algorithm);
            }
            init();
        }

    }

    public void draw() {
        // draw background every frame
        background(background_color);

        // if maze is not fully generated
        if (drawGeneration && !originalMaze.generationCompleted ){
            // generate next cell
            originalMaze.nextStep();

            // if generation should be drawn
            if (drawGeneration) {
                // draw each cell
                for (Cell cell : originalMaze.cells) {
                    cell.show();
                }
            }
        }

        // if maze generation is done (do once)
        if (originalMaze.generationCompleted && !init) {
            init();
        }

        // while maze is being solved
        if (drawAlgorithm && originalMaze.generationCompleted && !currentAlgorithm.getMaze().isSolved()) {
            drawInfo();
            // calc next step
            currentAlgorithm.calcNextStep();

            // if solving should be drawn
            if (drawAlgorithm) {
                //draw  each cell
                for (Cell cell : currentAlgorithm.getMaze().cells) {
                    cell.show();
                }
            }

        }

        // when maze is solved
        if (originalMaze.generationCompleted && currentAlgorithm.getMaze().isSolved()) {
            drawInfo();
            for (Cell cell : currentAlgorithm.getMaze().cells) {
                cell.show();
            }
        }

    }

    private void init() {
        init = true;

        if (drawAlgorithm) {
            frameRate(SOLVE_FPS);
            for (int i = 0; i < algorithmsMap.size(); i++) {
                algorithms.add(AlgorithmFactory.getAlgorithm(algorithmsMap.get(i), originalMaze.duplicate()));
            }
        }
        currentAlgorithm = algorithms.get(0);
        System.out.println("Generation done.");
        // stop draw loop in starting position.
        noLoop();
    }

    @Override
    public void keyPressed() {
        System.out.println(key);

        if (key == '0') {
            currentAlgorithm = algorithms.get(0);
            redraw();
        } else if ( key == '1') {
            currentAlgorithm = algorithms.get(1);
            redraw();
        } else if ( key == '2') {
            currentAlgorithm = algorithms.get(2);
            redraw();
        } else if ( key == '3') {
            currentAlgorithm = algorithms.get(3);
            redraw();
        } else if ( key == '4') {
            currentAlgorithm = algorithms.get(4);
            redraw();
        }
    }


    public void drawInfo() {
        textAlign(LEFT);
        noStroke();
        fill(255);
        textSize(20);
        text(currentAlgorithm.getInfo(), width - textWindowWidth  + 10, 100);
        textSize(16);
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
