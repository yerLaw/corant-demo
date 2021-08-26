package org.corant.jms.demo;

import org.corant.modules.jms.annotation.MessageDestination;
import org.corant.modules.jms.annotation.MessageDriven;

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

  @MessageDriven
  @MessageDestination(name = "test")
  void onMessage(Message msg) {
    logger.info("receive msg from jms");
  }
}
