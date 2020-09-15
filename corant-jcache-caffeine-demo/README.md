# Corant JCache Caffeine Demo

# Tutorial

##JCache annotations usage
```java
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
```
First, you should config your cache in conf file.
##Caffeine configurations in corant
You can config cache directly or its configuration's file path in `META-INF/application.properties` 
* cache configuration's file path in `META-INF/application.properties`:
```
caffeine.config.resource=META-INF/caffeine.properties
```
Then you can in `META-INF/caffeine.properties` config your cache.Format `.conf/.json/.properties` are supported.
* cache config in `META-INF/application.properties`
```
caffeine.jcache.myCache.policy.maximum.size=123
```
See the [reference.conf](https://github.com/ben-manes/caffeine/blob/master/jcache/src/main/resources/reference.conf) for more details.
##JCache programmatic API
You can `@Inject` CacheManager or CachingProvider.
```java
public class CachingProviderTest {
  @Inject CachingProvider cachingProvider;

  @Inject
  @SURI("hello")
  CacheManager cacheManager;
}
```
Required `@SURI`, its value defined for CacheManager's uri.
Then you can use CacheManager create/get cache in your code.
```java
Cache<Object, Object> cache = cacheManager.getCache("apple");
```
Note the above example `apple` named cache required config in your conf file.

eg.
```
caffeine.jcache.apple.policy.maximum.size=345
```
Caffeine will create `apple` named cache when you used `CacheManager.getCache`.



