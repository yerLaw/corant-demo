package cn.tom.client.gateway;

import cn.tom.client.application.TomAndJerryService;
import org.asosat.ddd.gateway.AbstractRests;
import org.corant.shared.exception.CorantRuntimeException;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Map;

import static org.corant.shared.util.Maps.getMapMap;

/**
 * tom-jerry <br>
 *
 * @auther sushuaihao 2019/11/12
 * @since
 */
@Path("/client/tomAndJerry")
@ApplicationScoped
public class TomAndJerryEndpoint extends AbstractRests {

  @Inject TomAndJerryService service;

  @PUT
  @Path("/compensate")
  @Compensate
  public Response compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraIdUrl) {
    String lraId = lraIdUrl.substring(lraIdUrl.lastIndexOf('/') + 1);
    Long jerry = service.getJerryByLraId(lraId);
    Long tom = service.getTomByLraId(lraId);
    if (jerry != null) {
      service.jerryFail(jerry);
    }
    if (tom != null) {
      service.tomSuccess(tom);
    }
    System.out.println("tom and jerry compensate");
    return Response.ok("Compensate").build();
  }

  @PUT
  @Path("/complete")
  @Complete
  public Response complete(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraIdUrl) {
    String lraId = lraIdUrl.substring(lraIdUrl.lastIndexOf('/') + 1);
    Long jerry = service.getJerryByLraId(lraId);
    Long tom = service.getTomByLraId(lraId);
    if (jerry != null) {
      service.jerrySuccess(jerry);
    }
    if (tom != null) {
      service.tomFail(tom);
    }
    System.out.println("tom and jerry complete");
    return Response.ok("Completed").build();
  }

  @Path("/createJerry/")
  @POST
  public Response createJerry(Map<?, ?> cmd) {
    Map jerry = service.createJerry(cmd);
    return this.ok(jerry);
  }

  @Path("/createTom/")
  @POST
  public Response createTom(Map<?, ?> cmd) {
    Map tom = service.createTom(cmd);
    return this.ok(tom);
  }

  @Path("/hello/")
  @GET
  public Response hello() {
    return this.ok("hello world");
  }

  @Path("/jerryFail/{id}")
  @PUT
  public Response jerryFail(@PathParam("id") Long id) {
    service.jerryFail(id);
    return this.ok();
  }

  @Path("/jerrySuccess/{id}")
  @PUT
  public Response jerrySuccess(@PathParam("id") Long id) {
    service.jerrySuccess(id);
    return this.ok();
  }

  @POST
  @LRA(end = false)
  @Path("/")
  public Response tomCatchJerry(
      @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraIdUrl, Map<String, Object> cmd) {
    String lraId = lraIdUrl.substring(lraIdUrl.lastIndexOf('/') + 1);
    Map<String, Object> jerryCmd = getMapMap(cmd, "jerry");
    Map<String, Object> tomCmd = getMapMap(cmd, "tom");
    jerryCmd.put("lraId", lraId);
    tomCmd.put("lraId", lraId);
    service.createJerry(jerryCmd);
    service.createTom(tomCmd);
    BigDecimal jerrySpeed = new BigDecimal((String) jerryCmd.get("speed"));
    BigDecimal tomSpeed = new BigDecimal((String) tomCmd.get("speed"));
    if (jerrySpeed.compareTo(tomSpeed) == -1) {
      throw new CorantRuntimeException("jerry is caught!");
    }
    return ok();
  }
}
