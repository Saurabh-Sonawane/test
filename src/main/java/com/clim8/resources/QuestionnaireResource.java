package com.clim8.resources;

import com.clim8.model.CustomerResponse;
import com.clim8.model.QuestionAnswer;
import com.clim8.model.Questionnaire;
import com.clim8.model.enums.Status;
import com.clim8.services.QuestionnaireService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import io.swagger.annotations.ApiOperation;


@Api(value="Clim8 Questionnaire API", description="This api provides endpoints to fetch and sumit questionnaires")
@RestController
@RequestMapping("/questionnaire-api")
public class QuestionnaireResource {
    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireResource.class);

    @Autowired
    private QuestionnaireService questionnaireService;

    @ApiOperation(value = "Get questionnaires to check eligibility for account", response = List.class)
    @RequestMapping(value = "/questionnaire", method = GET, produces = APPLICATION_JSON_VALUE)
    @GetMapping(value = "/questionnaire", produces = APPLICATION_JSON_VALUE)
    public List<Questionnaire> getQuestionnaire() {
        return questionnaireService.getQuestionnaires();
    }


    @ApiOperation(value = "Submit questionnaire and assess eligibility for account", response = CustomerResponse.class)
    @RequestMapping(value = "/submit/answers", method = PUT, produces = APPLICATION_JSON_VALUE)
    @GetMapping(value = "/submit/answers", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> submitAnswer(@RequestBody List<QuestionAnswer> questionAnswers) {
        CustomerResponse customerResponse = questionnaireService.checkQuestionnaire(questionAnswers);
        if(customerResponse.getStatus().equals(Status.NOT_COMPLETE)) {
            return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.OK);
        }

    }






}
