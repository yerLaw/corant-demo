package org.corant.job.demo;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * config-tck <br>
 *
 * @author sushuaihao 2021/1/25
 * @since
 */
@ApplicationScoped
public class CDIJobFactory implements JobFactory {

  @Inject JobExecutionDelegate jobExecutionDelegate;

  @Override
  public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) {
    return jobExecutionDelegate;
  }
}
