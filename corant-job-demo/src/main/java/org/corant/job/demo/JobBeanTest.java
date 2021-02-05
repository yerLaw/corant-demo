package org.corant.job.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * config-tck <br>
 *
 * @author sushuaihao 2021/1/19
 * @since
 */
@ApplicationScoped
public class JobBeanTest implements Job {

  @Inject Logger logger;

  @Override
  @CorantJob(triggerGroup = "testGroup", triggerKey = "triggerKey")
  public void execute(JobExecutionContext context) {
    Long orderId = (Long) context.getJobDetail().getJobDataMap().get("orderId");
    System.out.println("hello world sssss%s," + orderId);
    logger.warning(() -> String.format("orderId is [%s]", orderId.toString()));
  }
}
