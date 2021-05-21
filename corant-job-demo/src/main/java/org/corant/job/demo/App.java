package org.corant.job.demo;

import org.corant.Corant;

import javax.enterprise.context.ApplicationScoped;

/**
 * corant-suites-servlet <br>
 *
 * @author sushuaihao 2021/5/21
 * @since
 */
@ApplicationScoped
public class App {

  public static void main(String[] args) {
    Corant.startup();
  }
}
