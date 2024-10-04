package apply.lecture.application.member;

import apply.lecture.infrastructure.enrollment.EnrollRepositoryImpl;
import apply.lecture.infrastructure.lecture.Lecture;
import apply.lecture.infrastructure.member.Member;
import apply.lecture.infrastructure.member.MemberRepository;
import apply.lecture.infrastructure.member.MemberRepositoryImpl;
import apply.lecture.interfaces.common.CustomException;
import apply.lecture.interfaces.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    //유저 존재 여부 확인 메서드
    @Transactional(readOnly = true)
    public Member getFindMember(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
    }

}
