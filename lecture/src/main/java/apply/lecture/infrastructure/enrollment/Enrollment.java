package apply.lecture.infrastructure.enrollment;


import apply.lecture.infrastructure.member.Member;
import apply.lecture.infrastructure.lecture.Lecture;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enrollment {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "enroll_id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime createAt;

    @Builder
    public Enrollment(Member member, Lecture lecture){
        this.member = member;
        this.lecture = lecture;
        this.createAt = LocalDateTime.now();
    }



}
