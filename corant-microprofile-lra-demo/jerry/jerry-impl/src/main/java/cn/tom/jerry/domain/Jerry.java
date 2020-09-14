package cn.tom.jerry.domain;

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
@Table(name = "jerry")
public class Jerry extends AbstractGenericAggregate<Param, Jerry> {
  @Column private String name;
  @Column private Integer age;
  @Column private BigDecimal speed;
  @Column private Boolean successful;

  @Basic(optional = true)
  @Size(min = 1, max = 100)
  @Column(name = "lraId")
  private String lraId;

  public Jerry() {}

  public Jerry(String name, Integer age, BigDecimal speed, String lraId) {
    this.name = name;
    this.age = age;
    this.speed = speed;
    setLraId(lraId);
  }

  public Jerry fail() {
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

  public Jerry setLraId(String lraId) {
    this.lraId = lraId;
    return this;
  }

  public Jerry success() {
    return setSuccessful(true);
  }

  private Jerry setAge(Integer age) {
    this.age = age;
    return this;
  }

  private Jerry setName(String name) {
    this.name = name;
    return this;
  }

  private Jerry setSpeed(BigDecimal speed) {
    this.speed = speed;
    return this;
  }

  private Jerry setSuccessful(Boolean successful) {
    this.successful = successful;
    return this;
  }
}
