# Corant Swagger Demo

# Tutorial
## 1. Prerequisites
* maven 2.2.1 (or newer)
* JDK 11+
* Swagger UI
## 2. Usage
* Add dependency.
```
<dependency>
    <groupId>io.swagger.core.v3</groupId>
    <artifactId>swagger-jaxrs2</artifactId>
        <exclusions>
            <exclusion>
                <groupId>com.fasterxml.jackson.core</groupId>
                <artifactId>jackson-databind</artifactId>
            </exclusion>
        </exclusions>
    <version>2.0.8</version>
</dependency>
```
more details see the `pom.xml`
* Add a REST endpoint.
```java
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
```
* Solve the problem of CORS , in `application.properties`
```
## Servlet cors configurations
rs.cors.enabled=true
```
* You need a swagger UI server.Check [this](https://github.com/swagger-api/swagger-ui/blob/HEAD/docs/usage/installation.md) to install.
* Run the app and visit http://192.168.100.200:9001/?url=http://localhost:8080/openapi.json (192.168.100.200:9001 is my swagger UI server) and you should see the following.
![image](https://github.com/sushuaihao/corant-demo/blob/master/corant-swagger-demo/img/demo.png)
