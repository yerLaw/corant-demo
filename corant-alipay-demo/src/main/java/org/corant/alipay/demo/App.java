package org.corant.alipay.demo;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import org.corant.Corant;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * corant <br>
 *
 * @auther sushuaihao 2020/10/28
 * @since
 */
@ApplicationScoped
@ApplicationPath("/")
public class App extends Application {

  public static void main(String[] args) throws AlipayApiException {
        Corant.startup();
//    String publicKey =
//        AlipaySignature.getAlipayPublicKey(
//            "E:\\havaFun\\corant-demo\\corant-alipay-demo\\src\\main\\resources\\appCertPublicKey_2016102700769888.crt");
//    System.out.println("应用公钥数据：" + publicKey);
  }
}
