package com.upgrad.quora.service.business;


import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserAuthDao;
import com.upgrad.quora.service.dao.UserDAO;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

//This is service class for AnswerController.

@Service
public class AnswerBusinessService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    AnswerDao answerDao;

    @Autowired
    UserAuthDao userAuthDao;

    @Autowired
    private UserDAO userDao;


    //This method takes the question uuid and authorization token as the parameter from the AnswerController method getAllAnswerToQuestion.
    //This method return the list of AnswerEntity.
    //The authorizationToken is used to authorise the access by using UserAuthDao Method getAuthToken and are checked for the required condition.
    //If the condition are passed then the Question is fetched from the Question table using QuestionDao.
    //If the Question exists then Answer list is fetched using AnswerDao Method getAllAnswerToQuestion by passing the particular question.

    public List<AnswerEntity> getAllAnswerToQuestion(final String questionUuid,final String authorizationToken) throws AuthorizationFailedException, InvalidQuestionException {
        UserAuthEntity userAuthEntity = userAuthDao.getAuthToken(authorizationToken);

        if (userAuthEntity == null){//Chekcing if user is not signed in
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }else if (userAuthEntity.getLogoutAt() != null){//checking if user is signed out
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get the answers");
        }

        QuestionEntity questionEntity = questionDao.getQuestionByQuestionUuid(questionUuid);

        if(questionEntity == null){
            throw new InvalidQuestionException("QUES-001","The question with entered uuid whose details are to be seen does not exist");
        }
        //Returning the List of AnswerEntity to the calling method.
        List<AnswerEntity> answerEntities = answerDao.getAllAnswerToQuestion(questionEntity);
        return answerEntities;
    }

    @Transactional
    public AnswerEntity createAnswer(AnswerEntity answerEntity,String questionId, String accessToken) throws AuthorizationFailedException, InvalidQuestionException {

        UserAuthEntity userAuthEntity = userAuthDao.getAuthToken(accessToken);
        QuestionEntity questionEntity = questionDao.getQuestionByQuestionUuid(questionId);

        if(questionEntity == null)
        {
            throw new InvalidQuestionException("QUES-001","The question entered is invalid");
        }
        else if (userAuthEntity == null){//Chekcing if user is not signed in
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }else if (userAuthEntity.getLogoutAt() != null){//checking if user is signed out
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to post a question");
        }
        else{
            UserAuthEntity authenticatedUser = userDao.isAccessTokenPresent(accessToken);

            answerEntity.setUuid(UUID.randomUUID().toString());
            answerEntity.setDate(ZonedDateTime.now());
            answerEntity.setUser(authenticatedUser.getUser());
            answerEntity.setQuestion(questionEntity);
            return answerDao.createAnswer(answerEntity);
        }

    }

}