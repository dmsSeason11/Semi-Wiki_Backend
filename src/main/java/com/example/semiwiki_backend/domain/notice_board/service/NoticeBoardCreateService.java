package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardCreateRequestDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoardHeader;
import com.example.semiwiki_backend.domain.notice_board.exception.*;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardHeaderRepository;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;



@Service
@RequiredArgsConstructor
public class NoticeBoardCreateService {
    private final static Logger logger = LoggerFactory.getLogger(NoticeBoardCreateService.class);
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;
    private final UserNoticeBoardRepository userNoticeBoardRepository;
    private final NoticeBoardHeaderRepository noticeBoardHeaderRepository;
    private final HtmlImageExtractService htmlImageExtractService;


    @Transactional
    public NoticeBoardDetailResponseDto createNoticeBoard(NoticeBoardCreateRequestDto dto, Authentication authentication)
    {
        //유저 아이디 jwt토큰에서 가져옴
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow(UserNotFoundException::new);

        //타이틀 빈경우 예외
        if(dto.getTitle() == null || dto.getTitle().trim().equals(""))
            throw new NoTitleException();
        else if(noticeBoardRepository.existsByTitle(dto.getTitle()))
            throw new DuplicateTitleException();


        List<String> categories = dto.getCategories();
        if (categories == null || categories.isEmpty())
            throw new NoCategoryException();
        else if(categories.size() > 3) //카테고리 최대 개수 3개 설정
            throw new OverRunCategoryException();

        List<NoticeBoardHeader> headers = parseMarkdownToHeaders(dto.getContents()); //헤더가 없는경우 오류 발생
        if(headers == null || headers.isEmpty())
            throw new NoHeaderException();

        //noticeBoard 저장
        NoticeBoard noticeBoard = noticeBoardRepository.save(NoticeBoard.builder()
                .title(dto.getTitle())
                .categories(categories)
                .noticeBoardHeaders(headers)
                        .contents(dto.getContents())
                .build());

        //UserNoticeTable에 관계 저장

        UserNoticeBoard userNoticeBoard = UserNoticeBoard.builder()
                .user(user)
                .noticeBoard(noticeBoard).build();
        userNoticeBoardRepository.save(userNoticeBoard);
        //noticeBoard에도 바뀐것 저장
        noticeBoard.addUserNotice(userNoticeBoard);

        //반환 위해서 유저 리스트 만듬
        List<User> users = new ArrayList<>();
        for (UserNoticeBoard userNotice : noticeBoard.getUsers())
            users.add(userNotice.getUser());

        //HTML에서 이미지 URL 추출
        List<String> imageUrls = htmlImageExtractService.extractImageUrls(dto.getContents());

        //이미지 매핑
        htmlImageExtractService.assignImagesToNoticeBoard(noticeBoard.getId(), imageUrls);


        logger.info("\nuser : {}\ntitle : {}\nboard : \n{}\n", user.getAccountId() ,noticeBoard.getTitle(),noticeBoard.getContents());

        //반환은 detail로
        return NoticeBoardDetailResponseDto.builder()
                .title(noticeBoard.getTitle())
                .noticeBoardHeaders(noticeBoard.getNoticeBoardHeaders())
                .createdAt(noticeBoard.getCreatedAt())
                .contents(noticeBoard.getContents())
                .modficatedAt(noticeBoard.getModficatedAt())
                .users(users)
                .categories(noticeBoard.getCategories())
                .build();
    }

    private List<NoticeBoardHeader> parseMarkdownToHeaders(String contents) {
        List<NoticeBoardHeader> headers = new ArrayList<>();
        Stack<NoticeBoardHeader> stack = new Stack<>();
        int[] levelCount = new int[6];

        Document doc = Jsoup.parse(contents);
        Elements elements = doc.select("h1, h2, h3, h4, h5, h6");

        for (Element h : elements) {
            int level = Integer.parseInt(h.tagName().substring(1)); // h1~h6 -> 1~6
            levelCount[level - 1]++;
            for (int i = level; i < 6; i++) levelCount[i] = 0;

            // 1.1.1 식의 headerNumber 생성
            String headerNumber = "";
            for (int i = 0; i < level; i++) {
                if (levelCount[i] == 0) continue;
                if (!headerNumber.isEmpty()) headerNumber += ".";
                headerNumber += levelCount[i];
            }

            // 부모 헤더 찾기
            while (!stack.isEmpty() && stack.peek().getLevel() >= level) {
                stack.pop();
            }
            NoticeBoardHeader parentHeader = stack.isEmpty() ? null : stack.peek();

            // 본문 수집 (다음 헤더 전까지)
            StringBuilder contentsBuilder = new StringBuilder();
            Element next = h.nextElementSibling();
            while (next != null && !next.tagName().matches("h[1-6]")) {
                contentsBuilder.append(next.outerHtml()); // outerHtml()은 태그 포함 전체 HTML
                next = next.nextElementSibling();
            }

            // 엔티티 생성 시
            NoticeBoardHeader header = NoticeBoardHeader.builder()
                    .number(levelCount[level - 1])
                    .headerNumber(headerNumber)
                    .level(level)
                    .title(h.text()) // 헤더 텍스트만 제목
                    .contents(contentsBuilder.toString().trim()) // 본문은 HTML 그대로
                    .children(new ArrayList<>())
                    .build();

            // 부모-자식 관계 세팅
            if (parentHeader != null) {
                parentHeader.getChildren().add(header);
            } else {
                headers.add(header); // 최상위 헤더
            }

            stack.push(header);
        }

        saveChildrenHeaders(headers);
        return headers;
    }

    private void saveChildrenHeaders(List<NoticeBoardHeader> headers) {
        for (NoticeBoardHeader header : headers) {
            noticeBoardHeaderRepository.save(header);
            if (!header.getChildren().isEmpty()) {
                saveChildrenHeaders(header.getChildren());
            }
        }
    }
}