package org.corant.experiment;

/**
 * corant <br/>
 *
 * @author sushuaihao 2021/7/22
 * @since
 */
public class App {
  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      System.out.println("i = " + i);
      for (int j = 0; j < 10; j++) {
        if (j == 7) {
          break;
        }

        System.out.println("j = " + j);
      }

    }
  }
}
