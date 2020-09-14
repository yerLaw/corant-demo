package org.corant.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;
import javax.enterprise.context.ApplicationScoped;

/**
 * corant <br>
 *
 * @auther sushuaihao 2020/8/26
 * @since
 */
@ApplicationScoped
public class JCacheApplicationService {

  static Logger logger = LoggerFactory.getLogger(JCacheApplicationService.class);

  @CachePut(cacheName = "myCache")
  public String cachePut(@CacheValue String value, @CacheKey String key) {
    return value;
  }

  @CacheResult(cacheName = "myCache")
  public String cacheResult(@CacheKey String key) {
    /*
      if parameter key got no cache value , then invoke this method.
      And the value cached by this parameter key.
    */
    logger.info("not found value in cache , now invoke method and cached returned value");
    return null;
  }
}
