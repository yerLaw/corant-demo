package org.corant.jcache.redisson.demo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.corant.shared.util.Maps.getMapString;

/**
 * corant <br>
 *
 * @auther sushuaihao 2020/8/27
 * @since
 */
@Path("/cache")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JCacheEndpoint {

  @Inject JCacheApplicationService service;

  @Path("/put")
  @POST
  public Response cachePut(Map<?, ?> cmd) {
    String value = service.cachePut(getMapString(cmd, "value"), getMapString(cmd, "key"));
    return Response.ok(value).build();
  }

  @Path("/get")
  @POST
  public Response cacheResult(Map<?, ?> cmd) {
    String value = service.cacheResult(getMapString(cmd, "key"));
    return Response.ok(value).build();
  }

  @Path("/hello")
  @POST
  public Response helloWorld() {
    return Response.ok("hello world").build();
  }
}
