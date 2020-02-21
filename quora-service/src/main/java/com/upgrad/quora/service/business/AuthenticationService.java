package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDAO;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UsersEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class AuthenticationService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    PasswordCryptographyProvider passwordCryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
//    public UserAuthTokenEntity authenticate(final String username, final String password) throws AuthenticationFailedException {
        public UserAuthEntity authenticate(final String username, final String password) throws AuthenticationFailedException {
//        UserEntity userEntity = userDAO.getUserByUsername(username);
        UsersEntity userEntity = userDAO.getUserByUsername(username);
        if (userEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "This username does not exist");
        }
        String encryptedPassword = passwordCryptographyProvider.encrypt(password, userEntity.getSalt());
        if (encryptedPassword.equals(userEntity.getPassword())) {

//            UserAuthTokenEntity userAuthToken = new UserAuthTokenEntity();
            UserAuthEntity userAuthToken = new UserAuthEntity();
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            userAuthToken.setAccessToken(jwtTokenProvider.generateToken(userEntity.getUuid(), now, expiresAt));

            userAuthToken.setUser(userEntity);
            userAuthToken.setLoginAt(now);
            userAuthToken.setExpiresAt(expiresAt);
            userAuthToken.setUuid(userEntity.getUuid());
//            userAuthToken.setCreatedBy("api-backend");
//            userAuthToken.setCreatedAt(now);

//            UserAuthTokenEntity authToken = userDAO.createAuthToken(userAuthToken);
            UserAuthEntity authToken = userDAO.createAuthToken(userAuthToken);
//            userEntity.setLastLoginAt(now);
//            userDAO.updateUserEntity(userEntity);

            return authToken;
        } else {
            throw new AuthenticationFailedException("ATH-002", "Password failed");
        }
    }
}
