package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Transactional
    public void create(Question question, String content) {
        Answer answer = Answer.builder()
                .content(content)
                .build();
        question.addAnswer(answer);
        answerRepository.save(answer);
    }
}
