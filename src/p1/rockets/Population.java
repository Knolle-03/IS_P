package p1.rockets;

import processing.core.PApplet;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Population {

  List<Rocket> pool = new ArrayList<>();
  Target target;
  PApplet sketch;
  Rocket fittest;
  SelectionMethod selectionMethod;
  int gen = 0;

  public Population(PApplet sketch, int lifespan, float mutationRate, int size, Target target, SelectionMethod selectionMethod) {
    for (int i = 0; i < size; i++) {
      pool.add(new Rocket(sketch, lifespan, mutationRate));
    }
    this.target = target;
    this.sketch = sketch;
    this.selectionMethod = selectionMethod;
  }


  public void evaluate() {
    calcFitnessAndFittest();
    int counter = 0;
    for (int i = 0; i < pool.size(); i++) {
      if (pool.get(i).targetHit) counter++;

    }

    System.out.println("---Evaluation of generation: " + gen + "---");
    System.out.println("---Last Distance: " + fittest.lastDist);
    System.out.println("---Impact Age: " + fittest.impactAge);
    System.out.println("---Number of agents that hit the target: " + counter);
    gen++;
    repopulate(selectionMethod.selection(this, pool.size()));
  }


  public void calcFitnessAndFittest() {
    fittest = pool.get(0);
    for (Rocket r : pool) {
       r.calcFitness(target.getPos());
      if (r.fitness > fittest.fitness) fittest = r;
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
      Rocket parentB;
      //do {
        parentB = matingPool.get(ThreadLocalRandom.current().nextInt(matingPool.size()));
      //} while (parentA.equals(parentB));
      Rocket offSpring = parentA.mate(parentB);
      pool.set(i, offSpring);
    }
  }








}
