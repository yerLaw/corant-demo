package org.corant.job.demo;

import org.corant.context.proxy.ContextualMethodHandler;

/**
 * config-tck <br>
 *
 * @author sushuaihao 2021/1/22
 * @since
 */
public class CorantJobMetaData {
  private final ContextualMethodHandler method;
  private final String triggerKey;
  private final String triggerGroup;

  public CorantJobMetaData(ContextualMethodHandler method) {
    this.method = method;
    final CorantJob ann = method.getMethod().getAnnotation(CorantJob.class);
    this.triggerKey = ann.triggerKey();
    this.triggerGroup = ann.triggerGroup();
  }

  public static CorantJobMetaData of(ContextualMethodHandler method) {
    return new CorantJobMetaData(method);
  }

  public ContextualMethodHandler getMethod() {
    return method;
  }

  public String getTriggerGroup() {
    return triggerGroup;
  }

  public String getTriggerKey() {
    return triggerKey;
  }
}
