package apply.lecture.infrastructure.lecture;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public abstract class LectureRepositoryImpl implements LectureRepository{
    @Autowired
    private final LectureRepository  repository;

    @Override
    public Optional<Lecture> findByTitle(String name) {
        return repository.findByTitle(name);
    }
    @Override
    public Optional<Lecture> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Lecture> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Lecture> findByCapacityGreaterThanEqual(Long capcity) {
        capcity = 1L;
        return repository.findByCapacityGreaterThanEqual(capcity);
    }
}
