package io.narayana.lra.coordinator.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.corant.Corant;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

// mark the war as a JAX-RS archive
@ApplicationPath("/")
@OpenAPIDefinition(
    info = @Info(title = "LRA Coordinator", version = "1.0"),
    tags = @Tag(name = "LRA Coordinator"))
public class JaxRsActivator extends Application {
  public static void main(String... strings) {
    Corant.startup(JaxRsActivator.class);
  }
}
