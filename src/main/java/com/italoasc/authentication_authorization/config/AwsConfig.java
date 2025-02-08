package com.italoasc.authentication_authorization.config;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

    @Value("${aws.default.access-key}")
    private String AWS_ACCESS_KEY_ID;

    @Value("${aws.default.secret-key}")
    private String AWS_SECRET_ACCESS_KEY;

    @Bean
    public AWSCredentialsProvider awsCredentials() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY));
    }

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService(AWSCredentialsProvider credentialsProvider) {
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(awsCredentials())
                .withRegion(Regions.US_EAST_1).build();
    }
}
