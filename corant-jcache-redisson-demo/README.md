# Corant JCache Redisson Demo
# Requirements
1. java 8 (or newer)
2. maven 2.2.1 (or newer)
3. redis (this demo:5.0.5)
# Tutorial
## Add JCache Redisson dependency
```
<dependency>
    <groupId>org.corant</groupId>
    <artifactId>corant-suites-jcache-redisson</artifactId>
    <version>1.2.5-SNAPSHOT</version>
</dependency>
```
more details see the `pom.xml`
## Usage
It is similar to [corant-jcache-caffeine-demo](https://github.com/sushuaihao/corant-demo/tree/master/corant-jcache-caffeine-demo#tutorial)

But There are two differences:
1. Configuration of jcache redisson.The required configuration of redis's address.
```
redis.address=host.vm.internal:6379
redis.database=0
```
2. The difference to [Cache programmatic API](https://github.com/sushuaihao/corant-demo/tree/master/corant-jcache-caffeine-demo#cache-programmatic-api).
You need create and config cache by your code.
```java
Cache<Object, String> cache = cacheManager.createCache("demo", new MutableConfiguration<>());
```

