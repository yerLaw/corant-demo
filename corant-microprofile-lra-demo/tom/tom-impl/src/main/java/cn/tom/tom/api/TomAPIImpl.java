package cn.tom.tom.api;

import cn.tom.tom.api.client.TomAPIClient;
import cn.tom.tom.application.TomService;
import cn.tom.tom.domain.Tom;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Map;

import static org.corant.shared.util.Maps.*;

/**
 * tom-tom <br>
 *
 * @auther sushuaihao 2019/11/12
 * @since
 */
@Path("/api")
@ApplicationScoped
public class TomAPIImpl implements TomAPIClient {

  @Inject TomService service;

  @PUT
  @Path("/compensate")
  @Compensate
  public void compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraIdUrl) {
    System.out.println("Tom compensate");
  }

  @PUT
  @Path("/complete")
  @Complete
  public void complete(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraIdUrl) {
    System.out.println("Tom complete");
  }

  @POST
  @LRA(end = false)
  @Path("/")
  public Map create(Map<?, ?> cmd) {
    Tom tom =
        service.create(
            getMapString(cmd, "name"),
            getMapInteger(cmd, "age"),
            getMapBigDecimal(cmd, "speed"),
            getMapString(cmd, "lraId"));
    return mapOf(tom);
  }

  @Path("/v1/tom/fail/{id}")
  @PUT
  public void fail(@PathParam("id") Long id) {
    service.fail(id);
  }

  @Override
  @GET
  @Path("/v1/tom/lraId/{id}")
  public Long getTomByLraId(@PathParam("id") String id) {
    return service.getByLraId(id).getId();
  }

  @Path("/v1/tom/success/{id}")
  @PUT
  public void success(@PathParam("id") Long id) {
    service.success(id);
  }
}
