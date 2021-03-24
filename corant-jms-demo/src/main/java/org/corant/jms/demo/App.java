package org.corant.jms.demo;

import org.corant.Corant;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * corant-microprofile-restclient-demo <br>
 *
 * @auther sushuaihao 2020/9/11
 * @since
 */
@ApplicationScoped
@ApplicationPath("/")
public class App extends Application {

  public static void main(String[] args) {
    Corant.startup();
  }
}
