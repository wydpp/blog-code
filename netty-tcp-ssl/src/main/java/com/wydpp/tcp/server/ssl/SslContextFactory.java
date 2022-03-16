package com.wydpp.tcp.server.ssl;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

/**
 * @author wydpp
 */
public class SslContextFactory {

    public static SslContext getSslContext() {
        try {
            File certificate = new ClassPathResource("certs/openssl/server.cer").getFile();
            File privateKey = new ClassPathResource("certs/openssl/server_private.key").getFile();
            return SslContextBuilder.forServer(certificate, privateKey, "123456")
                    .sslProvider(SslProvider.OPENSSL)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
