package p2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GreedyBestFirstSearch implements Algorithm {
    @Override
    public List<Cell> calcNextStep(List<Cell> openList, List<Cell> current, Cell target) {
        Cell minCostCell = openList.get(0);
        if (openList.size() > 1) {
            for (Cell neighbour : openList) {
                neighbour.isExplored = true;
                if (neighbour.manhattanDistance < minCostCell.manhattanDistance) minCostCell = neighbour;
            }
        }
        return new ArrayList<>(Collections.singletonList(minCostCell));
    }
}
