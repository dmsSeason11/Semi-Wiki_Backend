package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.entity.Image;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.exception.NoticeBoardNotFoundException;
import com.example.semiwiki_backend.domain.notice_board.repository.ImageRepository;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
@RequiredArgsConstructor
public class HtmlImageExtractService {

  private final ImageRepository imageRepository;
  private final NoticeBoardRepository noticeBoardRepository;

  public List<String> extractImageUrls(String html) {
    Pattern pattern = Pattern.compile("<img[^>]+src=\"([^\"]+)\"");
    Matcher matcher = pattern.matcher(html);

    List<String> urls = new ArrayList<>();
    while (matcher.find()) {
      urls.add(matcher.group(1));
    }
    return urls;

  }

  public void assignImagesToNoticeBoard(Integer noticeBoardId, List<String> imageUrls) {
    NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
        .orElseThrow(NoticeBoardNotFoundException::new);

    List<Image> images = imageRepository.findByUrlIn(imageUrls);
    images.forEach(img -> img.assignTo(noticeBoard));
  }
}
