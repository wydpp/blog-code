package com.wydpp.tcp.server.ssl;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * @author wydpp
 */
public class SslContextFactory {

    public static SslContext getSslContext() {
        try {
            File certificate = new ClassPathResource("certs/openssl/server.cer").getFile();
            File privateKey = new ClassPathResource("certs/openssl/server_pcks8_private.key").getFile();
            return SslContextBuilder.forServer(certificate, privateKey, "123456")
                    .sslProvider(SslProvider.OPENSSL)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        File certificate = new ClassPathResource("certs/openssl/server.cer").getFile();
        File keyFile = new ClassPathResource("certs/openssl/server_pcks8_private.key").getFile();
        String keyPassword = "123456";
        SslContextBuilder.forServer(certificate, keyFile, keyPassword)
                .sslProvider(SslProvider.OPENSSL)
                .build();
    }


}
