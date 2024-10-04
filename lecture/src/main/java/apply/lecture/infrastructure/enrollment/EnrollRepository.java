package apply.lecture.infrastructure.enrollment;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface EnrollRepository extends JpaRepository<Enrollment,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Enrollment> findByMemberIdAndLectureId(Long memberId, Long lectureId);

   List<Enrollment> findAllByMemberId(Long memberId);
}
