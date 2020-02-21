package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class UserDAO {

    @PersistenceContext()
    private EntityManager entityManager;

    public UserEntity createUser(UserEntity userEntity){
        userEntity.setUuid(UUID.randomUUID().toString());
        entityManager.persist(userEntity);
        return userEntity;
    }

    public List<UserEntity> getUsersList(){
        return entityManager.createNamedQuery("getUsers",UserEntity.class).getResultList();
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
