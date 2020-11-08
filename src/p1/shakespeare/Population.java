package p1.shakespeare;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Population {

  List<Phrase> population;


  List<Phrase> matingPool;

  private char[] target;
  private int PHRASE_LENGTH;
  private float mutationRate;
  boolean gotTarget;
  private Phrase fittest;

  public Population(String target, float mutationRate, int maxPopSize) {
    if (target == null) throw new IllegalArgumentException("target length must be greater than 0.");
    PHRASE_LENGTH = target.length();
    this.target = target.toCharArray();
    this.mutationRate = mutationRate;
    population = new ArrayList<>();

    for (int i = 0; i < maxPopSize; i++) {
      population.add(new Phrase(target.length()));
    }
    fittest = population.get(0);
  }


  public void calcFitness() {
    fittest = population.get(0);
    for (Phrase phrase : population) {
      int fitness = 0;
      char[] currentChars = phrase.getChars();
      for (int j = 0; j < PHRASE_LENGTH; j++) {
        if (currentChars[j] == target[j]) {
          fitness++;
        }
      }
      phrase.setFitness(fitness / (double) PHRASE_LENGTH);
      if (phrase.getFitness() > fittest.getFitness()) fittest = phrase;
      if (fitness == target.length) {
        gotTarget = true;
      }
    }
  }


  // TODO:: rewrite!
  public void naturalSelection() {
    matingPool = new ArrayList<>();

    double maxFitness = 0;

    for (Phrase p : population) {
      if (p.getFitness() > maxFitness) maxFitness = p.getFitness();
    }
    for (Phrase phrase : population) {
      int n = (int) Math.floor(phrase.getFitness() * 100 / maxFitness);
      //System.out.println(n);
      for (int i = 0; i < n; i++) {
        matingPool.add(phrase);
      }
    }
  }

  public void generateNewPopulation() {
    for (int i = 0; i < population.size(); i++) {
      Phrase phraseA = matingPool.get(ThreadLocalRandom.current().nextInt(matingPool.size()));
      Phrase phraseB = matingPool.get(ThreadLocalRandom.current().nextInt(matingPool.size()));

      population.set(i, phraseA.crossover(phraseB));
      population.get(i).mutate(mutationRate);
    }
  }


  public Phrase getFittest() {
    return fittest;
  }

  public boolean evaluate() {
    return gotTarget;
  }
}
