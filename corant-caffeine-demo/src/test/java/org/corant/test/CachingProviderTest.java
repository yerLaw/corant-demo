package org.corant.test;

import org.corant.context.SURI;
import org.corant.devops.test.unit.CorantJUnit4ClassRunner;
import org.corant.devops.test.unit.RunConfig;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.cache.CacheManager;
import javax.cache.spi.CachingProvider;
import javax.inject.Inject;

/**
 * corant <br>
 *
 * @auther sushuaihao 2020/9/11
 * @since
 */
@RunWith(CorantJUnit4ClassRunner.class)
@RunConfig(configClass = App.class)
public class CachingProviderTest {
  @Inject CachingProvider cachingProvider;

  @Inject
  @SURI("hello")
  CacheManager cacheManager;

  @Test
  public void cacheManager() {
    cacheManager.getURI();
    cacheManager.getCacheNames();
  }

  @Test
  public void test() {
    CacheManager cacheManager = cachingProvider.getCacheManager();
    cacheManager.getURI();
  }
}
