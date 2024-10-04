package apply.lecture.infrastructure.member;

import apply.lecture.infrastructure.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//entity는 db 테이블과 맵핑되는 클래스, jpa 어노테이션 사용
@Entity
@Getter
@NoArgsConstructor// 파라미터가 없는 디폴트 생성자 생성 , Users(){}
public class Member {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String name;


    //@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
   // private List<Enrollment> enrollments = new ArrayList<>();

    @Builder
    public Member(String name) {
        this.name = name;
    }
}
