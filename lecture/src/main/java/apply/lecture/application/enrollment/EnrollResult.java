package apply.lecture.application.enrollment;

import apply.lecture.infrastructure.enrollment.Enrollment;
import lombok.Builder;

import java.time.LocalDateTime;

// 특강 신청 완료 목록 조회용
@Builder
public record EnrollResult(
    Long memberId,

    String memberName,

    Long lectureId,
    String lectureTitle,
    String lecturer,

    LocalDateTime createAt
) {

    public static EnrollResult fromInfo(Enrollment info){
        return EnrollResult.builder()
            .memberId(info.getMember().getId())
                .memberName(info.getMember().getName())
                .lectureId(info.getId())
                .lectureTitle(info.getLecture().getTitle())
                .lecturer(info.getLecture().getLecturer())
                .createAt(info.getCreateAt())
            .build();
    }

}
