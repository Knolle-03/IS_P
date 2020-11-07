import processing.core.*;



public class Rocket {

  private final PApplet sketch;
  private  DNA dna;
  private final PVector position;
  private final PVector velocity;
  private final PVector acceleration;
  private int age = 0;
  double fitness = 0;

  public Rocket(PApplet sketch,int lifespan, float mutationRate) {
    this(sketch);
    this.dna = new DNA(lifespan, mutationRate);

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

  public void applyForce() {
    velocity.add(dna.genes.get(age));
    age++;
  }

  public void update() {
    velocity.add(acceleration);
    position.add(velocity);
    acceleration.mult(0);

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
    float xDist = this.position.x - targetPos.x;
    float yDist = this.position.y - targetPos.y;

    fitness =  10 / Math.sqrt(xDist * xDist + yDist * yDist);
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
