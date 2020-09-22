package org.corant.microprofile.config.demo;

import org.corant.config.declarative.ConfigKeyItem;
import org.corant.config.declarative.ConfigKeyRoot;
import org.corant.config.declarative.DeclarativeConfig;
import org.corant.config.declarative.DeclarativePattern;
import org.eclipse.microprofile.config.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.corant.context.Instances.resolve;
import static org.corant.shared.util.Classes.tryAsClass;
import static org.corant.shared.util.Empties.isNotEmpty;
import static org.corant.shared.util.Strings.isNoneBlank;

/**
 * corant <br>
 *
 * @auther sushuaihao 2020/9/22
 * @since
 */
@ConfigKeyRoot(value = "my.pets", keyIndex = 2, ignoreNoAnnotatedItem = false)
public class PetsConfigurationDemo implements DeclarativeConfig {
  protected List<String> petsHandlersClasses = new ArrayList<>();
  private String name;
  private Integer age;
  private Double weight;
  @ConfigKeyItem(defaultValue = "false")
  private Boolean isWeak;
  private List<String> hobbies = new ArrayList<>();
  @ConfigKeyItem(pattern = DeclarativePattern.PREFIX)
  private Map<String, String> mapValue = new HashMap<>();
  private List<PetsHandler> petsHandlers = new ArrayList<>();

  public Integer getAge() {
    return age;
  }

  public List<String> getHobbies() {
    return hobbies;
  }

  public Map<String, String> getMapValue() {
    return mapValue;
  }

  public String getName() {
    return name;
  }

  public List<PetsHandler> getPetsHandlers() {
    return petsHandlers;
  }

  public List<String> getPetsHandlersClasses() {
    return petsHandlersClasses;
  }

  public Boolean getWeak() {
    return isWeak;
  }

  public Double getWeight() {
    return weight;
  }

  @Override
  public boolean isValid() {
    return isNoneBlank(name);
  }

  @Override
  public void onPostConstruct(Config config, String key) {
    if (isNotEmpty(petsHandlersClasses)) {
      for (String cls : petsHandlersClasses) {
        Class<?> rscls = tryAsClass(cls);
        if (rscls != null && PetsHandler.class.isAssignableFrom(rscls)) {
          PetsHandler rs = (PetsHandler) resolve(rscls);
          petsHandlers.add(rs);
        }
      }
    }
  }
}
