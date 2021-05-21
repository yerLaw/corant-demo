package org.corant.job.demo;

import org.corant.modules.quartz.embeddable.CorantTrigger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * corant-suites-servlet <br>
 *
 * @author sushuaihao 2021/5/21
 * @since
 */
@ApplicationScoped
public class JobService {

  @Inject Logger logger;

  @CorantTrigger(cron = "* * * * * ? *")
  public void test() {
    logger.fine("job1=====job1");
  }
}
