package apply.lecture.application;

import apply.lecture.application.enrollment.EnrollResult;
import apply.lecture.application.enrollment.EnrollService;
import apply.lecture.application.lecture.LectureCriteria;
import apply.lecture.application.lecture.LectureResult;
import apply.lecture.application.lecture.LectureService;
import apply.lecture.application.member.MemberService;
import apply.lecture.infrastructure.enrollment.Enrollment;
import apply.lecture.infrastructure.lecture.Lecture;
import apply.lecture.infrastructure.member.Member;
import apply.lecture.interfaces.common.CustomException;
import apply.lecture.interfaces.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EnrollLectureFacade {

    private final LectureService lectureService;
    private final EnrollService enrollService;
    private final MemberService memberService;



    @Transactional
    public LectureResult enroll(LectureCriteria criteria) {


        //유저 존재유무 확인
        Member member = memberService.getFindMember(criteria.memberId());

        //강의 존재유무 확인
        Lecture lecture = lectureService.getFindLecture(criteria.lectureTitle());

        // 강의 중복신청 여부 확인
        Optional<Enrollment> enrollment = enrollService.getFindSameEnroll(member.getId(), lecture.getId());

        Enrollment saveEnroll = null;

        if (enrollment.isPresent()) {
            throw new CustomException(ErrorCode.LECTURE_ALREADY_APPLIED);
        } else {
            //잔여석 -1 제거
            lecture.reduceCapacity();
            //등록목록 저장
             enrollService.saveEnroll(member, lecture);

        }

        return new LectureResult(lecture.getTitle(), lecture.getLecturer(), member.getName());
    }


    public List<EnrollResult> getEnrollLsit(Long memberId){
        return enrollService.getFindEnroll(memberId).stream().map(EnrollResult::fromInfo).toList();
    }

}
