package p1.rockets;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;


public class SmartRocketSketch extends PApplet{


  Population population;
  List<Obstacle> obstacles = new ArrayList<>();
  Target target;
  SelectionMethod SELECTION_METHOD = SelectionMethod.ROULETTE_WHEEL_SELECTION;
  int POP_SIZE = 500;
  int LIFESPAN = 100;
  float MUTATION_RATE = 0.01f;
  PVector START_POS;
  int count = 0;


  public void settings(){
    size(1000, 600);

  }

  public void setup() {
    START_POS = new PVector(width / 2f, height - height / 4f);
    target = new Target(this);
    population = new Population(this, LIFESPAN, MUTATION_RATE, POP_SIZE, target, SELECTION_METHOD, START_POS);


    //middle obstacle
    obstacles.add(new Obstacle(this,width / 4f, 300, width / 2f, 25));


    //additional obstacles
    obstacles.add(new Obstacle(this,10, 150, width / 3f, 25));
    obstacles.add(new Obstacle(this,width / 3f * 2, 150, width / 2f, 25));

    // outer walls
    obstacles.add(new Obstacle(this,10, 10, -100, height));
    obstacles.add(new Obstacle(this,0, 10, width, -100));
    obstacles.add(new Obstacle(this, 0, height - 10, width, 100));
    obstacles.add(new Obstacle(this,width - 10, 10, 100, height - 10));
  }

  public void draw(){
    background(0);
    if (count >= LIFESPAN) {
      count = 0;
      population.evaluate();

      population.repopulate(SELECTION_METHOD.selection(population, population.pool.size()));
    }
    for (Rocket rocket :  population.pool) {
      rocket.update(target.getPos(), obstacles);
      rocket.show();
    }
    for (Obstacle obs : obstacles) {
      obs.show();
    }
    count++;

    target.show();
  }


  public static void main(String[] args){
    String[] processingArgs = {"p1.rockets.MySketch"};
    SmartRocketSketch smartRocketSketch = new SmartRocketSketch();
    PApplet.runSketch(processingArgs, smartRocketSketch);
  }
}
