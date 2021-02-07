package org.corant.job.demo;

import org.corant.kernel.event.PostCorantReadyEvent;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.Instant;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * config-tck <br>
 *
 * @author sushuaihao 2021/1/22
 * @since
 */
@ApplicationScoped
public class CorantJobManager {

  @Inject protected CorantJobExtension extension;
  @Inject CDIJobFactory cdiJobFactory;

  protected void onPostCorantReadyEvent(@Observes PostCorantReadyEvent adv)
      throws SchedulerException {
    SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
    Scheduler scheduler = schedulerFactory.getScheduler();
    scheduler.setJobFactory(cdiJobFactory);
    scheduler.start();
    for (final CorantJobMetaData metaData : extension.getJobMetadata()) {
      JobDetail job = newJob(ContextJobImpl.class).build();
      job.getJobDataMap().put(String.valueOf(job.getKey()), metaData.getMethod());
      Trigger trigger =
          newTrigger()
              .withIdentity(metaData.getTriggerKey(), metaData.getTriggerGroup())
              .startAt(Date.from(Instant.now().plusSeconds(120)))
              .build();
      scheduler.scheduleJob(job, trigger);
    }
  }
}
