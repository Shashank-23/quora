package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;

import com.upgrad.quora.service.entity.UsersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDAO {


    @PersistenceContext
    private EntityManager entityManager;


    //This method is created to fetch the user details from the user table using uuid.
    //This method is only called from the Bussiness Service once authorization of requesting user is complete.
    //This method returns the user that it fetched and to the businessservice layer.
    //It has name query created which fetches the user details using uuid.if the user doesnt
    // not exist then the exception are caught and null is return to the service class.

    public UsersEntity getUser(final String uuid) {
        try {
            UsersEntity user = entityManager.createNamedQuery("userByUuid", UsersEntity.class).setParameter("uuid", uuid).getSingleResult();
            return user;
        }catch (NoResultException nre){
            return null;
        }
    }

    public UserEntity getUserByUsername(String username){
        try {
            return entityManager.createNamedQuery("getUserByUsername",UserEntity.class).setParameter("username",username).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public UserAuthTokenEntity createAuthToken(UserAuthTokenEntity userAuthTokenEntity){
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }

    public UserEntity updateUserEntity(UserEntity userEntity){
        entityManager.merge(userEntity);
        return userEntity;
    }

}


