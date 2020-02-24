package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.AnswerRequest;
import com.upgrad.quora.api.model.AnswerResponse;
import com.upgrad.quora.service.business.AnswerBusinessService;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerController {

    @Autowired
    AnswerBusinessService answerBusinessService;

    // Create Answer EndPoint
    @PostMapping(path = "/question/{questionId}/answer/create",consumes =  MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> signOut(AnswerRequest answerRequest, @PathVariable("questionId") final  String questionId, @RequestHeader("authorization") final String bearerAccessToken) throws AuthorizationFailedException, InvalidQuestionException {
        String accessToken = bearerAccessToken.split("Bearer ")[1];

        AnswerEntity answerEntity =  new AnswerEntity();
        answerEntity.setAns(answerRequest.getAnswer());

        AnswerEntity createdAnswer = answerBusinessService.createAnswer(answerEntity,questionId,accessToken);

        AnswerResponse answerResponse  = new AnswerResponse();
        answerResponse.id(createdAnswer.getUuid()).status("ANSWER CREATED");

        return new ResponseEntity<>(answerResponse, HttpStatus.CREATED);

    }

}
