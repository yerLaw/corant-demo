package org.corant.redisson.demo.test;

import org.corant.context.SURI;
import org.corant.devops.test.unit.CorantJUnit4ClassRunner;
import org.corant.devops.test.unit.RunConfig;
import org.corant.jcache.redisson.demo.App;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
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
    Cache<Object, String> cache = cacheManager.createCache("dog", new MutableConfiguration<>());
    cache.put("1", "big dog");
    String s = cache.get("1");
    System.out.println("s = " + s);
  }

  @Test
  public void test() {
    CacheManager cacheManager = cachingProvider.getCacheManager();
    cacheManager.getURI();
  }
}
