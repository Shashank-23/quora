package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDAO;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class SignOutUserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
        public UserAuthEntity getAuthEntity(String accessToken) throws SignOutRestrictedException {

        UserAuthEntity userAuthEntity = userDAO.isAccessTokenPresent(accessToken);
        if ( userAuthEntity == null) {
            throw new SignOutRestrictedException("SGR-001","User is not Signed in");
        }
        else{
            userAuthEntity.setLogoutAt(ZonedDateTime.now());
            userDAO.updatelogOutTime(userAuthEntity);
            return userAuthEntity;
        }
    }
}
