package p2.algorithms;

import p2.Cell;
import p2.Maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecursiveBestFirstSearch implements Algorithm {

    Maze maze;
    List<Cell> openList = new ArrayList<>();
    List<Cell> closedList = new ArrayList<>();
    Cell current;
    Cell start;
    Cell target;
    Map<Cell, Cell> nextToPrevMap = new HashMap<>();
    Cell minCostCell;
    int pathLength;
    int calcNextStepCalls = 0;

    public RecursiveBestFirstSearch(Maze maze) {
        this.maze = maze;
        this.current = maze.getCells().get(0);
        this.start = current;
        this.target = maze.getCells().get(maze.getCells().size());
        this.nextToPrevMap = new HashMap<>();
    }

    //    RBFS (node: N, value: F(N), bound: B)
//
//    IF f(N)>B, RETURN f(N)
//
//    IF N is a goal, EXIT algorithm
//
//    IF N has no children, RETURN infinity
//
//    FOR each child Ni of N,
//
//    IF f(N)<F(N), F[i] := MAX(F(N),f(Ni))
//
//    ELSE F[i] := f(Ni)
//
//    sort Ni and F[i] in increasing order of F[i]
//
//    IF only one child, F[2] := infinity
//
//    WHILE (F[1] <= B and F[1] < infinity)
//
//    F[1] := RBFS(N1, F[1], MIN(B, F[2]))
//
//    insert Ni and F[1] in sorted order
//
//    RETURN F[1]



    @Override
    public void calcNextStep() {

    }

    private void search() {}

    @Override
    public List<Cell> getCurrentCells() {
        return null;
    }

    @Override
    public void solve() {

    }

    @Override
    public Maze getMaze() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
