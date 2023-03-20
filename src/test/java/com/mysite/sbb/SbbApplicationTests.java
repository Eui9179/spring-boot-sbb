package com.mysite.sbb;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void test_질문_저장() {
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
	void test_전체_조회() {
		List<Question> all = questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	void test_subject_조회() {
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals("sbb에 대해서 알고 싶습니다.", q.getContent());
	}

	@Test
	void test_subject_and_content_조회() {
		Question q = questionRepository.findBySubjectAndContent(
				"sbb가 무엇인가요?",
				"sbb에 대해서 알고 싶습니다.");
		assertEquals(1, q.getId());
	}

	@Test
	void test_subject_like_조회() {
		List<Question> qList = questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	void test_subject_업데이트() {
		Optional<Question> oq = questionRepository.findById(2);
		assertTrue(oq.isPresent());

		Question q = oq.get();
		q.updateSubject("test2");
		questionRepository.save(q);
	}

	@Test
	void test_삭제() {
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());

		Question q = oq.get();
		questionRepository.delete(q);

		assertEquals(1, questionRepository.count());
	}

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
