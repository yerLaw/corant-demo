package cn.tom.jerry;

import org.corant.Corant;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * tom-jerry <br>
 *
 * @auther sushuaihao 2019/11/8
 * @since
 */
@ApplicationPath("/jerry")
public class App extends Application {
  public App() {}

  public static void main(String[] args) {
    Corant.startup(App.class);
  }
}
