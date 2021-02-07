package org.corant.job.demo;

import org.corant.context.proxy.ContextualMethodHandler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.lang.reflect.InvocationTargetException;

/**
 * config-tck <br>
 *
 * @author sushuaihao 2021/1/19
 * @since
 */
public class ContextJobImpl implements Job {

  private ContextualMethodHandler methodHandler;

  public ContextJobImpl(ContextualMethodHandler methodHandler) {
    this.methodHandler = methodHandler;
  }

  @Override
  public void execute(JobExecutionContext context) {
    try {
      this.methodHandler.invoke();
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
