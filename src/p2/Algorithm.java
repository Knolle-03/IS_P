package p2;

import java.util.List;

public interface Algorithm {
    List<Cell> calcNextStep(List<Cell> openList, List<Cell> current, Cell target);
}
