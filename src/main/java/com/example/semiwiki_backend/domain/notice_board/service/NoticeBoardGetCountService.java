package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardCountRequestDto;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeBoardGetCountService {
    private final NoticeBoardRepository noticeBoardRepository;

    public Long noticeBoardGetCount(NoticeBoardCountRequestDto requestDto) {
        final String title = requestDto.getTitle();
        final List<String> categories = requestDto.getCategories();
        if(title == null) {
            if (categories == null)
                return noticeBoardRepository.count();

            return noticeBoardRepository.countByCategoriesAllMatch(categories, categories.size());
        }
        if(categories == null)
            return noticeBoardRepository.countByTitleContainingIgnoreCase(title);

        return noticeBoardRepository.countByTitleContainingAndCategoriesAllMatch(title,categories, categories.size());
    }
}
