package com.clim8.services;

import com.clim8.model.CustomerResponse;
import com.clim8.model.QuestionAnswer;
import com.clim8.model.Questionnaire;
import com.clim8.model.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class QuestionnaireServiceTest {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Test
    void getQuestionnairesShouldReturnQuestionnaire() {
        //given

        //when
        List<Questionnaire> questionnaires = questionnaireService.getQuestionnaires();

        //then
        Assertions.assertEquals(4, questionnaires.size());
        Assertions.assertEquals("q1", questionnaires.get(0).getQuestion());
        Assertions.assertEquals(Arrays.asList("a1","a2"), questionnaires.get(0).getAnswers());

        Assertions.assertEquals("q2", questionnaires.get(1).getQuestion());
        Assertions.assertEquals(Arrays.asList("a1","a2"), questionnaires.get(1).getAnswers());

        Assertions.assertEquals("q3", questionnaires.get(2).getQuestion());
        Assertions.assertEquals(Arrays.asList("a1","a2"), questionnaires.get(2).getAnswers());

        Assertions.assertEquals("q4", questionnaires.get(3).getQuestion());
        Assertions.assertEquals(Arrays.asList("a1","a2"), questionnaires.get(3).getAnswers());
    }

    @Test
    void checkQuestionnaireShouldReturnNotCompleteIfQuestionsLessThan4() {
        //given
        List<QuestionAnswer> list = new ArrayList<>();
        list.add(new QuestionAnswer("q1","a1"));
        list.add(new QuestionAnswer("q2","a1"));

        //when
        CustomerResponse customerResponse = questionnaireService.checkQuestionnaire(list);

        //then
        Assertions.assertEquals(Status.NOT_COMPLETE, customerResponse.getStatus());
        Assertions.assertNotNull(customerResponse.getCustomerId());
        Assertions.assertEquals("Please complete the questionnaire", customerResponse.getMessage());
    }

    @Test
    void checkQuestionnaireShouldReturnNotSuitableIfAnswerNotCorrect() {
        //given
        List<QuestionAnswer> list = new ArrayList<>();
        list.add(new QuestionAnswer("q1","a1"));
        list.add(new QuestionAnswer("q2","a1"));
        list.add(new QuestionAnswer("q3","a1"));
        list.add(new QuestionAnswer("q4","a1"));

        //when
        CustomerResponse customerResponse = questionnaireService.checkQuestionnaire(list);

        //then
        Assertions.assertEquals(Status.NOT_SUITABLE, customerResponse.getStatus());
        Assertions.assertNotNull(customerResponse.getCustomerId());
        Assertions.assertEquals("Incorrect answers.", customerResponse.getMessage());
    }

    @Test
    void checkQuestionnaireShouldReturnSuitableIfAllAnswersCorrect() {
        //given
        List<QuestionAnswer> list = new ArrayList<>();
        list.add(new QuestionAnswer("q1","a1"));
        list.add(new QuestionAnswer("q2","a2"));
        list.add(new QuestionAnswer("q3","a1"));
        list.add(new QuestionAnswer("q4","a1"));

        //when
        CustomerResponse customerResponse = questionnaireService.checkQuestionnaire(list);

        //then
        Assertions.assertEquals(Status.SUITABLE, customerResponse.getStatus());
        Assertions.assertNotNull(customerResponse.getCustomerId());
        Assertions.assertEquals("Congratulations all answers are correct, you can open am account.", customerResponse.getMessage());
    }

}