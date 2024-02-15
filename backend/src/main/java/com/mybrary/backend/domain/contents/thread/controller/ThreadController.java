package com.mybrary.backend.domain.contents.thread.controller;

import com.mybrary.backend.domain.contents.thread.dto.requestDto.ThreadPostDto;
import com.mybrary.backend.domain.contents.thread.dto.requestDto.ThreadUpdateDto;
import com.mybrary.backend.domain.contents.thread.service.ThreadService;
import com.mybrary.backend.domain.member.entity.Member;
import com.mybrary.backend.domain.member.service.MemberService;
import com.mybrary.backend.global.format.code.ApiResponse;
import com.mybrary.backend.global.format.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Tag(name = "Thread 컨트롤러", description = "Thread Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/thread")
public class ThreadController {

    private final ApiResponse response;
    private final ThreadService threadService;
    private final MemberService memberService;

    @Operation(summary = "쓰레드 생성", description = "쓰레드 생성")
    @PostMapping
    public ResponseEntity<?> createThread(@Parameter(hidden = true) Authentication authentication,
                                          @RequestBody ThreadPostDto threadPostDto) {

        return response.success(ResponseCode.THREAD_CREATED,
                                threadService.createThread(authentication.getName(), threadPostDto));
    }

    @Operation(summary = "메인홈 쓰레드 조회", description = "메인홈에서의 쓰레드 목록 조회")
    @GetMapping("/home")
    public ResponseEntity<?> getMainAllThread(
        @Parameter(hidden = true) Authentication authentication,
        @RequestParam(name = "page") int page) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        return response.success(ResponseCode.MAIN_THREAD_LIST_FETCHED,
                                threadService.getMainAllThread(myId, page));
    }

    @Operation(summary = "나의 쓰레드 조회", description = "나의 마이브러리 책상에서의 쓰레드 목록 조회")
    @GetMapping("/desk")
    public ResponseEntity<?> getMyAllThread(
        @Parameter(hidden = true) Authentication authentication,
        @PageableDefault(page = 0, size = 10) Pageable page) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        return response.success(ResponseCode.MY_THREAD_LIST_FETCHED,
                                threadService.getMyAllThread(myId, page));
    }

    @Operation(summary = "특정 회원의 쓰레드 조회", description = "특정 회원의 마이브러리 책상에서의 쓰레드 목록 조회")
    @GetMapping("/{id}/desk")
    public ResponseEntity<?> getOtherAllThread(
        @Parameter(hidden = true) Authentication authentication,
        @PathVariable(name = "id") Long memberId,
        @PageableDefault(page = 0, size = 10) Pageable page) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        return response.success(ResponseCode.OTHER_MEMBER_THREAD_LIST_FETCHED,
                                threadService.getOtherAllThread(authentication.getName(), memberId, page));
    }

    @Operation(summary = "쓰레드 수정", description = "쓰레드 수정")
    @PutMapping
    public ResponseEntity<?> updateThread(@Parameter(hidden = true) Authentication authentication,
                                          @RequestBody ThreadUpdateDto threadUpdateDto) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        return response.success(ResponseCode.THREAD_UPDATED, threadService.updateThread(myId, threadUpdateDto));
    }

    @Operation(summary = "쓰레드 삭제", description = "쓰레드 아이디를 통한 쓰레드 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteThread(@Parameter(hidden = true) Authentication authentication,
                                          @PathVariable(name = "id") Long threadId) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        return response.success(ResponseCode.THREAD_DELETED,
                                threadService.deleteThread(myId, threadId));
    }

    @Operation(summary = "쓰레드 단건 조회", description = "쓰레드 아이디를 통한 쓰레드 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getThread(
        @Parameter(hidden = true) Authentication authentication,
        @PathVariable(name = "id") Long threadId) {
        Member me = memberService.findMember(authentication.getName());
        Long myId = me.getId();
        return response.success(ResponseCode.CONTENTS_SEARCHED,
                                threadService.getThread(authentication.getName(), myId, threadId));

    }

