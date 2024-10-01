package apply.lecture.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//entity는 db 테이블과 맵핑되는 클래스, jpa 어노테이션 사용
@Entity
@NoArgsConstructor // 파라미터가 없는 디폴트 생성자 생성 , Users(){}
@AllArgsConstructor // 파라미터로 받는 생성자 생성, 필드를 한번에 초기화 User(id, name ) {this.~}
public class Member {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;


}
