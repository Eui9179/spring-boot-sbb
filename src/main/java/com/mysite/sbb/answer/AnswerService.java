package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Transactional
    public void create(Question question, String content, SiteUser author) {
        Answer answer = Answer.builder()
                .content(content)
                .author(author)
                .build();
        question.addAnswer(answer);
        answerRepository.save(answer);
    }
}
