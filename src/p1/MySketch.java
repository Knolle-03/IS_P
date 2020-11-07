package p1;

import processing.core.PApplet;
import processing.core.PVector;


public class MySketch extends PApplet{


  private Population population;
  private Target target;
  private final PVector targetPos = new PVector(this.width / 2f, 50);
  int POP_SIZE = 10;
  int LIFESPAN = 200;
  float MUTATION_RATE = 0.005f;
  int count = 0;

  Rocket r1;
  Rocket r2;


  public void settings(){
    size(1000, 600);
    target = new Target(this);
    population = new Population(this, LIFESPAN, MUTATION_RATE, POP_SIZE, target);
//    r1 = new Rocket(this, new DNA(LIFESPAN, MUTATION_RATE), new PVector(width / 4f, height - 50), new PVector(), new PVector());
//    r2 = new Rocket(this, new DNA(LIFESPAN, MUTATION_RATE), new PVector(width / 4f * 3, height), new PVector(), new PVector());
//    System.out.println(r1.position.dist(r2.position));


  }

  public void draw(){
    background(0);
    if (count >= LIFESPAN) {
      count = 0;
      population.evaluate();
    }

    for (Rocket rocket :  population.population) {
      rocket.update(targetPos);
      rocket.show();
    }
    count++;
    target.show();

//    r1.show();
//    r2.show();



  }

  public void mousePressed(){
    background(64);
  }

  public static void main(String[] args){
    String[] processingArgs = {"p1.MySketch"};
    MySketch mySketch = new MySketch();
    PApplet.runSketch(processingArgs, mySketch);
  }
}
