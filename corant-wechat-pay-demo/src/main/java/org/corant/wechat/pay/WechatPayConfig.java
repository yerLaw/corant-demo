package org.corant.wechat.pay;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStream;

/**
 * corant-root <br>
 *
 * @auther sushuaihao 2020/11/25
 * @since
 */
@ApplicationScoped
public class WechatPayConfig extends WXPayConfig {

  @Inject
  @ConfigProperty(name = "cloud.wechat.pay.sandbox", defaultValue = "false")
  private Boolean useSandbox;

  @Inject
  @ConfigProperty(name = "cloud.wechat.pay.notify-url")
  private String notifyUrl;

  @Inject
  @ConfigProperty(name = "cloud.wechat.pay.appid")
  private String appId;

  @Inject
  @ConfigProperty(name = "cloud.wechat.pay.mchid")
  private String mchId;

  @Inject
  @ConfigProperty(name = "cloud.wechat.pay.key")
  private String key;

  public String getNotifyUrl() {
    return notifyUrl;
  }

  public Boolean getUseSandbox() {
    return useSandbox;
  }

  protected String getAppID() {
    return appId;
  }

  protected InputStream getCertStream() {
    return null;
  }

  protected String getKey() {
    return key;
  }

  protected String getMchID() {
    return mchId;
  }

  protected IWXPayDomain getWXPayDomain() {
    return new IWXPayDomain() {
      @Override
      public DomainInfo getDomain(WXPayConfig config) {
        return new DomainInfo(WXPayConstants.DOMAIN_API, true);
      }

      @Override
      public void report(String domain, long elapsedTimeMillis, Exception ex) {}
    };
  }
}
