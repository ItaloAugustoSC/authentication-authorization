package com.italoasc.authentication_authorization.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AWSCloudProperties {
    private static final String DEFAULT_SERVICE_ACCOUNT = "default";

    @Builder.Default
    private Map<String, ServiceAccount> aws = new HashMap<>();

    @Data
    @Builder
    public static class ServiceAccount{
        private String region;
        private ServiceAccountCredentials credentials;
    }

    @Data
    @Builder
    public static class ServiceAccountCredentials{
        private String accessKey;
        private String secretKey;
    }
}