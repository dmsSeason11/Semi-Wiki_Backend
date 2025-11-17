package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.entity.Image;
import com.example.semiwiki_backend.domain.notice_board.repository.ImageRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.notice_board.exception.ImageUploadIOException;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

  private final MinioClient minioClient;
  private final ImageRepository imageRepository;
  private final UserRepository userRepository;

  @Value("${minio.ip}")
  private String ip;
  @Value("${minio.bucket}")
  private String bucket;
  @Transactional
  public String UploadImage(MultipartFile file, Authentication authentication){

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    User user = userRepository.findById(userDetails.getId()).orElseThrow(UserNotFoundException::new);

    try {
      String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

      minioClient.putObject(
          PutObjectArgs.builder()
          .bucket(bucket)
          .object(fileName)
          .stream(file.getInputStream(), file.getSize(), -1)
          .contentType(file.getContentType())
          .build());

      String url = "http://" + ip + ":9000/" + bucket + "/" + fileName;

      imageRepository.save(Image.builder()
          .fileName(fileName)
          .noticeBoard(null)
          .user(user)
          .createdAt(LocalDateTime.now())
          .url(url)
          .build());

      return url;

    }catch (Exception e){
      throw new ImageUploadIOException();
    }
  }

}
