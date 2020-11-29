package p2.algorithms;

import p2.Cell;
import p2.Maze;

import java.util.List;
import java.util.Map;

public interface Algorithm {
    void calcNextStep();
    List<Cell> getCurrentCells();
    void solve();
}
