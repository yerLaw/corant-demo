package org.corant.query.demo;

import org.corant.devops.test.unit.CorantJUnit4ClassRunner;
import org.corant.devops.test.unit.RunConfig;
import org.corant.modules.query.NamedQueryService;
import org.corant.modules.query.QueryParameter;
import org.corant.modules.query.QueryService;
import org.corant.modules.query.sql.cdi.SqlQuery;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * my-app <br>
 *
 * @author sushuaihao 2021/6/9
 * @since
 */
@RunWith(CorantJUnit4ClassRunner.class)
@RunConfig(configClass = App.class)
public class Test {
  @Inject @SqlQuery NamedQueryService sqlQueryService;

  @org.junit.Test
  public void test() {
    QueryParameter.GenericQueryParameter<QueryUserCriteria> param =
        new QueryParameter.GenericQueryParameter<>();
    param.setLimit(10);
    param.setOffset(0);
    param.setCriteria(new QueryUserCriteria());
    QueryService.Paging<Object> page = sqlQueryService.page("Profiles.queryUser", param);
    int total = page.getTotal();
  }
}
