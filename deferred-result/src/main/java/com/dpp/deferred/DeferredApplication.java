package com.dpp.deferred;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.annotation.WebServlet;

/**
 * 开启异步servlet支持
 * @author wydpp
 */
@WebServlet(asyncSupported = true)
@SpringBootApplication
public class DeferredApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeferredApplication.class, args);
    }

}
