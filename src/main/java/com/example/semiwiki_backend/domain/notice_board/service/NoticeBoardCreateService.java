package com.example.semiwiki_backend.domain.notice_board.service;

import com.example.semiwiki_backend.domain.notice_board.dto.request.NoticeBoardCreateRequestDto;
import com.example.semiwiki_backend.domain.notice_board.dto.response.NoticeBoardDetailResponseDto;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoard;
import com.example.semiwiki_backend.domain.notice_board.entity.NoticeBoardHeader;
import com.example.semiwiki_backend.domain.notice_board.exception.NoCategoryException;
import com.example.semiwiki_backend.domain.notice_board.exception.NoHeaderException;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardHeaderRepository;
import com.example.semiwiki_backend.domain.notice_board.repository.NoticeBoardRepository;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.exception.UserNotFoundException;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.domain.user_notice_board.entity.UserNoticeBoard;
import com.example.semiwiki_backend.domain.user_notice_board.repository.UserNoticeBoardRepository;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


@Service
@RequiredArgsConstructor
public class NoticeBoardCreateService {
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;
    private final UserNoticeBoardRepository userNoticeBoardRepository;
    private final NoticeBoardHeaderRepository noticeBoardHeaderRepository;


    @Transactional
    public NoticeBoardDetailResponseDto createNoticeBoard(NoticeBoardCreateRequestDto dto, Authentication authentication)
    {
        //유저 아이디 jwt토큰에서 가져옴
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Integer userId = userDetails.getId();

        List<String> categories = dto.getCategories();
        if (categories == null || categories.isEmpty())
            throw new NoCategoryException();
        List<NoticeBoardHeader> headers = parseMarkdownToHeaders(dto.getContents());
        if(headers == null)
            throw new NoHeaderException();

        //noticeBoard 저장
        NoticeBoard noticeBoard = noticeBoardRepository.save(NoticeBoard.builder()
                .title(dto.getTitle())
                .categories(categories)
                .noticeBoardHeaders(headers)
                .build());

        //UserNoticeTable에 관계 저장
        UserNoticeBoard userNoticeBoard = UserNoticeBoard.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()))
                .noticeBoard(noticeBoard).build();
        userNoticeBoardRepository.save(userNoticeBoard);
        //noticeBoard에도 바뀐것 저장
        noticeBoard.addUserNotice(userNoticeBoard);

        //반환 위해서 유저 리스트 만듬
        List<User> users = new ArrayList<>();
        for (UserNoticeBoard userNotice : noticeBoard.getUsers())
            users.add(userNotice.getUser());

        //반환은 detail로
        return NoticeBoardDetailResponseDto.builder()
                .title(noticeBoard.getTitle())
                .noticeBoardHeaders(noticeBoard.getNoticeBoardHeaders())
                .createdAt(noticeBoard.getCreatedAt())
                .modficatedAt(noticeBoard.getModficatedAt())
                .users(users)
                .categories(noticeBoard.getCategories())
                .noticeBoardHeaders(noticeBoard.getNoticeBoardHeaders())
                .build();
    }

    private List<NoticeBoardHeader> parseMarkdownToHeaders(String contents) {
        List<NoticeBoardHeader> headers = new ArrayList<>();
        Stack<NoticeBoardHeader> stack = new Stack<>();
        int[] levelCount = new int[6];
        String headerContents = "";

        for (String line: contents.split("\n")) {
            int headerSize = 0;
            for (int i = 0; i < line.length() && line.charAt(i) == '#' && headerSize < 6; i++) {
                headerSize++;
            }
            boolean isValidHeader = headerSize > 0 &&
                    line.length() > headerSize &&
                    line.charAt(headerSize) == ' ';

            //헤더 있는경우 - 수정: isValidHeader 사용
            if(isValidHeader) {
                if (!stack.isEmpty()) {
                    stack.peek().setContentsInGenerate(headerContents);
                }
                headerContents = "";
                String title = line.substring(headerSize + 1).trim();
                levelCount[headerSize - 1]++;
                for(int i = headerSize; i < 6; i++)
                    levelCount[i] = 0;

                //1.1.1 이런거 저장하는 함수
                String headerNumber = "";
                for(int i = 0;i<headerSize;i++){
                    if(levelCount[i] == 0){
                        continue;
                    }
                    if(!headerNumber.isEmpty()) headerNumber += ".";
                    headerNumber += levelCount[i];
                }

                while (!stack.isEmpty() && stack.peek().getLevel() >= headerSize) { //지금 헤더가 앞 헤더보다 큰경우
                    stack.pop(); // 스택에서 제거
                }

                NoticeBoardHeader header = NoticeBoardHeader.builder()
                        .number(levelCount[headerSize-1])
                        .headerNumber(headerNumber)
                        .level(headerSize)
                        .contents("")
                        .title(title)
                        .children(new ArrayList<>())
                        .build();

                // noticeBoard가 있으면 헤더를 DB에 저장

                headerContents = "";
                if (!stack.isEmpty()) {
                    stack.peek().getChildren().add(header);
                } else {
                    headers.add(header);
                }
                stack.push(header);
            }
            else {
                headerContents += line + "\n";
            }
        }
        if (!stack.isEmpty()) {
            stack.peek().setContentsInGenerate(headerContents);
        }
        // 자식 헤더들도 모두 저장 (재귀적으로)
        saveChildrenHeaders(headers);
        return headers;
    }

    private void saveChildrenHeaders(List<NoticeBoardHeader> headers) {
        for (NoticeBoardHeader header : headers) {
            // 최상위 헤더들도 저장
            noticeBoardHeaderRepository.save(header);
            // 자식 헤더들이 있으면 재귀적으로 저장
            if (!header.getChildren().isEmpty()) {
                saveChildrenHeaders(header.getChildren());
            }
        }
    }
}