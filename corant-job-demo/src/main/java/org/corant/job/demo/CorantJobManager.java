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
import java.time.Instant;
import java.util.Date;
import java.util.Set;

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

  protected final Set<CorantJobMetaData> jobMetaDatas = Sets.newConcurrentHashSet();
  @Inject protected CorantJobExtension extension;
  @Inject CDIJobFactory cdiJobFactory;
  private Scheduler sched;

  protected void onPostCorantReadyEvent(@Observes PostCorantReadyEvent adv)
      throws SchedulerException {
    for (final CorantJobMetaData metaData : jobMetaDatas) {
      SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
      sched = schedFact.getScheduler();
      sched.setJobFactory(cdiJobFactory);
      sched.start();
      JobDetail job =
          newJob((Class<? extends Job>) metaData.getMethod().getClazz())
              .usingJobData("orderId", 123L)
              .build();
      Trigger trigger =
          newTrigger()
              .withIdentity(metaData.getTriggerKey(), metaData.getTriggerGroup())
              .startAt(Date.from(Instant.now().plusSeconds(120)))
              .build();
      sched.scheduleJob(job, trigger);
    }
  }

  @PostConstruct
  protected void postConstruct() {
    extension.getJobMethods().stream().map(CorantJobMetaData::of).forEach(jobMetaDatas::add);
  }
}
