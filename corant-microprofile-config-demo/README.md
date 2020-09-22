# Corant Microprofile Config Demo
[corant-config](https://github.com/finesoft/corant/tree/master/corant-config) implements [microprofile-config](https://github.com/eclipse/microprofile-config)

# Requirements
1. java 8 (or newer)
2. maven 2.2.1 (or newer)
# Tutorial
## Add Corant Microprofile Config dependency
```
<dependency>
    <groupId>org.corant</groupId>
    <artifactId>corant-suites-microprofile-config</artifactId>
    <version>${version.corant}</version>
</dependency>
```
more details see the `pom.xml`
## Microprofile Config Simple Usage
* Programmatic
```java
String value = ConfigProvider.getConfig().getValue("someserver.host", String.class);
```
* Simple Dependency Injection
```java
  @Inject Config config;

  @Inject
  @ConfigProperty(name = "someserver.host")
  String host;

  @Inject
  @ConfigProperty(name = "someserver.port.property.expression.demo")
  String port;
```
## Corant Config Annotation Simple Usage
* `@ConfigKeyRoot` and `@ConfigKeyItem` usage
```java
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
}
```
The `PetsConfigurationDemo` class fields value mapping in the `application.properties` file.

For example:
```
my.pets.dog.name=small dog
my.pets.dog.age=12
my.pets.dog.weight=21.21
my.pets.dog.is-weak=false
my.pets.dog.hobbies=laugh,smile,cry,run
my.pets.dog.map-value.key1=value1
my.pets.dog.map-value.key2=value2
my.pets.dog.pets-handlers-classes=org.corant.microprofile.config.demo.CatHandlerImpl,org.corant.microprofile.config.demo.DogHandlerImpl


my.pets.cat.name=hello kitty
my.pets.cat.age=2
my.pets.cat.weight=3.34
my.pets.cat.hobbies=sleep
```
* How to instantiate the `@ConfigKeyRoot` annotated class ?

```java
Map<String, PetsConfigurationDemo> demoMap = DeclarativeConfigResolver.resolveMulti(PetsConfigurationDemo.class);
PetsConfigurationDemo dog = demoMap.get("dog");
PetsConfigurationDemo cat = demoMap.get("cat");
```

or 

```
PetsConfigurationDemo demoConfig = DeclarativeConfigResolver.resolveSingle(PetsConfigurationDemo.class);
```
In this case , your app conf should like this:
```
my.pets.name=hello kitty
my.pets.age=2
my.pets.weight=3.34
my.pets.hobbies=sleep
```











