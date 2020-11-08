package p1.rockets;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;


public class MySketch extends PApplet{


  Population population;
  List<Obstacle> obstacles = new ArrayList<>();
  Target target;
  SelectionMethod SELECTION_METHOD = SelectionMethod.ROULETTE_WHEEL_SELECTION;
  int POP_SIZE = 1000;
  int LIFESPAN = 300;
  float MUTATION_RATE = 0.01f;
  int count = 0;

  public void settings(){
    size(1000, 600);
    target = new Target(this);
    population = new Population(this, LIFESPAN, MUTATION_RATE, POP_SIZE, target, SELECTION_METHOD);
    obstacles.add(new Obstacle(this,width / 4f, 300, width / 2f, 25));
    obstacles.add(new Obstacle(this,10, 150, width / 3f, 25));
    obstacles.add(new Obstacle(this,width / 3f * 2, 150, width / 2f, 25));
    obstacles.add(new Obstacle(this,0, 0, 10, height));
    obstacles.add(new Obstacle(this,0, 0, width, 10));
    obstacles.add(new Obstacle(this, 0, height - 10, width, 10));
    obstacles.add(new Obstacle(this,width - 10, 10, 10, height - 10));
  }

  public void draw(){
    background(0);
    if (count >= LIFESPAN) {
      count = 0;
      population.evaluate();
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

  public void mousePressed(){
    background(64);
  }

  public static void main(String[] args){
    String[] processingArgs = {"p1.rockets.MySketch"};
    MySketch mySketch = new MySketch();
    PApplet.runSketch(processingArgs, mySketch);
  }
}
