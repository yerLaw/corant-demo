package cn.tom.tom.domain;

import org.asosat.ddd.domain.model.AbstractGenericAggregate;
import org.asosat.shared.Param;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * tom-jerry <br>
 *
 * @auther sushuaihao 2019/11/11
 * @since
 */
@Entity
@Table(name = "tom")
public class Tom extends AbstractGenericAggregate<Param, Tom> {
  @Column private String name;
  @Column private Integer age;
  @Column private BigDecimal speed;
  @Column private Boolean successful;

  @Basic
  @Size(min = 1, max = 100)
  @Column(name = "lraId")
  private String lraId;

  public Tom() {}

  public Tom(String name, Integer age, BigDecimal speed, String lraId) {
    this.name = name;
    this.age = age;
    this.speed = speed;
    setLraId(lraId);
  }

  public Tom fail() {
    return setSuccessful(false);
  }

  public Integer getAge() {
    return age;
  }

  public String getLraId() {
    return lraId;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getSpeed() {
    return speed;
  }

  public Boolean getSuccessful() {
    return successful;
  }

  public Tom setLraId(String lraId) {
    this.lraId = lraId;
    return this;
  }

  public Tom success() {
    return setSuccessful(true);
  }

  private Tom setAge(Integer age) {
    this.age = age;
    return this;
  }

  private Tom setName(String name) {
    this.name = name;
    return this;
  }

  private Tom setSpeed(BigDecimal speed) {
    this.speed = speed;
    return this;
  }

  private Tom setSuccessful(Boolean successful) {
    this.successful = successful;
    return this;
  }
}
