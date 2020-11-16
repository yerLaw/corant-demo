package org.corant.alipay.demo;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * 公钥证书方式 corant <br>
 *
 * @auther sushuaihao 2020/10/28
 * @since
 */
@Path("/alipay")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PayEndpoint {

  private AlipayClient alipayClient;
  @Inject AlipayConfig alipayConfig;

  @Path("/notify")
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response paidNotify(@Context HttpServletRequest request) throws AlipayApiException {
    Map<String, String[]> map = request.getParameterMap();
    Map<String, String> paramMap = new HashMap<>();
    for (String key : map.keySet()) {
      String value = request.getParameter(key);
      paramMap.put(key, value);
    }
    boolean signVerifyPass =
        AlipaySignature.rsaCertCheckV1(
            paramMap,
            alipayConfig.getAlipayCertPublicKeyPath(),
            alipayConfig.getCharset(),
            alipayConfig.getSignType());
    if (signVerifyPass) {
      String outTradeNo = paramMap.get("out_trade_no");
      String tradeNo = paramMap.get("trade_no");
      String tradeStatus = paramMap.get("trade_status");
      String totalAmount = paramMap.get("total_amount");
      System.out.println("outTradeNo = " + outTradeNo);
      System.out.println("tradeNo = " + tradeNo);
      System.out.println("tradeStatus = " + tradeStatus);
      System.out.println("totalAmount = " + totalAmount);
      // 商户校验逻辑...
    }
    return Response.ok("success").build();
  }

  @Path("/show")
  @GET
  public Response payShow(
      @QueryParam(value = "return_url") String returnUrl,
      @QueryParam(value = "order_id") String orderId,
      @QueryParam(value = "amount") String amount,
      @QueryParam(value = "order_name") String orderName)
      throws AlipayApiException {
    AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
    alipayRequest.setReturnUrl(returnUrl);
    alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
    Map<String, String> param = new HashMap<>();
    param.put("out_trade_no", orderId);
    param.put(
        "product_code",
        "FAST_INSTANT_TRADE_PAY"); // 说明：销售产品码，与支付宝签约的产品码名称。 注：目前仅支持FAST_INSTANT_TRADE_PAY
    param.put("total_amount", amount);
    param.put("subject", orderName);
    alipayRequest.setBizContent(JSONObject.toJSONString(param));
    String body = alipayClient.pageExecute(alipayRequest).getBody();
    return Response.ok(body).build();
  }

  @POST
  @Path("/query")
  public Response query(Map<String, String> param) throws AlipayApiException {
    AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
    request.setBizContent(JSONObject.toJSONString(param));
    AlipayTradeQueryResponse response = alipayClient.certificateExecute(request);
    return Response.ok(response).build();
  }

  @Path("/return_url")
  @GET
  public Response returnUrl(@Context HttpServletRequest request) throws AlipayApiException {
    Map<String, String[]> map = request.getParameterMap();
    Map<String, String> paramMap = new HashMap<>();
    for (String key : map.keySet()) {
      String value = request.getParameter(key);
      paramMap.put(key, value);
    }
    boolean rsaCertCheckV1 =
        AlipaySignature.rsaCertCheckV1(
            paramMap,
            alipayConfig.getAlipayCertPublicKeyPath(),
            alipayConfig.getCharset(),
            alipayConfig.getSignType());
    String res;
    if (rsaCertCheckV1) {
      res = "同步验签成功";
    } else {
      res = "同步验签失败";
    }
    String outTradeNo = paramMap.get("out_trade_no");
    String tradeNo = paramMap.get("trade_no");
    System.out.println("outTradeNo = " + outTradeNo);
    System.out.println("tradeNo = " + tradeNo);
    res = res + "trade_no:" + tradeNo + ",out_trade_no:" + outTradeNo;
    return Response.ok(res).build();
  }

  @Path("/test")
  @POST
  public Response test() {
    return Response.ok("success").build();
  }

  @PostConstruct
  void onPostConstruct() throws AlipayApiException {
    CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
    // 设置网关地址
    certAlipayRequest.setServerUrl(alipayConfig.getGatewayUrl());
    // 设置应用Id
    certAlipayRequest.setAppId(alipayConfig.getAppid());
    // 设置应用私钥
    certAlipayRequest.setPrivateKey(alipayConfig.getMerchantPrivateKey());
    // 设置请求格式，固定值json
    certAlipayRequest.setFormat("json");
    // 设置字符集
    certAlipayRequest.setCharset(alipayConfig.getCharset());
    // 设置签名类型
    certAlipayRequest.setSignType(alipayConfig.getSignType());
    // 设置应用公钥证书路径
    certAlipayRequest.setCertPath(alipayConfig.getAppPublicKeyPath());
    // 设置支付宝公钥证书路径
    certAlipayRequest.setAlipayPublicCertPath(alipayConfig.getAlipayCertPublicKeyPath());
    // 设置支付宝根证书路径
    certAlipayRequest.setRootCertPath(alipayConfig.getRootCertPath());
    // 构造client
    alipayClient = new DefaultAlipayClient(certAlipayRequest);
  }
}
