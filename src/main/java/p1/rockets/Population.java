package p1.rockets;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Population {

  List<Rocket> pool = new ArrayList<>();
  Target target;
  PApplet sketch;
  Rocket fittest;
  Rocket worst;
  float averageDist;
  PVector startPos;
  SelectionMethod selectionMethod;
  int lifespan;
  int gen = 0;



  List<Integer> hits = new ArrayList<>();
  List<Float> bestDistances = new ArrayList<>();
  List<Integer> bestImpactAges = new ArrayList<>();

  public Population(PApplet sketch, int lifespan, float mutationRate, int size, Target target, SelectionMethod selectionMethod, PVector startPos) {

    for (int i = 0; i < size; i++) {
      pool.add(new Rocket(sketch, lifespan, mutationRate, startPos));
    }

    this.startPos = startPos;
    this.lifespan = lifespan;
    this.target = target;
    this.sketch = sketch;
    this.selectionMethod = selectionMethod;

  }


  public void evaluate() {
    calcFitnessAndGetExtremes();
    int counter = 0;
    for (int i = 0; i < pool.size(); i++) {
      if (pool.get(i).targetHit) counter++;

    }
    averageDist = 0;
    float bestDist = Float.MAX_VALUE;
    int bestImpactAge = lifespan;



    for (Rocket r : pool) {
      averageDist += r.lastDist;
      if (r.lastDist < bestDist) bestDist = r.lastDist;
      if (r.impactAge < bestImpactAge) bestImpactAge = r.impactAge;
    }
    bestDistances.add(bestDist);
    bestImpactAges.add(bestImpactAge);
    averageDist /= pool.size();
    System.out.println("----------------------------------------------");
    System.out.println("---Evaluation of generation: " + gen + "------");
    System.out.println("----------------------------------------------");
    System.out.println("---Fittest Distance: " + fittest.lastDist);
    System.out.println("---Fittest Impact Age: " + fittest.impactAge);
    System.out.println("----------------------------------------------");
    System.out.println("---Worst Last Distance: " + worst.lastDist);
    System.out.println("---Worst Impact Age: " + worst.impactAge);
    System.out.println("----------------------------------------------");
    System.out.println("---Average distance: " + averageDist);
    System.out.println("---Number of agents that hit the target: " + counter);
    System.out.println("==============================================");
    hits.add(counter);

    if (gen % 10 == 0) {
      System.out.println("Hits: " + hits);
      System.out.println("Best Distances: " + bestDistances);
      System.out.println("Best Ages: " + bestImpactAges);
    }
    gen++;
  }


  public void calcFitnessAndGetExtremes() {
    fittest = pool.get(0);
    worst = pool.get(0);
    for (Rocket r : pool) {
       r.calcFitness(target.getPos());
      if (r.fitness > fittest.fitness) fittest = r;
      if (r.fitness < worst.fitness) worst = r;
    }
    for (Rocket r : pool) {
      r.fitness /= fittest.fitness;
    }
  }


  public void sortByFitness() {
    pool.sort(Comparator.comparing(Rocket::getFitness));
  }

  public void sortByLastDistance() {
    pool.sort(Comparator.comparing(Rocket::getLastDist));
  }


  public void repopulate(List<Rocket> matingPool) {

    for (int i = 0; i < pool.size(); i++) {
      Rocket parentA = matingPool.get(ThreadLocalRandom.current().nextInt(matingPool.size()));
      Rocket parentB = matingPool.get(ThreadLocalRandom.current().nextInt(matingPool.size()));
      Rocket offSpring = parentA.mate(parentB);
      pool.set(i, offSpring);
    }
  }

}
