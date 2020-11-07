import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Population {

  List<Rocket> population = new ArrayList<>();
  Target target;
  PApplet sketch;
  Rocket fittest;

  public Population(PApplet sketch, int lifespan, float mutationRate, int size, Target target) {
    for (int i = 0; i < size; i++) {
      population.add(new Rocket(sketch, lifespan, mutationRate));
    }
    this.target = target;
    this.sketch = sketch;
  }


  public void evaluate() {
    calcFitnessAndFittest();
    repopulate();
  }


  public void calcFitnessAndFittest() {
    fittest = population.get(0);
    for (Rocket r : population) {
       r.calcFitness(target.getPos());
      if (r.fitness > fittest.fitness) fittest = r;
      System.out.println(r.fitness);
    }
    for (Rocket r : population) {
      r.fitness /= fittest.fitness;
    }
  }

//  private float distToTarget(Rocket rocket) {
//    return 10 / target.getPos().dist(rocket.getPos());
//  }

  public void repopulate() {
    List<Rocket> matingPool = new ArrayList<>();
    for (Rocket rocket : population) {
      int n = (int) Math.floor(rocket.fitness * 100);
      System.out.println("This Rocket is put in the mating pool " + n + " times. Its fitness is: " + rocket.fitness);
      for (int i = 0; i < n; i++) {
        matingPool.add(rocket);
      }
    }
    System.out.println("Size: " + matingPool.size() + "Selection: " + matingPool);


    for (int i = 0; i < population.size(); i++) {
      Rocket parentA = matingPool.get(ThreadLocalRandom.current().nextInt(matingPool.size()));
      Rocket parentB = matingPool.get(ThreadLocalRandom.current().nextInt(matingPool.size()));
      population.set(i, parentA.mate(parentB));
    }
  }








}
