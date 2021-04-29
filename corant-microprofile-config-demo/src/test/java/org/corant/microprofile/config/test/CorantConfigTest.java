package org.corant.microprofile.config.test;

import org.corant.config.declarative.ConfigInstances;
import org.corant.devops.test.unit.CorantJUnit4ClassRunner;
import org.corant.devops.test.unit.RunConfig;
import org.corant.microprofile.config.demo.App;
import org.corant.microprofile.config.demo.PetsConfigurationDemo;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * corant <br>
 *
 * @auther sushuaihao 2020/9/22
 * @since
 */
@RunWith(CorantJUnit4ClassRunner.class)
@RunConfig(configClass = App.class)
public class CorantConfigTest {

  @Inject Config config;

  @Inject
  @ConfigProperty(name = "someserver.host")
  String host;

  @Inject
  @ConfigProperty(name = "someserver.port.property.expression.demo")
  String port;

  @Test
  public void configMapValue() {
    Map<String, PetsConfigurationDemo> demoMap =
        ConfigInstances.resolveMulti(PetsConfigurationDemo.class);
    PetsConfigurationDemo dog = demoMap.get("dog");
    Map<String, String> dogMapValue = dog.getMapValue();
    String value1 = dogMapValue.get("key1");
    assertEquals("value1", value1);
  }

  @Test
  public void configProvider() {
    String value = ConfigProvider.getConfig().getValue("someserver.host", String.class);
    assertEquals("192.168.2.32", value);
  }

  @Test
  public void test() {
    String value = config.getValue("someserver.host", String.class);
    assertEquals("192.168.2.32", value);
  }

  @Test
  public void testConfigExpression() {
    assertEquals("9087", port);
  }

  @Test
  public void testConfigProperty() {
    assertEquals("192.168.2.32", host);
  }

  @Test
  public void testResolveConfig() {
    Map<String, PetsConfigurationDemo> demoMap =
        ConfigInstances.resolveMulti(PetsConfigurationDemo.class);
    PetsConfigurationDemo dog = demoMap.get("dog");
    String dogName = dog.getName();
    assertEquals("small dog", dogName);
    PetsConfigurationDemo cat = demoMap.get("cat");
    String catName = cat.getName();
    assertEquals("hello kitty", catName);
  }
}
