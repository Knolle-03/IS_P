package p1.rockets;

import processing.core.*;

import java.util.List;


public class Rocket {

  PApplet sketch;
  DNA dna;
  PVector position;
  PVector velocity;
  PVector acceleration;
  private int age = 0;
  double fitness = 0;
  boolean targetHit = false;
  boolean crashed;
  int impactAge;
  float lastDist;
  boolean alreadySet = false;


  public Rocket(PApplet sketch,int lifespan, float mutationRate) {
    this(sketch);
    this.dna = new DNA(lifespan, mutationRate);

  }

  public Rocket(PApplet sketch, DNA dna, PVector position, PVector velocity, PVector acceleration) {
    this.sketch = sketch;
    this.dna = dna;
    this.position = position;
    this.velocity = velocity;
    this.acceleration = acceleration;
  }

  public Rocket(PApplet sketch) {
    this.sketch = sketch;
    position = new PVector(sketch.width / 2f, sketch.height - 150);
    velocity = new PVector();
    acceleration = new PVector();
  }

  public Rocket(PApplet sketch, DNA dna) {
    this(sketch);
    this.dna = dna;
  }

  private void applyForce(PVector vector) {
    acceleration.add(vector);
    age++;
  }

  public void update(PVector targetPos, List<Obstacle> obstacles) {
    if(targetHit(targetPos)){
      targetHit = true;
      if(!alreadySet) {
        impactAge = age;
        alreadySet = true;
      }
    }
    if(obstacleHit(obstacles)) crashed = true;



    applyForce(dna.genes.get(age));
    if (!targetHit && !crashed) {
      velocity.add(acceleration);
      position.add(velocity);
      acceleration.mult(0);
    }
  }

  public boolean targetHit(PVector targetPos) {
    lastDist = PVector.dist(this.position, targetPos);
    return  lastDist < 25;
  }

  public boolean obstacleHit(List<Obstacle> obstacles) {
    for (Obstacle obs : obstacles) {
      if ((position.x > obs.x && position.x < obs.x + obs.width) &&
          (position.y > obs.y && position.y < obs.y + obs.height)) {
        return true;
      }
    }
    return false;
  }

  public void calcFitness(PVector targetPos) {
    fitness =  1/this.position.dist(targetPos);
    if (targetHit){
      fitness *= (100d * dna.genes.size()) / impactAge;
    } else if (!crashed) {
      fitness *= 2;
    } else fitness /= 10d;
  }

  public void show() {
    sketch.push();
    sketch.translate(position.x, position.y);
    sketch.rotate(velocity.heading());
//    sketch.rectMode(PConstants.CENTER);
//    sketch.rect(0,0,60,10);
    sketch.triangle(25,5,15,0,15,10);
    sketch.pop();
  }





  public Rocket mate(Rocket rocket) {
    return  new Rocket(sketch, this.dna.crossover(rocket.dna));
  }

  @Override
  public String toString() {
    return "Rocket{" +
            "fitness=" + fitness +
            '}';
  }


  public double getFitness() {
    return fitness;
  }

  public float getLastDist() {
    return lastDist;
  }
}
