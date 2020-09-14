import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * corant <br>
 *
 * @auther sushuaihao 2020/9/11
 * @since
 */
public class UrlEncodeTest {

  @Test
  public void test() throws UnsupportedEncodingException {
    String encode = URLEncoder.encode("https://passport.yhd.com/wechat/callback.do", "UTF-8");
    System.out.println("encode = " + encode);
  }
}
