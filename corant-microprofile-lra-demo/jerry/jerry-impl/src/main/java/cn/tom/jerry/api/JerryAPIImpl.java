package cn.tom.jerry.api;

import cn.tom.jerry.application.JerryService;
import cn.tom.jerry.domain.Jerry;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Map;

import static org.corant.shared.util.Maps.*;

/**
 * tom-jerry <br>
 *
 * @auther sushuaihao 2019/11/12
 * @since
 */
@Path("/api")
@ApplicationScoped
public class JerryAPIImpl implements JerryAPIClient {

  @Inject JerryService service;

  @Compensate
  @PUT
  @Path("/compensate")
  public void compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraIdUrl) {
    System.out.println("Jerry compensate");
  }

  @PUT
  @Path("/complete")
  @Complete
  public void complete(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraIdUrl) {
    System.out.println("Jerry complete");
  }

  @POST
  @LRA(end = false)
  @Path("/")
  public Map create(Map<?, ?> cmd) {
    Jerry jerry =
        service.create(
            getMapString(cmd, "name"),
            getMapInteger(cmd, "age"),
            getMapBigDecimal(cmd, "speed"),
            getMapString(cmd, "lraId"));
    return mapOf(jerry);
  }

  @Path("/v1/jerry/fail/{id}")
  @PUT
  public void fail(@PathParam("id") Long id) {
    service.fail(id);
  }

  @GET
  @Path("/v1/jerry/lraId/{id}")
  public Long getJerryByLraId(@PathParam("id") String id) {
    return service.getByLraId(id).getId();
  }

  @Path("/v1/jerry/success/{id}")
  @PUT
  public void success(@PathParam("id") Long id) {
    service.success(id);
  }
}
