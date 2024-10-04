package apply.lecture.infrastructure.lecture;

import apply.lecture.infrastructure.lecture.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture,Long> {

    //[STEP3] 동시성 대응 PESSIMISTICLOCK
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
   // Optional<Lecture> findByIdAndLectureId(Long id, Long lectureId);

    Optional<Lecture> findByTitle(String title);


    List<Lecture> findByCapacityGreaterThanEqual(Long capacity);

}
