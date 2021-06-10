package org.corant.query.demo;

import java.time.Instant;

/**
 * my-app <br>
 *
 * @author sushuaihao 2021/6/9
 * @since
 */
public class QueryUserCriteria {
  Long employerPartyId;
  Instant createdTimeStart;
  Instant createdTimeEnd;
  String keyword;

  public QueryUserCriteria() {}

  public QueryUserCriteria(
      Long employerPartyId, Instant createdTimeStart, Instant createdTimeEnd, String keyword) {
    this.employerPartyId = employerPartyId;
    this.createdTimeStart = createdTimeStart;
    this.createdTimeEnd = createdTimeEnd;
    this.keyword = keyword;
  }

  public Instant getCreatedTimeEnd() {
    return createdTimeEnd;
  }

  public Instant getCreatedTimeStart() {
    return createdTimeStart;
  }

  public Long getEmployerPartyId() {
    return employerPartyId;
  }

  public String getKeyword() {
    return keyword;
  }

  public QueryUserCriteria setCreatedTimeEnd(Instant createdTimeEnd) {
    this.createdTimeEnd = createdTimeEnd;
    return this;
  }

  public QueryUserCriteria setCreatedTimeStart(Instant createdTimeStart) {
    this.createdTimeStart = createdTimeStart;
    return this;
  }

  public QueryUserCriteria setEmployerPartyId(Long employerPartyId) {
    this.employerPartyId = employerPartyId;
    return this;
  }

  public QueryUserCriteria setKeyword(String keyword) {
    this.keyword = keyword;
    return this;
  }
}
