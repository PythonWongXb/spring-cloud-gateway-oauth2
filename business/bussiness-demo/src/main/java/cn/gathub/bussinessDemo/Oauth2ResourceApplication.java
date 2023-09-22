package cn.gathub.bussinessDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Honghui [wanghonghui_work@163.com] 2021/3/16
 */
@EnableFeignClients(basePackages = "cn.gathub.client.clients")
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("cn.gathub.business.mapper")
public class Oauth2ResourceApplication {

  public static void main(String[] args) {
    SpringApplication.run(Oauth2ResourceApplication.class, args);
  }

}