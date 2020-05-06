package com.clim8.resources;

import com.clim8.model.CustomerResponse;
import com.clim8.model.QuestionAnswer;
import com.clim8.model.Questionnaire;
import com.clim8.model.enums.Status;
import com.clim8.services.QuestionnaireService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(QuestionnaireResource.class)
class QuestionnaireResourceTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionnaireService questionnaireService;

    @Test
    void verifyGetQuestionnaireShouldReturn200WithResponse() throws Exception {
        //given
        Questionnaire questionnaire1 = new Questionnaire("question1", Arrays.asList("ans1", "ans2"));
        Questionnaire questionnaire2 = new Questionnaire("question2", Arrays.asList("ans1", "ans2"));
        Questionnaire questionnaire3 = new Questionnaire("question3", Arrays.asList("ans1", "ans2"));
        Questionnaire questionnaire4 = new Questionnaire("question4", Arrays.asList("ans1", "ans2"));

        List<Questionnaire> questionnaires = Arrays.asList(questionnaire1, questionnaire2, questionnaire3, questionnaire4);
        given(questionnaireService.getQuestionnaires()).willReturn(questionnaires);

        //when //then
        mockMvc.perform(get("/questionnaire-api/questionnaire"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].question", is("question1")))
                .andExpect(jsonPath("$[0].answers", contains("ans1","ans2")))
                .andExpect(jsonPath("$[1].question", is("question2")))
                .andExpect(jsonPath("$[1].answers", contains("ans1","ans2")))
                .andExpect(jsonPath("$[2].question", is("question3")))
                .andExpect(jsonPath("$[2].answers", contains("ans1","ans2")))
                .andExpect(jsonPath("$[3].question", is("question4")))
                .andExpect(jsonPath("$[3].answers", contains("ans1","ans2")));


    }

    @Test
    void verifyPutSubmitQuestionAnswersShouldReturn400WithNotCompleteResponse() throws Exception {
        //given
        String request = "[\n" +
                "    {\n" +
                "        \"question\": \"q1\",\n" +
                "        \"answer\": \"a1\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"question\": \"q4\",\n" +
                "        \"answer\": \"a1\"\n" +
                "    }\n" +
                "]";

        String customerId = UUID.randomUUID().toString();
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomerId(customerId);
        customerResponse.setStatus(Status.NOT_COMPLETE);
        customerResponse.setMessage("Please complete the questionnaire");
        given(questionnaireService.checkQuestionnaire(ArgumentMatchers.anyList())).willReturn(customerResponse);

        //when //then
        mockMvc.perform(put("/questionnaire-api/submit/answers").content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("NOT_COMPLETE")))
                .andExpect(jsonPath("$.customerId", is(customerId)));


    }

    @Test
    void verifyPutSubmitQuestionAnswersShouldReturn200WithNotSuitableResponse() throws Exception {
        //given
        String request = "[\n" +
                "    {\n" +
                "        \"question\": \"q1\",\n" +
                "        \"answer\": \"a1\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"question\": \"q2\",\n" +
                "        \"answer\": \"a1\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"question\": \"q3\",\n" +
                "        \"answer\": \"a1\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"question\": \"q4\",\n" +
                "        \"answer\": \"a1\"\n" +
                "    }\n" +
                "]";

        String customerId = UUID.randomUUID().toString();
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomerId(customerId);
        customerResponse.setStatus(Status.NOT_SUITABLE);
        customerResponse.setMessage("Please complete the questionnaire");
        given(questionnaireService.checkQuestionnaire(ArgumentMatchers.anyList())).willReturn(customerResponse);

        //when //then
        mockMvc.perform(put("/questionnaire-api/submit/answers").content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("NOT_SUITABLE")))
                .andExpect(jsonPath("$.customerId", is(customerId)));


    }

    @Test
    void verifyPutSubmitQuestionAnswersShouldReturn200WithSuitableResponse() throws Exception {
        //given
        String request = "[\n" +
                "    {\n" +
                "        \"question\": \"q1\",\n" +
                "        \"answer\": \"a1\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"question\": \"q2\",\n" +
                "        \"answer\": \"a2\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"question\": \"q3\",\n" +
                "        \"answer\": \"a1\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"question\": \"q4\",\n" +
                "        \"answer\": \"a1\"\n" +
                "    }\n" +
                "]";

        String customerId = UUID.randomUUID().toString();
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomerId(customerId);
        customerResponse.setStatus(Status.SUITABLE);
        customerResponse.setMessage("Please complete the questionnaire");
        given(questionnaireService.checkQuestionnaire(ArgumentMatchers.anyList())).willReturn(customerResponse);

        //when //then
        mockMvc.perform(put("/questionnaire-api/submit/answers").content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("SUITABLE")))
                .andExpect(jsonPath("$.customerId", is(customerId)));


    }
}