package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UsersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class UserDAO {


    @PersistenceContext
    private EntityManager entityManager;

//    @PersistenceContext()
//    private EntityManager entityManager;


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

//    public UserEntity getUserByUsername(String username){
        public UsersEntity getUserByUsername(String username){
        try {
//            return entityManager.createNamedQuery("getUserByUsername",UserEntity.class).setParameter("username",username).getSingleResult();
            return entityManager.createNamedQuery("getUserByUsername",UsersEntity.class).setParameter("username",username).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

//    public UserAuthTokenEntity createAuthToken(UserAuthTokenEntity userAuthTokenEntity){
        public UserAuthEntity createAuthToken(UserAuthEntity userAuthEntity){
        entityManager.persist(userAuthEntity);
        return userAuthEntity;
    }

    public UsersEntity updateUserEntity(UsersEntity userEntity){
//        public UserEntity updateUserEntity(UserEntity userEntity){
        entityManager.merge(userEntity);
        return userEntity;
    }

    public UsersEntity createUser(UsersEntity userEntity){
//        public UserEntity createUser(UserEntity userEntity){
        userEntity.setUuid(UUID.randomUUID().toString());
        entityManager.persist(userEntity);
        return userEntity;
    }

    public List<UsersEntity> getUsersList(){
//        public List<UserEntity> getUsersList(){
        return entityManager.createNamedQuery("getUsers",UsersEntity.class).getResultList();
    }

    public UserAuthEntity isAccessTokenPresent(String accessToken){
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken",UserAuthEntity.class).setParameter("accessToken",accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public UserAuthEntity updatelogOutTime(UserAuthEntity userAuthEntity){
        entityManager.merge(userAuthEntity);
        return userAuthEntity;
    }

}


