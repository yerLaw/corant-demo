package cn.tom.tom.gateway;

import org.asosat.ddd.gateway.AbstractRests;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * tom-jerry <br>
 *
 * @auther sushuaihao 2019/11/11
 * @since
 */
@Path("/tom")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TomEndpoint extends AbstractRests {

  @Path("/generate/")
  @POST
  public Response generate() {
    return Response.ok().build();
  }

  @Path("/run/")
  @POST
  public Response run() {
    return Response.ok().build();
  }
}
