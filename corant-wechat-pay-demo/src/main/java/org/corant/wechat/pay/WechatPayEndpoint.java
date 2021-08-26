package org.corant.wechat.pay;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.corant.modules.json.Jsons;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * corant-root <br>
 *
 * @auther sushuaihao 2020/11/23
 * @since
 */
@Path("/wechat_pay")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WechatPayEndpoint {

  @Inject WechatPayConfig wechatPayConfig;
  private WXPay wxPay;

  /**
   * 微信支付异步通知
   *
   * @param xmlvo
   * @return
   */
  @POST
  @Path("/notify")
  @Consumes(MediaType.APPLICATION_XML)
  public Response notify(WechatNotifyXMLVO xmlvo) throws Exception {
    Map map = Jsons.copyMapper().convertValue(xmlvo, Map.class);
    return Response.ok(WXPayUtil.mapToXml(map)).build();
  }

  /**
   * 查询交易信息
   *
   * @param outTradeNo
   * @return
   * @throws Exception
   */
  @GET
  @Path("/query")
  public Response query(@QueryParam(value = "out_trade_no") String outTradeNo) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("out_trade_no", outTradeNo);
    Map<String, String> result = wxPay.orderQuery(map);
    return Response.ok(result).build();
  }

  /**
   * 创建预交易二维码
   *
   * @param productId
   * @param orderId
   * @param body
   * @param amt
   * @return
   * @throws Exception
   */
  @Path("/show")
  @GET
  public Response show(
      @QueryParam(value = "product_id") String productId,
      @QueryParam(value = "out_trade_no") String orderId,
      @QueryParam(value = "body") String body,
      @QueryParam(value = "total_fee") String amt)
      throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("body", body);
    map.put("out_trade_no", orderId);
    map.put("total_fee", amt);
    map.put("trade_type", "NATIVE");
    map.put("product_id", productId);
    Map<String, String> result = wxPay.unifiedOrder(map);
    return Response.ok(result).build();
  }

  @PostConstruct
  void onPostConstruct() throws Exception {
    wxPay =
        new WXPay(
            wechatPayConfig, wechatPayConfig.getNotifyUrl(), true, wechatPayConfig.getUseSandbox());
  }
}
