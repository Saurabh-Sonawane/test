package com.clim8.services;

import com.clim8.model.*;
import com.clim8.model.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionnaireService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireService.class);
    private List<Questionnaire> questionnaires ;

    public QuestionnaireService() {
        questionnaires = new ArrayList<>();
        String question1 = "q1";
        String question2 = "q2";
        String question3 = "q3";
        String question4 = "q4";

        String answer1 = "a1";
        String answer2 = "a2";

        Questionnaire questionnaire1 = new Questionnaire(question1, Arrays.asList(answer1, answer2));
        Questionnaire questionnaire2 = new Questionnaire(question2, Arrays.asList(answer1, answer2));
        Questionnaire questionnaire3 = new Questionnaire(question3, Arrays.asList(answer1, answer2));
        Questionnaire questionnaire4 = new Questionnaire(question4, Arrays.asList(answer1, answer2));

        questionnaires = Arrays.asList(questionnaire1, questionnaire2, questionnaire3, questionnaire4);

    }

    public List<Questionnaire> getQuestionnaires() {
        return questionnaires;
    }

    public CustomerResponse checkQuestionnaire(List<QuestionAnswer> questionAnswers) {
        UUID uuid = UUID.randomUUID();
        logQuestionnaire(uuid, questionAnswers);

        CustomerResponse customerResponse = validateNoOfAnswers(uuid, questionAnswers);
        if(customerResponse != null) {
            return customerResponse;
        }

        customerResponse = checkAnswers(uuid, questionAnswers);
        return customerResponse;

    }

    private void logQuestionnaire(UUID uuid, List<QuestionAnswer> questionAnswers) {
        logger.info("Customer with customer Id : {}",uuid);
        logger.info("Submitted Questionnaire : {}",questionAnswers);
    }

    private CustomerResponse validateNoOfAnswers(UUID uuid, List<QuestionAnswer> questionAnswers) {
        CustomerResponse customerResponse = null;
        if(questionAnswers.size() != 4 || ( ! isAllQuestionsAnswered( questionAnswers))) {
            customerResponse = new CustomerResponse();
            customerResponse.setCustomerId(uuid.toString());
            customerResponse.setStatus(Status.NOT_COMPLETE);
            customerResponse.setMessage("Please complete the questionnaire");
        }
        return customerResponse;
    }

    private boolean isAllQuestionsAnswered(List<QuestionAnswer> questionAnswers) {
        return questionAnswers.stream().map(QuestionAnswer::getQuestion)
                .collect(Collectors.toList()).containsAll(Arrays.asList("q1","q2","q3","q4"));
    }

    private CustomerResponse checkAnswers(UUID uuid, List<QuestionAnswer> questionAnswers) {
        CustomerResponse customerResponse = null;

        Map<String, String> correctAnswers = getCorrectAnswers();

        for ( QuestionAnswer questionAnswer: questionAnswers ) {
            String correctAnswer = correctAnswers.get(questionAnswer.getQuestion());
            if(!correctAnswer.equals(questionAnswer.getAnswer())) {
                customerResponse = new CustomerResponse();
                customerResponse.setCustomerId(uuid.toString());
                customerResponse.setStatus(Status.NOT_SUITABLE);
                customerResponse.setMessage("Incorrect answers.");
                return customerResponse;
            }
        }
        customerResponse = new CustomerResponse();
        customerResponse.setCustomerId(uuid.toString());
        customerResponse.setStatus(Status.SUITABLE);
        customerResponse.setMessage("Congratulations all answers are correct, you can open am account.");
        return customerResponse;
    }

    private Map<String, String> getCorrectAnswers() {
        Map<String, String> correctAnswers = new HashMap<>();
        correctAnswers.put("q1","a1");
        correctAnswers.put("q2","a2");
        correctAnswers.put("q3","a1");
        correctAnswers.put("q4","a1");
        return correctAnswers;
    }
}
