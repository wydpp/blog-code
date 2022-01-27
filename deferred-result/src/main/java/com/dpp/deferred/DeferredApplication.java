package com.dpp.deferred;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.annotation.WebServlet;

//开启异步servlet支持
@WebServlet(asyncSupported = true)
@SpringBootApplication
public class DeferredApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeferredApplication.class, args);
    }

    /*@Bean
    public ConfigurableServletWebServerFactory webServerFactory(){
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> {
            connector.setProperty("relaxedQueryChars","|{}[](),/:;<=>?@[\\]{}\\");
            connector.setProperty("relaxedPathChars","|{}[](),/:;<=>?@[\\]{}\\");
        });
        return factory;
    }*/

}
