package org.corant.job.demo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * config-tck <br>
 *
 * @author sushuaihao 2021/2/5
 * @since
 */
@ApplicationScoped
public class JobService {
  @Inject Logger logger;

  @CorantJob(triggerGroup = "job_1", triggerKey = "job_key_1")
  public void job_1() {
    logger.info("job_1====job_1====job_1====job_1");
  }

  @CorantJob(triggerGroup = "job_2", triggerKey = "job_key_2")
  public void job_2() {
    logger.info("job_2====job_2====job_2====job_2");
  }
}
