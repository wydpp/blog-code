package com.wydpp.tcp.util;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.util.Base64;

/**
 * @author wydpp
 */
public class EncryptUtil {
    private static final String ALGOGRITHM = "RSA";
    private static final String PUBLIC_KEY_PATH = "public.key";
    private static final String PRIVATE_KEY_PATH = "private.key";

    private static String publicKey =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsIpdgCDGKbLBjdQKu3127rgajbUbgbiLb7iY7nlviaVLDa8klELBkA29PYm+Piq8U47+08ZB2MIaRBsw+Uq31r2aFHr1e2Mreb7G+viCULAi3pp/TT8YHDACHNkxkv3BzT29wQDJ6QA2fY7FPGyt2dP017ZcweIOVO73wqi2HVFo4p41ltKiV4+TmycvMmIejQ4J+AJE+wNN79pqPRTyeg2CsJTODnRCP1tUVWBVC884+j5I+5bbJEmt6RP445pshaWMqXRhwGdYG7VTSappOnsA0vpW9SNhFTix+CbilpmnRvV+lODRvPxBUC1ET6pJ6BP12gOIBVc5JsyFjhhHOwIDAQAB";
    private static String privateKey =
            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCwil2AIMYpssGN1Aq7fXbuuBqNtRuBuItvuJjueW+JpUsNrySUQsGQDb09ib4+KrxTjv7TxkHYwhpEGzD5SrfWvZoUevV7Yyt5vsb6+IJQsCLemn9NPxgcMAIc2TGS/cHNPb3BAMnpADZ9jsU8bK3Z0/TXtlzB4g5U7vfCqLYdUWjinjWW0qJXj5ObJy8yYh6NDgn4AkT7A03v2mo9FPJ6DYKwlM4OdEI/W1RVYFULzzj6Pkj7ltskSa3pE/jjmmyFpYypdGHAZ1gbtVNJqmk6ewDS+lb1I2EVOLH4JuKWmadG9X6U4NG8/EFQLURPqknoE/XaA4gFVzkmzIWOGEc7AgMBAAECggEAR8krtgMdsSz/6YUUjCK7btyr532CXuBEyrwca7Qzd5wgBcN3M1Zl0QbVMaXGFw9Xnv6hfkm60sIy3gjUhDouwQI+Gh/ZnD+6GPpLfRf6HYdR1oSqSSXjzupyoXpr3yKSYLu/YjDI6ZbtkWaxqx1iIBZBFEoSM8CRJVY6pwvlacvW5xQ0TNGXSrOH1EF7tk4LQiVBHhq67hT9fVIT65+MUnzcNDX2S1X+YfxZLmaMDdjSmOXt5crKXGJKg/h6UWrixh8ym/r9fq38v9zrypmjskLGVOYSDspelp3N5vuUPUGEjhx/lgbLcyM4m2K/FR74bAYJfBZBpBIhkXwi5CPhQQKBgQD3MSWhPfNzJvkJGUUjmNiDQcm81O0BBMjDLIH0arr/t66/9kfA2hcprC0N+zsPILj9xmLXzP+WkaswBfO3qU8EMTni/7RPBtJ1ZK8nao/ksslRK2wvDCRAFuq1XfOedmzgOOsLiRCPiXxUBJ9d5IOb1zdiYMW745ziij1u30+8IQKBgQC21L6bMj7kOAtW+KS6axzAYeKGdGvvCxqkt2spWSmegtdt3UudiYzVLUduqbBb+vej7JoLQGT8ly97SAgD4en/RkVJtBJFLkP1HND5gorI0CQI3MLWbifp8vSr31yIbbNa3KdrmJ60U08+jK6a8VAlOSfp9WCYIR2CH1o4no932wKBgHaXu6f6ItxyLDRzTTpg0C9Y1sKjnPlKJE+PAOIUepbItywdqhsaBtbVZeA5UgJV/qqyDSXfkhfY1zp5AI5yyhgGzfDcINNxQHNV1LqiY1qjc/ZfUM3Zk2K0G/Lbmk/CHUYNyKaGIc3bdiyQ8yyGv6V6wXWPXA5iixgcnpCczpJBAoGAVeyF58olZHwyWB1izXmpeYCHRUlRsHY3f1H0Oup8dNJpDGkEMHWEimtNNuTozVGVMKTxgHgy/cLPvdCMc8aogiA0T/8g1wJ3TarNS3XeY6HuTXvy1XPPrlNQSbYfdr6XnAw3YonHjFfa1jqnlIn0cIn8CCgZzWVCyPSTIijipCECgYEAipd7dfJc9aj0KEa4vorcJrcajTsfhHfxFCadEWOiBeSOiRTBu3sWzbXhH/Dulk6HQ4xB+2n7x0E7IJdnsFejU3CNYH2jhDgw2eckm5ArdGl/caGKjltKtlPIZdvYIMjqO3+MqZUBzplYttzL7VrkEgi5sYK2t6FwFhTDbuyiEMA=";


    public static void main(String[] args) throws Exception {
//        rsaGenerate();
        encryptAndDecrypt();
    }

    public static void rsaGenerate() throws Exception {
        //KeyPairGenerator引擎类用于产生密钥对，JDK(7)默认支持的算法有，DiffieHellman、DSA、RSA、EC
        KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGOGRITHM);
        //产生密钥对
        KeyPair keyPair = generator.generateKeyPair();
        //获取公钥
        PublicKey publicKey = keyPair.getPublic();
        //获取私钥
        PrivateKey privateKey = keyPair.getPrivate();

        String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        System.out.println("publicKey=\n" + publicKeyStr);
        System.out.println("privateKey=\n" + privateKeyStr);

        //将公钥与私钥写入文件，以备后用
        writeKey(PUBLIC_KEY_PATH, publicKey);
        writeKey(PRIVATE_KEY_PATH, privateKey);
    }

    public static void encryptAndDecrypt() throws Exception {
        Cipher cipher = Cipher.getInstance(ALGOGRITHM);
        //读取私钥，进行加密
        PrivateKey privateKey = (PrivateKey) readKey(PRIVATE_KEY_PATH);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        //加密
        String sendInfo = "《Java精讲》公众号";
        byte[] results = cipher.doFinal(sendInfo.getBytes());

        //读取公钥，进行解密
        PublicKey publicKey = (PublicKey) readKey(PUBLIC_KEY_PATH);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        //解密
        byte[] deciphered = cipher.doFinal(results);
        //得到明文
        String recvInfo = new String(deciphered);
        System.out.println(recvInfo);
    }

    public static void writeKey(String path, Key key) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(key);
        oos.close();
    }

    public static Key readKey(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream bis = new ObjectInputStream(fis);
        Object object = bis.readObject();
        bis.close();
        return (Key) object;
    }

}
