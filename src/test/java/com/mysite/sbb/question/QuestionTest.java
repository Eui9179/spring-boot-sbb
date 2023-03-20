package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Question에 엔티티 저장")
    void t1() {
        // given
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

        // when
        questionRepository.save(q1);
        questionRepository.save(q2);
    }

    @Test
    @DisplayName("Question 전체 조회")
    void t2() {
        List<Question> all = questionRepository.findAll();
        assertEquals(2, all.size());

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
        Optional<Question> oq = questionRepository.findById(1);
        assertTrue(oq.isPresent());

        Question q = oq.get();
        questionRepository.delete(q);

        assertEquals(1, questionRepository.count());
    }

    @Test
    @DisplayName("댓글 조회")
    @Transactional
    void t8() {
        Optional<Question> oq = questionRepository.findById(2);
        assertTrue(oq.isPresent());

        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }
}
