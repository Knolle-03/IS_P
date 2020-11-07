package p1;

import processing.core.*;



public class Rocket {

  PApplet sketch;
  DNA dna;
  PVector position;
  PVector velocity;
  PVector acceleration;
  private int age = 0;
  double fitness = 0;
  boolean targetHit = false;

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
    position = new PVector(sketch.width / 2f, sketch.height - 300);
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

  public void update(PVector targetPos) {
    float distance = PVector.dist(this.position, targetPos);
    if (distance < 400) {
      targetHit = true;
      position = targetPos;
    }
    applyForce(dna.genes.get(age));
    if (!targetHit) {
      velocity.add(acceleration);
      position.add(velocity);
      acceleration.mult(0);
    }
  }

  public void show() {
    sketch.push();
    sketch.translate(position.x, position.y);
    sketch.rotate(velocity.heading());
    sketch.rectMode(PConstants.CENTER);
    sketch.rect(0,0,60,10);
//    sketch.triangle((sketch.width / 2) - 10,sketch.height + 20,
//                       (sketch.width / 2)     ,sketch.height + 50,
//                    (sketch.width / 2) + 10,sketch.height + 20);
    sketch.pop();
  }


  public void calcFitness(PVector targetPos) {
    fitness =  1/this.position.dist(targetPos);
  }


  public Rocket mate(Rocket rocket) {
    return  new Rocket(sketch, rocket.dna.crossover(rocket.dna));
  }

  @Override
  public String toString() {
    return "Rocket{" +
            "fitness=" + fitness +
            '}';
  }
}
