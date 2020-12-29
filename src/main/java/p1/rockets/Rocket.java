package p1.rockets;

import processing.core.*;

import java.util.List;


public class Rocket {

  PApplet sketch;
  DNA dna;
  PVector position;
  PVector startPos;
  PVector velocity;
  PVector acceleration;
  private int age = 0;
  double fitness = 0;
  boolean targetHit = false;
  boolean crashed;
  int impactAge = Integer.MAX_VALUE;
  float lastDist;
  boolean alreadySet = false;


  public Rocket(PApplet sketch,int lifespan, float mutationRate, PVector startPos) {
    this(sketch);
    this.startPos = startPos;
    this.dna = new DNA(lifespan, mutationRate);
  }

  public Rocket(PApplet sketch) {
    this.sketch = sketch;
    this.position = new PVector( sketch.width /2f, sketch.height - sketch.height / 5f);
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
    if(rocketHit(targetPos)){
      targetHit = true;
      if(!alreadySet) {
        impactAge = age;
        alreadySet = true;
      }
    }
    if(rocketHit(obstacles)) crashed = true;
    applyForce(dna.genes.get(age));
    if (!targetHit && !crashed) {
      velocity.add(acceleration);
      position.add(velocity);
      //acceleration.mult(0);
      velocity.limit(10);
    }
  }

  public boolean rocketHit(PVector targetPos) {
    lastDist = position.dist(targetPos);
    return  lastDist < 25;
  }

  public boolean rocketHit(List<Obstacle> obstacles) {
    for (Obstacle obs : obstacles) {
      if ((position.x > obs.x && position.x < obs.x + obs.width) &&
          (position.y > obs.y && position.y < obs.y + obs.height)) {
        return true;
      }
    }
    return false;
  }

  public void calcFitness(PVector targetPos) {
    fitness =  1 / position.dist(targetPos);
    if (targetHit){
      fitness *= 10d * dna.genes.size() / impactAge;
    } else if (crashed) {
      fitness /= 10;
    }
  }



  public void show() {
    sketch.push();
    sketch.translate(position.x, position.y);
    sketch.rotate(velocity.heading());
//    sketch.rectMode(PConstants.CENTER);
//    sketch.rect(0,0,60,10);
    sketch.triangle(50,5,15,0,15,10);
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
