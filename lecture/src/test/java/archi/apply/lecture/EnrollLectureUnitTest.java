package archi.apply.lecture;

import apply.lecture.LectureApplication;
import apply.lecture.application.EnrollLectureFacade;
import apply.lecture.application.enrollment.EnrollService;
import apply.lecture.application.lecture.LectureCriteria;
import apply.lecture.application.lecture.LectureService;
import apply.lecture.application.member.MemberService;
import apply.lecture.infrastructure.enrollment.EnrollRepository;
import apply.lecture.infrastructure.enrollment.Enrollment;
import apply.lecture.infrastructure.lecture.Lecture;
import apply.lecture.infrastructure.lecture.LectureRepository;
import apply.lecture.infrastructure.member.Member;
import apply.lecture.infrastructure.member.MemberRepository;
import apply.lecture.interfaces.common.CustomException;
import apply.lecture.interfaces.common.ErrorCode;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = LectureApplication.class)
class EnrollLectureUnitTest {
    ;
    @Autowired
    private LectureService lectureService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private EnrollService enrollService;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EnrollRepository enrollRepository;

    @Test
    @DisplayName("[실패] 파라미터 값 검사 - 유저아이디")
    void fail_paramNullUserId(){
        //given
        String errorMsg = "";
        //when
        try{
            LectureCriteria lectureCriteria = new LectureCriteria(-1l,"MATH").toCommand();
        }catch (CustomException e ){
            errorMsg = e.getMessage();
        }
        //then
        assertEquals("신청 유저아이디가 없습니다.",errorMsg);

    }

    @Test
    @DisplayName("[실패] 파라미터 값 검사 - 강의명")
    void fail_paramNullLectureTitle(){
        //given
        String errorMsg = "";
        //when
        try{
            LectureCriteria lectureCriteria = new LectureCriteria(1l,"").toCommand();
        }catch (CustomException e ){
            errorMsg = e.getMessage();
        }
        //then
        assertEquals("신청 강의명이 없습니다.",errorMsg);

    }

    @Test
    @DisplayName("[실패] 없는 강의 조회시")
    void fail_NoSuchLecture(){
        //given
        String errorMsg = "";
        //when
        try{
            lectureService.getFindLecture("없는 강의");
        }catch (CustomException e ){
            errorMsg = e.getMessage();
        }
        //then
        assertEquals("특강이 존재하지 않습니다.",errorMsg);

    }

    @Test
    @DisplayName("[실패] 없는 멤버 조회시")
    void fail_NoSuchMember(){
        //given
        String errorMsg = "";
        //when
        try{
            memberService.getFindMember(99L);
        }catch (CustomException e ){
            errorMsg = e.getMessage();
        }
        //then
        assertEquals("유저가 존재하지 않습니다.",errorMsg);

    }

    @Test
    @DisplayName("[성공] 특강 신청")
    void success_enrollLecture(){
        //given
        Member member = memberService.getFindMember(1L);
        Lecture lecture = lectureService.getFindLecture("MATH");
        //when
        enrollService.saveEnroll(member,lecture);
        List<Enrollment> enrollments = enrollService.getFindEnroll(member.getId());
        //then
        assertThat(enrollments.get(0).getId()).isEqualTo(1L);
        assertThat(enrollments.get(0).getMember().getId()).isEqualTo(1L);
        assertThat(enrollments.get(0).getMember().getName()).isEqualTo("test1");
        assertThat(enrollments.get(0).getLecture().getId()).isEqualTo(1L);
        assertThat(enrollments.get(0).getLecture().getTitle()).isEqualTo("MATH");
        assertThat(enrollments.get(0).getLecture().getLecturer()).isEqualTo("KIM");

    }

    @Test
    @DisplayName("[실패] 특강 중복 신청")
    void fail_enrollDupleLecture() throws Exception{
        //given
        String errorMsg = "";
        Member member = memberService.getFindMember(1L);
        Lecture lecture = lectureService.getFindLecture("MATH"); // facade에 검증 로직을 만들고 서비스에는 만들지 않았는데, 서비스도 추가해줘야하나요? 아니면 유닛테스트를 위한 검토정도인가요?
        enrollService.saveEnroll(member,lecture);
        //when
        try{
            enrollService.saveEnroll(member,lecture);
        }catch (CustomException e){
            errorMsg = e.getMessage();
        }
        //then
        assertEquals("중복 신청입니다.",errorMsg);
    }

    @Test
    @DisplayName("[실패] 신청인원 이상 신청할 때")
    void fail_OverCapacityLecture(){
        //given
        String errorMsg = "";

        Member member = memberService.getFindMember(5L);
        Lecture lecture = lectureService.getFindLecture("COMPUTER");
        for(int i = 0; i < 30; i++){
            lecture.reduceCapacity();
        }
        //when
        try{
            enrollService.saveEnroll(member,lecture);
        }catch (CustomException e ){
            errorMsg = e.getMessage();
        }
        //then
        assertEquals("특강 수강 인원을 초과했습니다.",errorMsg);

    }

    @Test
    @DisplayName("[성공]특강 등록 목록 조회 - 유저기준")
    void succes_enrollLectureListById(){
        //given
        Member member = memberService.getFindMember(1L);
        Lecture lecture = lectureService.getFindLecture("MATH");
        Lecture lecture2 = lectureService.getFindLecture("COMPUTER");
        //when
        enrollService.saveEnroll(member,lecture);
        enrollService.saveEnroll(member,lecture2);
        List<Enrollment> enrollments = enrollService.getFindEnroll(member.getId());
        //then
        assertThat(enrollments.get(0).getId()).isEqualTo(1L);
        assertThat(enrollments.get(0).getMember().getId()).isEqualTo(1L);
        assertThat(enrollments.get(0).getMember().getName()).isEqualTo("test1");
        assertThat(enrollments.get(0).getLecture().getId()).isEqualTo(1L);
        assertThat(enrollments.get(0).getLecture().getTitle()).isEqualTo("MATH");
        assertThat(enrollments.get(0).getLecture().getLecturer()).isEqualTo("KIM");
        assertThat(enrollments.get(1).getId()).isEqualTo(2L);
        assertThat(enrollments.get(1).getMember().getId()).isEqualTo(1L);
        assertThat(enrollments.get(1).getMember().getName()).isEqualTo("test1");
        assertThat(enrollments.get(1).getLecture().getId()).isEqualTo(2L);
        assertThat(enrollments.get(1).getLecture().getTitle()).isEqualTo("COMPUTER");
        assertThat(enrollments.get(1).getLecture().getLecturer()).isEqualTo("LEE");
    }
}
