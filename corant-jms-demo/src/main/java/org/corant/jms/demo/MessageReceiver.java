package org.corant.jms.demo;

import org.corant.modules.jms.shared.annotation.MessageReceive;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.Message;
import java.util.logging.Logger;

/**
 * corant-suites-concurrency <br>
 *
 * @author sushuaihao 2021/3/25
 * @since
 */
@ApplicationScoped
public class MessageReceiver {

  @Inject Logger logger;

  @MessageReceive(destinations = "test")
  void onMessage(Message msg) {
    logger.info("receive msg from jms");
  }
}
