package p1.shakespeare;


public class Main {

    public static void main(String[] args) {

        String target = "Heute ist Dienstag der 10. November 2020.";
        int genNumber = 0;
        Population population = new Population(target, 0.01f, 500);

        while (!population.gotTarget){

            population.calcFitness();
            genNumber++;
            System.out.printf("GenNumber: %5d | Best of population: %s\n", genNumber, population.getFittest().toString());
            if (population.gotTarget) break;
            population.naturalSelection();
            population.generateNewPopulation();

        }

    }
}
