import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DNA {

  List<PVector> genes = new ArrayList<>();
  float mutationRate;

  public DNA(int length, float mutationRate) {
    for (int i = 0; i < length; i++) {
      genes.add(PVector.random2D().setMag(0.5f));
    }
    this.mutationRate = mutationRate;
  }

  public DNA(List<PVector> genes, float mutationRate) {
    this.genes = genes;
    this.mutationRate = mutationRate;
  }

  public DNA crossover(DNA dna) {
    int cut = ThreadLocalRandom.current().nextInt(dna.genes.size());
    List<PVector> newGenes = new ArrayList<>();
    for (int i = 0; i < genes.size(); i++) {
      if (i < cut) newGenes.add(genes.get(i));
      else newGenes.add(dna.genes.get(i));
    }
    DNA childDNA = new DNA(newGenes, mutationRate);
    childDNA.mutate();
    return childDNA;
  }


  public void mutate() {
    for (PVector vec : genes) {
      float roll = ThreadLocalRandom.current().nextFloat();
      if (roll < mutationRate) vec = PVector.random2D();
    }
  }

}
