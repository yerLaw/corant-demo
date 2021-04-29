package org.corant.jms.demo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;

/**
 * corant <br>
 *
 * @auther sushuaihao 2020/11/16
 * @since
 */
@Path("/app")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldEndpoint {

//  final ExecutorService executorService = Executors.newSingleThreadExecutor();
  @Inject JMSContext jmsContext;
  @Inject ExecutorService executorService;

  @Path("/greeting")
  @GET
  public Response hello(@QueryParam(value = "kw") String kw) {
    executorService.submit(
        () -> {
          jmsContext.createProducer().send(jmsContext.createQueue("test"), kw);
        });
    return Response.ok("Hello World!").build();
  }
}
