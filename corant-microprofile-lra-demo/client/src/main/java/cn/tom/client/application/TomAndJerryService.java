package cn.tom.client.application;

import cn.tom.jerry.api.JerryAPIClient;
import cn.tom.tom.api.client.TomAPIClient;
import org.asosat.ddd.application.service.AbstractApplicationService;
import org.corant.modules.ddd.annotation.ApplicationServices;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Map;

/**
 * tom-jerry <br>
 *
 * @auther sushuaihao 2019/11/12
 * @since
 */
@ApplicationScoped
@Transactional
@ApplicationServices
public class TomAndJerryService extends AbstractApplicationService {

  @Inject @RestClient JerryAPIClient jerryAPIClient;
  @Inject @RestClient TomAPIClient tomAPIClient;

  public Map createJerry(Map<?, ?> cmd) {
    return jerryAPIClient.create(cmd);
  }

  public Map createTom(Map<?, ?> cmd) {
    return tomAPIClient.create(cmd);
  }

  public Long getJerryByLraId(String lraId) {
    return jerryAPIClient.getJerryByLraId(lraId);
  }

  public Long getTomByLraId(String lraId) {
    return tomAPIClient.getTomByLraId(lraId);
  }

  public void jerryFail(Long id) {
    jerryAPIClient.fail(id);
  }

  public void jerrySuccess(Long id) {
    jerryAPIClient.success(id);
  }

  public void tomFail(Long id) {
    tomAPIClient.fail(id);
  }

  public void tomSuccess(Long id) {
    tomAPIClient.success(id);
  }
}
