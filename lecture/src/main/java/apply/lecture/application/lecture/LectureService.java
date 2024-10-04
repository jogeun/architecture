package apply.lecture.application.lecture;

import apply.lecture.infrastructure.lecture.LectureRepository;
import apply.lecture.infrastructure.lecture.Lecture;
import apply.lecture.infrastructure.lecture.LectureRepositoryImpl;
import apply.lecture.interfaces.common.CustomException;
import apply.lecture.interfaces.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    //특강 이름 조회
    @Transactional(readOnly = true)
    public Lecture getFindLecture(String lectureTitle){
        return
                lectureRepository.findByTitle(lectureTitle)
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_EXIST));
    }


}
