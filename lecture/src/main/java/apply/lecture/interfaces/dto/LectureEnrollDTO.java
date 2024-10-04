package apply.lecture.interfaces.dto;

import apply.lecture.application.lecture.LectureCriteria;
import apply.lecture.application.lecture.LectureResult;

public class LectureEnrollDTO {
    public record Request(Long memberId, String lectureTitle){
        public LectureCriteria chkCriteria(){
            return new LectureCriteria(memberId, lectureTitle);
        }

    }
    public record Response(LectureResult lectureResult){

    }
}
