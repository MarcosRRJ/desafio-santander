package br.com.santander.casecep.infrastructure.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@ConditionalOnProperty(name = "aws.archive.enabled", havingValue = "true")
public class AwsS3Config {

    @Bean
    public S3Client s3Client(@Value("${aws.region:us-east-1}") String region,
                             @Value("${aws.access-key:dummy}") String accessKey,
                             @Value("${aws.secret-key:dummy}") String secretKey,
                             @Value("${aws.s3.endpoint:}") String endpoint) {
        var builder = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)));
        if (!endpoint.isBlank()) {
            builder.endpointOverride(URI.create(endpoint)).forcePathStyle(true);
        }
        return builder.build();
    }
}
