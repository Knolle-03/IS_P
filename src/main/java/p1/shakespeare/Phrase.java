package p1.shakespeare;

import java.util.concurrent.ThreadLocalRandom;

public class Phrase {

  private double fitness;
  private char[] chars;




  public Phrase(int length) {

    chars = new char[length];

    for (int i = 0; i < length; i++) {
      chars[i] = Utils.getRandomChar();
    }
  }

  public Phrase(char[] phrase) {
    this.chars = phrase;

  }

  public Phrase crossover(Phrase partner) {
    char [] childPhrase = new char[chars.length];
    int cut = ThreadLocalRandom.current().nextInt(chars.length);
    for (int i = 0; i < chars.length; i++) {
      if (i < cut) {
        childPhrase[i] = chars[i];
      } else {
        childPhrase[i] = partner.chars[i];
      }

    }
    return new Phrase(childPhrase);
  }

  public void mutate(float rate) {
    float chance = rate;

    for (int i = 0; i < chars.length; i++) {
      float roll = ThreadLocalRandom.current().nextFloat();
      if (roll < chance) {
        chars[i] = Utils.getRandomChar();
        chance /= 2;
      }
    }


  }


  public double getFitness() {
    return fitness;
  }

  public char[] getChars() {
    return chars;
  }


  public void setFitness(double fitness) {
    this.fitness = fitness;
  }

  public void setChars(char[] chars) {
    this.chars = chars;
  }

  @Override
  public String toString() {
    return String.format("Fitness: %5f | String: %s", fitness, new String(chars));
  }
}
