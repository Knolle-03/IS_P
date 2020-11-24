package p2;

public class AlgorithmFactory {

    public static Algorithm getAlgorithm(String name) {
        if (name == null) return null;
        else if (name.equals("GBFS")) return new GreedyBestFirstSearch();

        return null;
    }
}
