package apply.lecture.application.lecture;

import apply.lecture.interfaces.common.CustomException;
import apply.lecture.interfaces.common.ErrorCode;

public record LectureCriteria(

        Long memberId,
        String lectureTitle
) {
  public LectureCriteria toCommand(){

      if (memberId == null || memberId <= 0) {
          throw new CustomException(ErrorCode.USER_EMPTY_INPUT);
      }

      if (lectureTitle == null || lectureTitle.isEmpty()) {
          throw new CustomException(ErrorCode.LECTURE_EMPTY_INPUT);
      }

      return new LectureCriteria(memberId, lectureTitle);
  }
}
