package com.losevskiyfz.nplusonetask.repository;

import com.losevskiyfz.nplusonetask.dto.QuestionAnswerDto;
import com.losevskiyfz.nplusonetask.entity.Question;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @NonNull
    @Query("SELECT new com.losevskiyfz.nplusonetask.dto.QuestionAnswerDto(q.question, a.answer) " +
            "FROM Question q JOIN q.answer a")
    List<QuestionAnswerDto> findQuestionsAndAnswers();

}
