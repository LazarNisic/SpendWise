package com.ldz.SpendWise.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfiguration implements Serializable {
    private List<String> swaggerUrls;
    private Jwt jwt;

    @Data
    public static class Jwt implements Serializable {
        private String secret;
        private Long accessExpirationMs;
        private Long refreshExpirationMs;
    }
}
