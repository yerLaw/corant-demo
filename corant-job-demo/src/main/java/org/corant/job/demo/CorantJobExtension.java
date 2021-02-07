package org.corant.job.demo;

import org.corant.context.proxy.ContextualMethodHandler;

import javax.annotation.Priority;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.newSetFromMap;

/**
 * config-tck <br>
 *
 * @author sushuaihao 2021/1/22
 * @since
 */
public class CorantJobExtension implements Extension {

  protected final Set<CorantJobMetaData> metaDatas = newSetFromMap(new ConcurrentHashMap<>());

  public Set<CorantJobMetaData> getJobMetadata() {
    return Collections.unmodifiableSet(metaDatas);
  }

  protected void onBeforeShutdown(@Observes @Priority(0) BeforeShutdown bs) {
    metaDatas.clear();
  }

  protected void onProcessAnnotatedType(
      @Observes @WithAnnotations({CorantJob.class}) ProcessAnnotatedType<?> pat) {
    final Class<?> beanClass = pat.getAnnotatedType().getJavaClass();
    ContextualMethodHandler.fromDeclared(beanClass, m -> m.isAnnotationPresent(CorantJob.class))
        .forEach(cm -> metaDatas.add(CorantJobMetaData.of(cm)));
  }
}
