package apply.lecture.infrastructure.member;

import apply.lecture.infrastructure.enrollment.EnrollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public abstract class MemberRepositoryImpl implements MemberRepository{
    private final MemberRepository  repository;

    @Override
    public Optional<Member> findById(Long id) {
        return repository.findById(id);
    }


}
