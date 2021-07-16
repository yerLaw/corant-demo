package org.corant.aliyun.oss.demo;

import org.corant.Corant;

import javax.enterprise.context.ApplicationScoped;

/**
 * corant-microprofile-restclient-demo <br>
 *
 * @auther sushuaihao 2020/9/11
 * @since
 */
@ApplicationScoped
public class App {

  public static void main(String[] args) {
    AliyunFileUpload aliyunFileUpload = Corant.call(AliyunFileUpload.class);
    aliyunFileUpload.upload();

  }
}
