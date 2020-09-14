package cn.tom.tom.api.client;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import java.util.Map;

@Path("/api")
@RegisterRestClient
public interface TomAPIClient {

  @PUT
  @Path("/compensate")
  @Compensate
  void compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraIdUrl);

  @PUT
  @Path("/complete")
  @Complete
  void complete(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraIdUrl);

  @POST
  @Path("/")
  @LRA(end = false)
  Map create(Map<?, ?> cmd);

  @Path("/v1/tom/fail/{id}")
  @PUT
  void fail(@PathParam("id") Long id);

  @GET
  @Path("/v1/tom/lraId/{id}")
  Long getTomByLraId(@PathParam("id") String id);

  @Path("/v1/tom/success/{id}")
  @PUT
  void success(@PathParam("id") Long id);
}
