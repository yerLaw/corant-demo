package cn.tom.tom.application;

import cn.tom.tom.domain.Tom;
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
 * @auther sushuaihao 2019/11/11
 * @since
 */
@ApplicationScoped
@Transactional
@ApplicationServices
public class TomService extends AbstractApplicationService {

  @Inject JPARepository repo;

  public Tom create(String name, Integer age, BigDecimal speed, String lraId) {
    return new Tom(name, age, speed, lraId).preserve(Param.empty(), null);
  }

  public Tom fail(Long id) {
    return repo.get(Tom.class, id).fail();
  }

  public Tom getByLraId(String lraId) {
    EntityManager entityManager = repo.getEntityManager();
    TypedQuery<Tom> query =
        entityManager.createQuery("SELECT i FROM Tom i WHERE i.lraId = :lraId", Tom.class);
    List<Tom> toms = query.setParameter("lraId", lraId).getResultList();
    return toms.size() > 0 ? toms.get(0) : new Tom();
  }

  public Tom success(Long id) {
    return repo.get(Tom.class, id).success();
  }
}
