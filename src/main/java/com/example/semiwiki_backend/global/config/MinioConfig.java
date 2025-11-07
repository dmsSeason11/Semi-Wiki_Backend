package com.example.semiwiki_backend.global.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

  @Value("${minio.url}")
  private String minioUrl;

  @Value("${minio.access-key}")
  private String accessKey;

  @Value("${minio.secret-key}")
  private String secretKey;

  @Bean
  public MinioClient minioClient() {
    return MinioClient.builder()
        .endpoint(minioUrl)
        .credentials(accessKey, secretKey)
        .build();
  }

  @Bean
  CommandLineRunner initMinio(MinioClient minioClient) {
    return args -> {
      boolean found = minioClient.bucketExists(
          BucketExistsArgs.builder()
              .bucket("images")
              .build()
      );

      if (!found) {
        minioClient.makeBucket(
            MakeBucketArgs.builder()
                .bucket("images")
                .build()
        );
      }
    };
  }
}