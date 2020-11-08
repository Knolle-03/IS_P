package p1.shakespeare;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

  public static char getRandomChar() {
    int coin = ThreadLocalRandom.current().nextInt(2);
    int ascii;
    if (coin == 1) {
      ascii = ThreadLocalRandom.current().nextInt(32, 91 );
    } else {
      ascii = ThreadLocalRandom.current().nextInt(97, 123);
    }

    return (char) ascii;
  }

}
