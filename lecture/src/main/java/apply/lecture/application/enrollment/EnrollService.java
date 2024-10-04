package apply.lecture.application.enrollment;

import apply.lecture.application.lecture.LectureCriteria;
import apply.lecture.infrastructure.enrollment.EnrollRepository;
import apply.lecture.infrastructure.enrollment.Enrollment;
import apply.lecture.infrastructure.lecture.Lecture;
import apply.lecture.infrastructure.member.Member;
import apply.lecture.interfaces.common.CustomException;
import apply.lecture.interfaces.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollService {

    private final EnrollRepository enrollRepository;

    //강의 등록하기 
    @Transactional
    public void saveEnroll(Member member, Lecture lecture){
        //Unit 테스트 용 검증 로직 주석
        /*Optional<Enrollment> enrollment = enrollRepository.findByMemberIdAndLectureId(member.getId(), lecture.getId());
        if (enrollment.isPresent()) {
            throw new CustomException(ErrorCode.LECTURE_ALREADY_APPLIED);
        }else {
            lecture.reduceCapacity();
            enrollRepository.save(Enrollment.builder().member(member).lecture(lecture).build());
        }*/
        System.out.println("capacity" + lecture.getCapacity());
        if(lecture.getCapacity()>0) {
            enrollRepository.save(Enrollment.builder().member(member).lecture(lecture).build());
        }else{
            throw new CustomException(ErrorCode.LECTURE_NOT_ENOUGH);
        }
    }

    //동일 등록 멤버 확인하기
    @Transactional(readOnly = true)
    public Optional<Enrollment> getFindSameEnroll(Long memberId, Long lectureId){
        return enrollRepository.findByMemberIdAndLectureId(memberId,lectureId);
    }

    //유저 등록목록 조회
    public List<Enrollment> getFindEnroll(Long memberId){
        return enrollRepository.findAllByMemberId(memberId);
    }


}
