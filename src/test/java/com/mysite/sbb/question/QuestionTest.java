package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class QuestionTest {

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void beforeEach() {
        questionRepository.deleteAll();
        questionRepository.clearAutoIncrement();
        init_data();
    }

    void init_data() {
        Question q1 = Question.builder()
                .subject("sbb가 무엇인가요?")
                .content("sbb에 대해서 알고 싶습니다.")
                .createDate(LocalDateTime.now())
                .build();

        Question q2 = Question.builder()
                .subject("스프링부트 모델 질문입니다.")
                .content("id는 자동으로 생성되나요?")
                .createDate(LocalDateTime.now())
                .build();

        Question q3 = Question.builder()
                .subject("곧 지울겁니다.")
                .content("삭제 예정")
                .createDate(LocalDateTime.now())
                .build();

        questionRepository.save(q1);
        questionRepository.save(q2);
        questionRepository.save(q3);
    }

    @Test
    @DisplayName("Question 전체 조회")
    void t2() {
        List<Question> all = questionRepository.findAll();
        assertEquals(3, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    @DisplayName("subject로 조회")
    void t3() {
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals("sbb에 대해서 알고 싶습니다.", q.getContent());
    }

    @Test
    @DisplayName("subject와 content로 조회")
    void t4() {
        Question q = questionRepository.findBySubjectAndContent(
                "sbb가 무엇인가요?",
                "sbb에 대해서 알고 싶습니다.");
        assertEquals(1, q.getId());
    }

    @Test
    @DisplayName("subject like 조회")
    void t5() {
        List<Question> qList = questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    @DisplayName("subject 업데이트")
    void t6() {
        Optional<Question> oq = questionRepository.findById(2);
        assertTrue(oq.isPresent());

        Question q = oq.get();
        q.updateSubject("id가 자동으로 올라가나요?");
        questionRepository.save(q);
    }

    @Test
    @DisplayName("Question 엔티티 삭제")
    void t7() {
        Optional<Question> oq = questionRepository.findById(3);
        assertTrue(oq.isPresent());

        Question q = oq.get();
        questionRepository.delete(q);

        assertEquals(2, questionRepository.count());
    }
}
