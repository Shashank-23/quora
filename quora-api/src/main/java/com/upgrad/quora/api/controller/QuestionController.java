package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.QuestionRequest;
import com.upgrad.quora.api.model.QuestionResponse;
import com.upgrad.quora.service.business.QuestionBusinessService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    @Autowired
    QuestionBusinessService questionBusinessService;

    // Create Question EndPoint
    @PostMapping(path = "/question/create",consumes =  MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> signOut(QuestionRequest questionRequest,@RequestHeader("authorization") final String bearerAccessToken) throws  AuthorizationFailedException {
        String accessToken = bearerAccessToken.split("Bearer ")[1];

        QuestionEntity questionEntity =  new QuestionEntity();
        questionEntity.setContent(questionRequest.getContent());

        QuestionEntity createdQuestion = questionBusinessService.createQuestion(questionEntity,accessToken);

        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.id(createdQuestion.getUuid());

        return new ResponseEntity<>(questionResponse, HttpStatus.CREATED);

    }

}
