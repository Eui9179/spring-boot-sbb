package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AnswerTest {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Test
    void test_answer_생성_조회() {
        Optional<Question> oq = questionRepository.findById(2);
        assertTrue(oq.isPresent());

        Question q = oq.get();

        Answer answer = Answer.builder()
                .content("네 자동으로 생성됩니다.")
                .question(q)
                .createDate(LocalDateTime.now())
                .build();
        answerRepository.save(answer);
    }
}
