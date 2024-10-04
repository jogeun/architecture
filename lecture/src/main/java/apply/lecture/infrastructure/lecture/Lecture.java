package apply.lecture.infrastructure.lecture;

import apply.lecture.interfaces.common.CustomException;
import apply.lecture.interfaces.common.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Lecture {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    private String title;

    private String lecturer;


    @ColumnDefault("30L")
    private Long capacity = 30L;// 남은 인원

    @Builder
    public Lecture(Long id,String title, String lecturer){
        this.id = id;
        this.title = title;
        this.lecturer = lecturer;
        this.capacity = 30L;
    }
    public void reduceCapacity(){
        if(capacity > 0 ){
            capacity--;
        }else{
            throw new CustomException(ErrorCode.LECTURE_NOT_ENOUGH);
        }
    }

}
