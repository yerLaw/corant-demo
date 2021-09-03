package cn.tom.jerry.application;

import cn.tom.jerry.domain.Jerry;
import org.asosat.ddd.application.service.AbstractApplicationService;
import org.asosat.shared.Param;
import org.corant.modules.ddd.annotation.ApplicationServices;
import org.corant.modules.ddd.shared.repository.JPARepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * tom-jerry <br>
 *
 * @auther sushuaihao 2019/11/12
 * @since
 */
@ApplicationScoped
@Transactional
@ApplicationServices
public class JerryService extends AbstractApplicationService {

  @Inject JPARepository repo;

  public Jerry create(String name, Integer age, BigDecimal speed, String lraId) {
    return new Jerry(name, age, speed, lraId).preserve(Param.empty(), null);
  }

  public Jerry fail(Long id) {
    return repo.get(Jerry.class, id).fail();
  }

  public Jerry getByLraId(String lraId) {
    EntityManager entityManager = repo.getEntityManager();
    TypedQuery<Jerry> query =
        entityManager.createQuery("SELECT i FROM Jerry i WHERE i.lraId = :lraId", Jerry.class);
    List<Jerry> jerries = query.setParameter("lraId", lraId).getResultList();
    return jerries.size() > 0 ? jerries.get(0) : new Jerry();
  }

  public Jerry success(Long id) {
    return repo.get(Jerry.class, id).success();
  }
}
