package com.ohouse.ohouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
@EnableCaching
public class OhouseApplication {

  public static void main(String[] args) {
    SpringApplication.run(OhouseApplication.class, args);
  }

}
