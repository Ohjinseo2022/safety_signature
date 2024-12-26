package com.safety_signature.safety_signature_back.config;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UrlPathHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Configuration
public class MinioConfiguration {
    @Value("${client.minio.endpoint}")
    private String endpoint;

    @Value("${client.minio.bucket}")
    private String bucket;

    @Value("${client.minio.accessKey}")
    private String accessKey;

    @Value("${client.minio.secretKey}")
    private String secretKey;

    /*
     *  Keycloak 서버와 통신하기 위한 클라이언트 빌더
     * */
    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();

        try {
            URI uri = new URI(endpoint);
            if(uri.getScheme().equalsIgnoreCase("https")) {
                minioClient.ignoreCertCheck();
            }
        } catch (NoSuchAlgorithmException | KeyManagementException | URISyntaxException err) {
            log.error(err.getMessage());
            throw new RuntimeException(err);
        }

        return minioClient;
    }
}
