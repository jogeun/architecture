package apply.lecture.infrastructure.enrollment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public abstract class EnrollRepositoryImpl implements EnrollRepository{

    private final EnrollRepository  repository;

   @Override
    public Enrollment save(Enrollment enrollment) {
        return repository.save(enrollment);
    }

    @Override
    public Optional<Enrollment> findById(Long userId) {

        return repository.findById(userId);
    }


}
