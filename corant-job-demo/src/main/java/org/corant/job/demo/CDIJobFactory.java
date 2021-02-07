package org.corant.job.demo;

import org.corant.context.proxy.ContextualMethodHandler;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import javax.enterprise.context.ApplicationScoped;

/**
 * config-tck <br>
 *
 * @author sushuaihao 2021/2/5
 * @since
 */
@ApplicationScoped
public class CDIJobFactory implements JobFactory {

  @Override
  public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) {
    JobDetail jobDetail = bundle.getJobDetail();
    return new ContextJobImpl(
        (ContextualMethodHandler) jobDetail.getJobDataMap().get(jobDetail.getKey()));
  }
}