//    @Operation(summary = "쓰레드 단건 조회", description = "쓰레드 아이디를 통한 쓰레드 조회")
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getThread(
//        @PathVariable(name = "id") Long threadId) {
//
//        MemberInfoDto member1 = new MemberInfoDto(1L, "wnsgh", "안녕하세요 최준호입니다", "123123");
//        MemberInfoDto member2 = new MemberInfoDto(2L, "aksrl", "안녕하세요 서만기입니다", "666666");
//        MemberInfoDto member3 = new MemberInfoDto(3L, "gPtjs", "안녕하세요 박혜선입니다", "145643");
//        MemberInfoDto member4 = new MemberInfoDto(4L, "thdud", "안녕하세요 최소영입니다", "000000");
//
//        MemberGetDto memberdetail1 = new MemberGetDto(1L, "wndgh@ssafy.com", "최준호", "wnsgh", "안녕하세요 최준호입니다", "123123", true, true);
//        MemberGetDto memberdetail2 = new MemberGetDto(2L, "aksrl@ssafy.com", "서만기", "aksrl", "안녕하세요 서만기입니다", "666666", true, true);
//        MemberGetDto memberdetail3 = new MemberGetDto(3L, "gPtjs@ssafy.com", "박헤선", "gPtjs", "안녕하세요 박혜선입니다", "145643", true, true);
//        MemberGetDto memberdetail4 = new MemberGetDto(4L, "thdud@ssafy.com", "최소영", "thdud", "안녕하세요 최소영입니다", "000000", true, true);
//
//        List<String> tagList = new ArrayList<>();
//        tagList.add("여행");
//        tagList.add("부산");
//        tagList.add("해운대바다");
//        tagList.add("광어연어우럭");
//        tagList.add("광안리해수욕장");
//        tagList.add("부산여행");
//        tagList.add("대전");
//
//        List<MemberGetDto> mentionList = new ArrayList<>();
//        mentionList.add(memberdetail1);
//        mentionList.add(memberdetail2);
//        mentionList.add(memberdetail3);
//        mentionList.add(memberdetail4);
//
//
//        HomePaperGetDto paper1 = new HomePaperGetDto(1L, "2023-09-06", 8, "내용1", "내용2", "123", "123", "123", "123", "000", "000", "000", "000", tagList, mentionList, 1344563, 10, 34213, true, true, true);
//        HomePaperGetDto paper2 = new HomePaperGetDto(2L, "2023-09-06", 2, "내용234332", "두번째페이지", "123", "123", "123", "123", "000", "000", "000", "000", tagList, mentionList, 1344563, 10, 34213, true, true, true);
//        HomePaperGetDto paper3 = new HomePaperGetDto(3L, "2023-09-06", 4, "23423423", "세번째페이지", "123", "123", "123", "123", "000", "000", "000", "000", tagList, mentionList, 1344563, 10, 34213, true, true, true);
//        List<HomePaperGetDto> paperList = new ArrayList<>();
//        paperList.add(paper1);
//        paperList.add(paper2);
//        paperList.add(paper3);
//
//        HomeThreadGetDto thread = new HomeThreadGetDto(1L, member1, true, false, paperList);
//
//        return response.success(ResponseCode.THREAD_FETCHED, thread);
//    }
//
//
//    @Operation(summary = "메인홈 쓰레드 조회", description = "메인홈에서의 쓰레드 목록 조회")
//    @GetMapping("/home")
//    public ResponseEntity<?> getMainAllThread(
//        @PageableDefault(page = 0, size = 10) Pageable page) {
//
//        MemberInfoDto member1 = new MemberInfoDto(1L, "wnsgh", "안녕하세요 최준호입니다", "123123");
//        MemberInfoDto member2 = new MemberInfoDto(2L, "aksrl", "안녕하세요 서만기입니다", "666666");
//        MemberInfoDto member3 = new MemberInfoDto(3L, "gPtjs", "안녕하세요 박혜선입니다", "145643");
//        MemberInfoDto member4 = new MemberInfoDto(4L, "thdud", "안녕하세요 최소영입니다", "000000");
//
//        MemberGetDto memberdetail1 = new MemberGetDto(1L, "wndgh@ssafy.com", "최준호", "wnsgh", "안녕하세요 최준호입니다", "123123", true, true);
//        MemberGetDto memberdetail2 = new MemberGetDto(2L, "aksrl@ssafy.com", "서만기", "aksrl", "안녕하세요 서만기입니다", "666666", true, true);
//        MemberGetDto memberdetail3 = new MemberGetDto(3L, "gPtjs@ssafy.com", "박헤선", "gPtjs", "안녕하세요 박혜선입니다", "145643", true, true);
//        MemberGetDto memberdetail4 = new MemberGetDto(4L, "thdud@ssafy.com", "최소영", "thdud", "안녕하세요 최소영입니다", "000000", true, true);
//
//        List<String> tagList1 = new ArrayList<>();
//        tagList1.add("여행");
//        tagList1.add("부산");
//        tagList1.add("해운대바다");
//        tagList1.add("광어연어우럭");
//        tagList1.add("광안리해수욕장");
//        tagList1.add("부산여행");
//        tagList1.add("대전");
//
//        List<String> tagList2 = new ArrayList<>();
//        tagList2.add("태그");
//        tagList2.add("#####");
//
//        List<MemberGetDto> mentionList1 = new ArrayList<>();
//        mentionList1.add(memberdetail1);
//        mentionList1.add(memberdetail2);
//
//        List<MemberGetDto> mentionList2 = new ArrayList<>();
//        mentionList2.add(memberdetail3);
//        mentionList2.add(memberdetail4);
//
//
//        HomePaperGetDto paper1 = new HomePaperGetDto(1L, "2023-09-06", 8, "내용1", "내용2", "123", "123", "123", "123", "000", "000", "000", "000", tagList1, mentionList1, 1344563, 10, 34213, true, true, true);
//        HomePaperGetDto paper2 = new HomePaperGetDto(2L, "2023-09-06", 2, "내용1", "내용2", "123", "123", "123", "123", "000", "000", "000", "000", tagList2, mentionList2, 874647, 2, 1345, false, true, true);
//        List<HomePaperGetDto> paperList = new ArrayList<>();
//        paperList.add(paper1);
//        paperList.add(paper2);
//
//        HomeThreadGetDto thread1 = new HomeThreadGetDto(1L, member1, false, true, paperList);
//        HomeThreadGetDto thread2 = new HomeThreadGetDto(2L, member3, false, false, paperList);
//        HomeThreadGetDto thread3 = new HomeThreadGetDto(3L, member2, true, false, paperList);
//        HomeThreadGetDto thread4 = new HomeThreadGetDto(4L, member2, true, false, paperList);
//        HomeThreadGetDto thread5 = new HomeThreadGetDto(5L, member4, false, true, paperList);
//
//        List<HomeThreadGetDto> list = new ArrayList<>();
//        list.add(thread1);
//        list.add(thread2);
//        list.add(thread3);
//        list.add(thread4);
//        list.add(thread5);
//
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("threadList", list);
//        map.put("page", page);
//
//        return response.success(ResponseCode.MAIN_THREAD_LIST_FETCHED, map);
//    }
//
//    @Operation(summary = "나의 쓰레드 조회", description = "나의 마이브러리 책상에서의 쓰레드 목록 조회")
//    @GetMapping("/desk")
//    public ResponseEntity<?> getMyAllThread(@PageableDefault(page = 0, size = 10) Pageable page) {
//
//
//        MemberInfoDto member1 = new MemberInfoDto(1L, "wnsgh", "안녕하세요 최준호입니다", "123123");
//        MemberInfoDto member2 = new MemberInfoDto(2L, "aksrl", "안녕하세요 서만기입니다", "666666");
//        MemberInfoDto member3 = new MemberInfoDto(3L, "gPtjs", "안녕하세요 박혜선입니다", "145643");
//        MemberInfoDto member4 = new MemberInfoDto(4L, "thdud", "안녕하세요 최소영입니다", "000000");
//
//        MemberGetDto memberdetail1 = new MemberGetDto(1L, "wndgh@ssafy.com", "최준호", "wnsgh", "안녕하세요 최준호입니다", "123123", true, true);
//        MemberGetDto memberdetail2 = new MemberGetDto(2L, "aksrl@ssafy.com", "서만기", "aksrl", "안녕하세요 서만기입니다", "666666", true, true);
//        MemberGetDto memberdetail3 = new MemberGetDto(3L, "gPtjs@ssafy.com", "박헤선", "gPtjs", "안녕하세요 박혜선입니다", "145643", true, true);
//        MemberGetDto memberdetail4 = new MemberGetDto(4L, "thdud@ssafy.com", "최소영", "thdud", "안녕하세요 최소영입니다", "000000", true, true);
//
//        List<String> tagList1 = new ArrayList<>();
//        tagList1.add("여행");
//        tagList1.add("부산");
//        tagList1.add("해운대바다");
//        tagList1.add("광어연어우럭");
//        tagList1.add("광안리해수욕장");
//        tagList1.add("부산여행");
//        tagList1.add("대전");
//
//        List<String> tagList2 = new ArrayList<>();
//        tagList2.add("태그");
//        tagList2.add("#####");
//
//        List<MemberGetDto> mentionList1 = new ArrayList<>();
//        mentionList1.add(memberdetail1);
//        mentionList1.add(memberdetail2);
//
//        List<MemberGetDto> mentionList2 = new ArrayList<>();
//        mentionList2.add(memberdetail3);
//        mentionList2.add(memberdetail4);
//
//
//        PaperGetDto paper1 = new PaperGetDto(1L, "2023-09-06", 8, "내용1", "내용2", "123", "123", "123", "123", "000", "000", "000", "000", tagList1, mentionList1, 1344563, 10, 34213, true);
//        PaperGetDto paper2 = new PaperGetDto(2L, "2023-09-06", 2, "내용1", "내용2", "123", "123", "123", "123", "000", "000", "000", "000", tagList2, mentionList2, 874647, 2, 1345, false);
//
//        List<PaperGetDto> paperList = new ArrayList<>();
//        paperList.add(paper1);
//        paperList.add(paper2);
//
//        ThreadGetDto thread1 = new ThreadGetDto(1L, member1, paperList);
//        ThreadGetDto thread2 = new ThreadGetDto(2L, member3, paperList);
//        ThreadGetDto thread3 = new ThreadGetDto(3L, member2, paperList);
//        ThreadGetDto thread4 = new ThreadGetDto(4L, member2, paperList);
//        ThreadGetDto thread5 = new ThreadGetDto(5L, member4, paperList);
//
//        List<ThreadGetDto> list = new ArrayList<>();
//        list.add(thread1);
//        list.add(thread2);
//        list.add(thread3);
//        list.add(thread4);
//        list.add(thread5);
//
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("threadList", list);
//        map.put("page", page);
//
//        return response.success(ResponseCode.MY_THREAD_LIST_FETCHED, map);
//    }
//
//    @Operation(summary = "특정 회원의 쓰레드 조회", description = "회원 아이디를 통한 특정 회원의 마이브러리 책상에서의 쓰레드 목록 조회")
//    @GetMapping("/{id}/desk")
//    public ResponseEntity<?> getOtherAllThread(@PathVariable(name = "id") Long mybraryId,
//                                                @PageableDefault(page = 0, size = 10) Pageable page) {
//
//        MemberInfoDto member1 = new MemberInfoDto(1L, "wnsgh", "안녕하세요 최준호입니다", "123123");
//        MemberInfoDto member2 = new MemberInfoDto(2L, "aksrl", "안녕하세요 서만기입니다", "666666");
//        MemberInfoDto member3 = new MemberInfoDto(3L, "gPtjs", "안녕하세요 박혜선입니다", "145643");
//        MemberInfoDto member4 = new MemberInfoDto(4L, "thdud", "안녕하세요 최소영입니다", "000000");
//
//        MemberGetDto memberdetail1 = new MemberGetDto(1L, "wndgh@ssafy.com", "최준호", "wnsgh", "안녕하세요 최준호입니다", "123123", true, true);
//        MemberGetDto memberdetail2 = new MemberGetDto(2L, "aksrl@ssafy.com", "서만기", "aksrl", "안녕하세요 서만기입니다", "666666", true, true);
//        MemberGetDto memberdetail3 = new MemberGetDto(3L, "gPtjs@ssafy.com", "박헤선", "gPtjs", "안녕하세요 박혜선입니다", "145643", true, true);
//        MemberGetDto memberdetail4 = new MemberGetDto(4L, "thdud@ssafy.com", "최소영", "thdud", "안녕하세요 최소영입니다", "000000", true, true);
//
//        List<String> tagList1 = new ArrayList<>();
//        tagList1.add("여행");
//        tagList1.add("부산");
//        tagList1.add("해운대바다");
//        tagList1.add("광어연어우럭");
//        tagList1.add("광안리해수욕장");
//        tagList1.add("부산여행");
//        tagList1.add("대전");
//
//        List<String> tagList2 = new ArrayList<>();
//        tagList2.add("태그");
//        tagList2.add("#####");
//
//        List<MemberGetDto> mentionList1 = new ArrayList<>();
//        mentionList1.add(memberdetail1);
//        mentionList1.add(memberdetail2);
//
//        List<MemberGetDto> mentionList2 = new ArrayList<>();
//        mentionList2.add(memberdetail3);
//        mentionList2.add(memberdetail4);
//
//
//        HomePaperGetDto paper1 = new HomePaperGetDto(1L, "2023-09-06", 8, "내용1", "내용2", "123", "123", "123", "123", "000", "000", "000", "000", tagList1, mentionList1, 1344563, 10, 34213, true, true, true);
//        HomePaperGetDto paper2 = new HomePaperGetDto(2L, "2023-09-06", 2, "내용1", "내용2", "123", "123", "123", "123", "000", "000", "000", "000", tagList2, mentionList2, 874647, 2, 1345, false, true, true);
//        List<HomePaperGetDto> paperList = new ArrayList<>();
//        paperList.add(paper1);
//        paperList.add(paper2);
//
//        HomeThreadGetDto thread1 = new HomeThreadGetDto(1L, member1, false, true, paperList);
//        HomeThreadGetDto thread2 = new HomeThreadGetDto(2L, member3, false, false, paperList);
//        HomeThreadGetDto thread3 = new HomeThreadGetDto(3L, member2, true, false, paperList);
//        HomeThreadGetDto thread4 = new HomeThreadGetDto(4L, member2, true, false, paperList);
//        HomeThreadGetDto thread5 = new HomeThreadGetDto(5L, member4, false, true, paperList);
//
//        List<HomeThreadGetDto> list = new ArrayList<>();
//        list.add(thread1);
//        list.add(thread2);
//        list.add(thread3);
//        list.add(thread4);
//        list.add(thread5);
//
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("threadList", list);
//        map.put("page", page);
//
//        return response.success(ResponseCode.OTHER_MEMBER_THREAD_LIST_FETCHED, map);
//    }


}
