package cn.tom.client.gateway;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * tom-jerry <br>
 *
 * @auther sushuaihao 2019/11/12
 * @since
 */
@Path("/lra")
@ApplicationScoped
public class LraResource {

  @PUT
  @Path("/compensate")
  @Compensate
  public Response compensate(
      @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
      @HeaderParam(LRA.LRA_HTTP_RECOVERY_HEADER) URI recoveryId) {
    System.out.println("COMPENSATING LRA " + lraId);
    System.out.println("COMPENSATE recovery id: " + recoveryId);

    return Response.ok().build();
  }

  @PUT
  @Path("/complete")
  @Complete
  public Response complete(
      @HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
      @HeaderParam(LRA.LRA_HTTP_RECOVERY_HEADER) URI recoveryId) {
    System.out.println("COMPLETING LRA " + lraId);
    System.out.println("COMPLETE recovery id: " + recoveryId);

    return Response.ok().build();
  }

  @PUT
  @Path("/")
  @LRA
  public Response performLRA(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
    System.out.println("PERFORMING LRA work " + lraId);

    return Response.ok(lraId.toString()).build();
  }
}
