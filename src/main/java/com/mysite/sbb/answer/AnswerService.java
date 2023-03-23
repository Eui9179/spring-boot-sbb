package com.mysite.sbb.answer;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final UserService userService;

    @Transactional
    public void create(Question question, String content, SiteUser author) {
        Answer answer = Answer.builder()
                .content(content)
                .author(author)
                .build();
        question.addAnswer(answer);
        answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("answer not found"));
    }

    public void modify(Answer answer, String content) {
        answer.modify(content);
        answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        answerRepository.save(answer);
    }

    @Transactional
    public void vote(Integer answerId, String username) {
        Answer answer = this.getAnswer(answerId);
        answer.addVoter(userService.getUser(username));
    }
}
