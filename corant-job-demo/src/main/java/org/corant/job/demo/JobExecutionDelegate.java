package org.corant.job.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * config-tck <br>
 *
 * @author sushuaihao 2021/1/25
 * @since
 */
@ApplicationScoped
public class JobExecutionDelegate implements Job {

  @Inject private Instance<Job> jobInstance;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    Instance<? extends Job> job = jobInstance.select(context.getJobDetail().getJobClass());
    if (job.isAmbiguous())
      throw new IllegalStateException("Cannot produce job: ambiguous instance");
    if (job.isUnsatisfied())
      throw new IllegalStateException("Cannot produce job: unsatisfied instance");
    job.get().execute(context);
  }
}
