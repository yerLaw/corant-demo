# Corant JAX-RS Demo

# Tutorial
## 1. Prerequisites
* maven 2.2.1 (or newer)
* JDK 8 or 11+
## 2. Add JAX-RS component
* Add dependency.
```
<dependency>
    <groupId>org.corant</groupId>
    <artifactId>corant-suites-jaxrs-resteasy</artifactId>
    <version>${version.corant}</version>
</dependency>
```
* Extends the `Application`.
```java
@ApplicationScoped
@ApplicationPath("/")
public class App extends Application {
  public static void main(String[] args) {
    Corant.startup();
  }
}
```
* Add a REST endpoint.
```java
@Path("/app")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldEndpoint {
  @Path("/greeting")
  @GET
  public Response hello() {
    return Response.ok("Hello World!").build();
  }
}
```
* Run the app and visit http://localhost:8080/app/greeting and you should see the following.
```
Hello World!
```
