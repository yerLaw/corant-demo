package org.corant.jpa.demo.test;

import org.corant.devops.test.unit.CorantJUnit4ClassRunner;
import org.corant.devops.test.unit.RunConfig;
import org.corant.jpa.demo.App;
import org.corant.suites.jpa.shared.JPAService;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * corant <br>
 *
 * @auther sushuaihao 2020/9/22
 * @since
 */
@RunWith(CorantJUnit4ClassRunner.class)
@RunConfig(configClass = App.class)
public class CorantTest {

  //  @Inject @PersistenceContext EntityManager entityManager;

  @Inject JPAService jpaService;

  @Inject @Any Instance<EntityManagerFactory> entityManagerFactory;

  @PersistenceUnit(unitName = "testPu")
  EntityManagerFactory entityManagerFactory1;

  @Test
  public void getEmf() {
    //    System.out.println("entityManager = " + entityManager.toString());
    //    System.out.println("entityManagerFactory = " + entityManagerFactory);
    EntityManagerFactory testPu = jpaService.getEntityManagerFactory("testPu");
    System.out.println("testPu = " + testPu);
  }
}
