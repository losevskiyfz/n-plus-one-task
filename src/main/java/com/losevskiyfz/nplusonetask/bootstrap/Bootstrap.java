package com.losevskiyfz.nplusonetask.bootstrap;

import com.losevskiyfz.nplusonetask.dto.QuestionAnswerDto;
import com.losevskiyfz.nplusonetask.entity.Answer;
import com.losevskiyfz.nplusonetask.entity.Question;
import com.losevskiyfz.nplusonetask.entity.Test;
import com.losevskiyfz.nplusonetask.repository.QuestionRepository;
import com.losevskiyfz.nplusonetask.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator.assertSelectCount;

@Component
public class Bootstrap implements CommandLineRunner {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final Bootstrap bootstrap;

    @Autowired
    public Bootstrap(TestRepository testService, QuestionRepository questionRepository, @Lazy Bootstrap bootstrap) {
        this.testRepository = testService;
        this.questionRepository = questionRepository;
        this.bootstrap = bootstrap;
    }

    private void initializeDatabase(){
        Test test = new Test();
        test.setName("Java Core Test");

        Question question1 = new Question();
        question1.setQuestion("Internal mechanism of HashMap");
        question1.setTest(test);

        Answer answer1 = new Answer();
        answer1.setAnswer("A hash table data structure with red-black tree restructuring");

        answer1.setQuestion(question1);
        question1.setAnswer(answer1);

        Question question2 = new Question();
        question2.setQuestion("Comparable contract");
        question2.setTest(test);

        Answer answer2 = new Answer();
        answer2.setAnswer("Upon implementing comparable you should follow a total order principe");

        answer2.setQuestion(question2);
        question2.setAnswer(answer2);

        Question question3 = new Question();
        question3.setQuestion("What is the size of char primitive data type?");
        question3.setTest(test);

        Answer answer3 = new Answer();
        answer3.setAnswer("2 bytes");

        answer3.setQuestion(question3);
        question3.setAnswer(answer3);

        test.setQuestions(List.of(question1, question2, question3));

        testRepository.save(test);

    }

    @Transactional
    public void verifyNPlusOne(){
        System.out.println("---------- VERIFY THE NUMBER OF QUERIES DURING ISSUING COMPOUND DATA ----------");

        System.out.println("Try to access some questions and answers...");

        List<QuestionAnswerDto> questions = questionRepository.findQuestionsAndAnswers();

        for(QuestionAnswerDto question: questions){
            System.out.println("Question: " + question.getQuestionText());
            System.out.println("Answer: " + question.getAnswerText());
        }

//        assertSelectCount(questions.size() + 1);
        assertSelectCount(1);

        System.out.println("How many select queries have benn occurred?");

//        System.out.println("questions.size() + 1 = " + (questions.size() + 1));
        System.out.println(1);

        System.out.println("---------- END OF THE VERIFYING QUERIES DURING ISSUING COMPOUND DATA ----------");

    }

    @Override
    public void run(String... args) {
        initializeDatabase();
        bootstrap.verifyNPlusOne();
    }

}
