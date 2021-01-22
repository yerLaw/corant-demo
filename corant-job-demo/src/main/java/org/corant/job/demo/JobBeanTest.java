package org.corant.job.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import javax.enterprise.context.ApplicationScoped;

/**
 * config-tck <br>
 *
 * @author sushuaihao 2021/1/19
 * @since
 */
@ApplicationScoped
public class JobBeanTest implements Job {

  @Override
  @CorantJob(triggerGroup = "testGroup", triggerKey = "triggerKey")
  public void execute(JobExecutionContext context) {
    System.out.println("hello world,");
  }
}
