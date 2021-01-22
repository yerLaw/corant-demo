package org.corant.job.demo;

import org.corant.kernel.event.PostCorantReadyEvent;
import org.corant.shared.util.Sets;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Set;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * config-tck <br>
 *
 * @author sushuaihao 2021/1/22
 * @since
 */
@ApplicationScoped
public class CorantJobManager {

  protected final Set<CorantJobMetaData> jobMetaDatas = Sets.newConcurrentHashSet();
  @Inject protected CorantJobExtension extension;

  protected void onPostCorantReadyEvent(@Observes PostCorantReadyEvent adv)
      throws SchedulerException {
    for (final CorantJobMetaData metaData : jobMetaDatas) {
      SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
      Scheduler sched = schedFact.getScheduler();
      sched.start();
      JobDetail job = newJob((Class<? extends Job>) metaData.getMethod().getClazz()).build();
      Trigger trigger =
          newTrigger()
              .withIdentity(metaData.getTriggerKey(), metaData.getTriggerGroup())
              .startNow()
              .withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever())
              .build();
      sched.scheduleJob(job, trigger);
    }
  }

  @PostConstruct
  protected void postConstruct() {
    extension.getJobMethods().stream().map(CorantJobMetaData::of).forEach(jobMetaDatas::add);
  }
}
