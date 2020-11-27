package org.corant.wechat.pay;

import org.corant.Corant;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * corant <br>
 *
 * @auther sushuaihao 2020/10/28
 * @since
 */
@ApplicationScoped
@ApplicationPath("/")
public class App extends Application {

  public static void main(String[] args) {
    Corant.startup();
  }
}
