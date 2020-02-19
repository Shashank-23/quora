package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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

}
