package archi.apply.lecture;

import apply.lecture.LectureApplication;
import apply.lecture.application.EnrollLectureFacade;
import apply.lecture.application.enrollment.EnrollService;
import apply.lecture.application.lecture.LectureCriteria;
import apply.lecture.application.lecture.LectureResult;
import apply.lecture.application.lecture.LectureService;
import apply.lecture.application.member.MemberService;
import apply.lecture.infrastructure.enrollment.EnrollRepository;
import apply.lecture.infrastructure.lecture.Lecture;
import apply.lecture.infrastructure.lecture.LectureRepository;
import apply.lecture.infrastructure.member.Member;
import apply.lecture.infrastructure.member.MemberRepository;

import apply.lecture.interfaces.common.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;
@Transactional
@SpringBootTest(classes = LectureApplication.class)
public class EnrollIntegratTest {

    @Autowired
    private LectureService lectureService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private EnrollService enrollService;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EnrollRepository enrollRepository;

    @Autowired
    private EnrollLectureFacade facade;

    @Test
    @DisplayName("선착순 보다 많이 신청했을 때")
    void enrollLectureConcurrencyHaveMax() throws InterruptedException, ExecutionException {

         String lectureTitle = "MATH";

        Lecture lecture = lectureService.getFindLecture(lectureTitle);

        long successCount = 0;
        long failCount = 0;
        //given
        final long threads = 40;

        List<CompletableFuture<Boolean>> tasks = new ArrayList<>();
        List<Long> exceptionCount = new ArrayList<>();


        //when
        for(long i = 1; i <= threads; i++){
            long memberId = i;
            tasks.add(CompletableFuture.supplyAsync(()->{
                LectureResult result = facade.enroll(new LectureCriteria(memberId,lectureTitle).toCommand());
                return result != null;
            }).exceptionally(CustomException  -> {
                exceptionCount.add(memberId);
                return false;
            }));

        }

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
        allTasks.join();

        for (int i = 1; i<=threads; i++){
            if(tasks.get(i).get()){
                successCount++;
            }else{
                failCount++;
            }
        }

        assertThat(successCount).isEqualTo(30);
        assertThat(failCount).isEqualTo(10);
    }
}
