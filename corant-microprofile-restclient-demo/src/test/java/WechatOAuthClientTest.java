import org.corant.demo.App;
import org.corant.devops.test.unit.CorantJUnit4ClassRunner;
import org.corant.devops.test.unit.RunConfig;
import org.corant.suites.cloud.tencent.wechat.WechatOAuthClient;
import org.corant.suites.cloud.tencent.wechat.WechatOAuthConfig;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Map;

/**
 * corant <br>
 *
 * @auther sushuaihao 2020/9/11
 * @since
 */
@RunWith(CorantJUnit4ClassRunner.class)
@RunConfig(configClass = App.class)
public class WechatOAuthClientTest {
  @Inject @RestClient WechatOAuthClient wechatOAuthClient;

  @Inject WechatOAuthConfig authConfig;

  @Test
  public void properties() {
    String appid = authConfig.getAppid();
    String qrCodeUrl = authConfig.getQrCodeUrl();
    String redirectUri = authConfig.getRedirectUri();
    String secret = authConfig.getSecret();
    System.out.println("appid = " + appid);
    System.out.println("qrCodeUrl = " + qrCodeUrl);
    System.out.println("redirectUri = " + redirectUri);
    System.out.println("secret = " + secret);
  }

  @Test
  public void test() {
    Map<?, ?> response =
        wechatOAuthClient.grantAccessToken("appid", "secret", "code", "authorization_code");
    System.out.println("response = " + response);
  }
}
