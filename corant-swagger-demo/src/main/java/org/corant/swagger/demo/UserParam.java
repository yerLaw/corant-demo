package org.corant.swagger.demo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * corant-root <br>
 *
 * @auther sushuaihao 2020/11/18
 * @since
 */
@Schema(description = "用户参数")
public class UserParam {
  @Schema(description = "姓名")
  private String name;

  @Schema(description = "用户ID")
  private String id;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public UserParam setId(String id) {
    this.id = id;
    return this;
  }

  public UserParam setName(String name) {
    this.name = name;
    return this;
  }
}
