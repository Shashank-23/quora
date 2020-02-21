package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonBusinessService {


    @Autowired
    UserDAO userDao;
}
