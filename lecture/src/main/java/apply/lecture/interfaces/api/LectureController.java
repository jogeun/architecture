package apply.lecture.interfaces.api;

import apply.lecture.application.EnrollLectureFacade;
import apply.lecture.application.enrollment.EnrollResult;
import apply.lecture.interfaces.dto.LectureEnrollDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {

   private final EnrollLectureFacade enrollLectureFacade;

   /* @GetMapping ("/{id}")
    public ResponseEntity<Lecture> lec(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(enrollLectureFacade.enroll(id,null));
    }
*/

    //특강 신청
    @PostMapping ("/enroll")
    public ResponseEntity<LectureEnrollDTO.Response> enrollLecture(@RequestBody LectureEnrollDTO.Request request){
        final var result = enrollLectureFacade.enroll(request.chkCriteria());
        return ResponseEntity.ok(new LectureEnrollDTO.Response(result));
    }

    //특강 신청 완료 목록 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<EnrollResult>> completedList(
            @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(enrollLectureFacade.getEnrollLsit(userId));
    }
}
