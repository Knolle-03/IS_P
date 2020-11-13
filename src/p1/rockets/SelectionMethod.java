package p1.rockets;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SelectionMethod {
  DETERMINISTIC_TRUNCATION_SELECTION {
    @Override
    public List<Rocket> selection(Population population, int matingPoolSize) {
      population.sortByFitness();
      List<Rocket> survivors = new ArrayList<>();
      for (int i = 0; i < matingPoolSize / 2; i++) {
        survivors.add(population.pool.get(i));
      }

      return survivors;
    }
  }, TOURNAMENT_SELECTION_WITH_REDRAW {
    @Override
    public List<Rocket> selection(Population population, int matingPoolSize) {
      List<Rocket> survivors = new ArrayList<>();
      for (int i = 0; i < matingPoolSize; i++) {
        Rocket r1 = population.pool.get(ThreadLocalRandom.current().nextInt(population.pool.size()));
        Rocket r2 = population.pool.get(ThreadLocalRandom.current().nextInt(population.pool.size()));

        if (r1.fitness >= r2.fitness) survivors.add(r1);
        else survivors.add(r2);
      }

      return survivors;
    }
  }, TOURNAMENT_SELECTION_WITHOUT_REDRAW {
    @Override
    public  List<Rocket> selection(Population population, int matingPoolSize) {
      List<Rocket> survivors = new ArrayList<>();
      Rocket r1;
      Rocket r2;


      for (int i = 0; i < matingPoolSize; i++) {
        do {
          r1 = population.pool.get(ThreadLocalRandom.current().nextInt(population.pool.size()));
        } while (survivors.contains(r1));
        do {
          r2 = population.pool.get(ThreadLocalRandom.current().nextInt(population.pool.size()));
        } while (survivors.contains(r2));

        if (r1.fitness >= r2.fitness) survivors.add(r1);
        else survivors.add(r2);
      }

      return survivors;
    }
  },

  ROULETTE_WHEEL_SELECTION {
    @Override
    public List<Rocket> selection(Population population, int matingPoolSize) {
      List<Rocket> survivors = new ArrayList<>();
      for (Rocket rocket : population.pool) {
        int n = (int) Math.floor(rocket.fitness * 100);
        for (int i = 0; i < n; i++) {
          survivors.add(rocket);
        }
      }
      return survivors;
    }
  },

  RANKED_BASED_ROULETTE_WHEEL_SELECTION {
    @Override
    public List<Rocket> selection(Population population, int matingPoolSize) {
      List<Rocket> survivors = new ArrayList<>();
      population.sortByFitness();
      for (int j = 0; j < population.pool.size(); j++) {
        Rocket rocket = population.pool.get(j);
        int n = 2 * (population.pool.size() + 1 - j) / (population.pool.size() * population.pool.size() - population.pool.size());
        for (int i = 0; i < n; i++) {
          survivors.add(rocket);
        }
      }
      return survivors;
    }
  },

  ELITIST_SELECTION {
    // TODO:: rewrite so that the elite bypasses the recombination and mutation phase!
    @Override
    public List<Rocket> selection(Population population,int matingPoolSize, int k_Best, SelectionMethod selectionMethod ) {
      List<Rocket> survivors = new ArrayList<>();
      population.sortByFitness();
      for (int i = 0; i < k_Best; i++) {
        survivors.add(population.pool.get(i));
      }

      List<Rocket> rest = selectionMethod.selection(population, matingPoolSize - k_Best);

      return Stream.concat(survivors.stream(), rest.stream()).collect(Collectors.toList());
    }
  };


  public List<Rocket> selection(Population population, int matingPoolSize) {
    return null;
  }
  public List<Rocket> selection(Population population,int matingPoolSize,  int k_Best, SelectionMethod selectionMethod) {
    return null;
  }
}
