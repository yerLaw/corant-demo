package org.corant.swagger.demo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * corant-root <br>
 *
 * @auther sushuaihao 2020/11/18
 * @since
 */
@Schema(description = "用户")
public class UserVO {
  private Long id;

  @Schema(description = "用户姓名")
  private String name;

  @Schema(description = "地址")
  private String address;

  @Schema(description = "年龄")
  private Integer age;

  public String getAddress() {
    return address;
  }

  public Integer getAge() {
    return age;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public UserVO setAddress(String address) {
    this.address = address;
    return this;
  }

  public UserVO setAge(Integer age) {
    this.age = age;
    return this;
  }

  public UserVO setId(Long id) {
    this.id = id;
    return this;
  }

  public UserVO setName(String name) {
    this.name = name;
    return this;
  }
}
