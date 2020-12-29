package p2.algorithms;

import p2.Cell;
import p2.Maze;

import java.util.List;

public interface Algorithm {
    void calcNextStep();
    List<Cell> getCurrentCells();
    void solve();
    Maze getMaze();
    String getName();
    String getInfo();
}
