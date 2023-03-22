package com.mysite.sbb.question;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Question300Test {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void t1() {
        questionRepository.deleteAll();
        for (int i = 0; i < 300; i++) {
            questionService.create(
                    String.format("test %03d", i),
                    "test content",
                    null
            );
        }

        List<Question> questionList = questionRepository.findAll();
        Assertions.assertEquals(300, questionList.size());
    }


}
