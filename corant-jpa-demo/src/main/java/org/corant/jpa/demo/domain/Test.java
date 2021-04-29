package org.corant.jpa.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * corant-suites-concurrency <br>
 *
 * @author sushuaihao 2021/4/1
 * @since
 */
@Entity
public class Test {

  private Long id;

  @Id
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
