package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AnswerTest {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @BeforeEach
    void beforeEach() {
        answerRepository.deleteAll();
        answerRepository.clearAutoIncrement();

        questionRepository.deleteAll();
        questionRepository.clearAutoIncrement();
        Question q = Question.builder()
                .subject("sbb가 무엇인가요?")
                .content("sbb에 대해서 알고 싶습니다.")
                .createDate(LocalDateTime.now())
                .build();
        questionRepository.save(q);
    }

    @Transactional
    @Test
    void t1() {
        Optional<Question> oq = questionRepository.findById(1);
        assertTrue(oq.isPresent());
        assertEquals("sbb가 무엇인가요?", oq.get().getSubject());

        Answer answer = Answer.builder()
                .content("모르겠습니다.")
                .question(oq.get())
                .createDate(LocalDateTime.now())
                .build();

        answerRepository.save(answer);

        List<Answer> answerAll = answerRepository.findAll();
        Answer savedAnswer = answerAll.get(0);
        assertEquals("모르겠습니다.", savedAnswer.getContent());
    }
}
