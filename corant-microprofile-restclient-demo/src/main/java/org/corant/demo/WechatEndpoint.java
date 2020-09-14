package org.corant.demo;

import org.corant.suites.cloud.tencent.wechat.WechatOAuthClient;
import org.corant.suites.cloud.tencent.wechat.WechatOAuthConfig;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Map;

/**
 * corant-microprofile-restclient-demo <br>
 *
 * @auther sushuaihao 2020/9/11
 * @since
 */
@Path("/wechat")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WechatEndpoint {

  @Inject WechatOAuthConfig authConfig;
  @Inject @RestClient WechatOAuthClient wechatOAuthClient;
  private String state;

  @Path("/callback")
  @GET
  public Response callback(@QueryParam("code") String code, @QueryParam("state") String state) {
    System.out.println("code = " + code);
    System.out.println("state = " + state);
    wechatOAuthClient.grantAccessToken(
        authConfig.getAppid(), authConfig.getSecret(), code, "authorization_code");
    return null;
  }

  @Path("/hello")
  @POST
  public Response helloWorld() {
    return Response.ok("hello world").build();
  }

  @Path("/redirect")
  @POST
  public Response redirect(Map<?, ?> cmd) throws URISyntaxException, UnsupportedEncodingException {
    return Response.seeOther(
            new URI(
                MessageFormat.format(
                    authConfig.getQrCodeUrl(),
                    authConfig.getAppid(),
                    URLEncoder.encode(authConfig.getRedirectUri(), "UTF-8"),
                    state)))
        .build();
  }

  @Path("/wechatOAuthClient")
  @POST
  public Response wechatOAuthClient() {
    wechatOAuthClient.grantAccessToken("appid", "secret", "code", "authorization_code");
    return Response.ok("hello world").build();
  }
}
