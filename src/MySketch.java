import processing.core.PApplet;
import processing.core.PVector;


public class MySketch extends PApplet{

  private Population population;
  private Target target;
  private final PVector targetPos = new PVector(this.width / 2f, 50);
  int POP_SIZE = 200;
  int LIFESPAN = 200;
  float MUTATION_RATE = 0.1f;
  int count = 0;

  public void settings(){
    size(2000, 1000);
    target = new Target(this, targetPos);
    population = new Population(this,LIFESPAN, MUTATION_RATE, POP_SIZE, target);

  }

  public void draw(){
    background(0);
    if (count == LIFESPAN) {
      count = 0;
      population.evaluate();


    }
    for (Rocket rocket :  population.population) {
      rocket.update();
      rocket.show();
      rocket.applyForce();
    }
    count++;
    target.show();

  }

  public void mousePressed(){
    background(64);
  }

  public static void main(String[] args){
    String[] processingArgs = {"MySketch"};
    MySketch mySketch = new MySketch();
    PApplet.runSketch(processingArgs, mySketch);
  }
}
