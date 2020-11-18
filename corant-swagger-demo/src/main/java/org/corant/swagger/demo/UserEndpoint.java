package org.corant.swagger.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * corant <br>
 *
 * @auther sushuaihao 2020/11/16
 * @since
 */
@Path("/user")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserEndpoint {

  @Path("/get")
  @GET
  @Operation(summary = "获取用户详情")
  @ApiResponse(content = @Content(schema = @Schema(implementation = UserVO.class)))
  public Response hello(UserParam param) {
    return Response.ok("Hello World!").build();
  }
}
