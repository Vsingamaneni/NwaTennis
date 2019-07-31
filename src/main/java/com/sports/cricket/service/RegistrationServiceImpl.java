package com.sports.cricket.service;

import com.sports.cricket.dao.RegistrationDao;
import com.sports.cricket.dto.Teams;
import com.sports.cricket.model.Register;
import com.sports.cricket.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService, Serializable {

    RegistrationDao registrationDao;

    @Autowired
    public void setRegistrationDao(RegistrationDao registrationDao) {
        this.registrationDao = registrationDao;
    }

    @Override
    public UserLogin loginUser(UserLogin userLogin) {
        return registrationDao.loginUser(userLogin);
    }

    @Override
    public Register getUser(String emailId) {
        return registrationDao.getUser(emailId);
    }

    @Override
    public List<Teams> getAllUsers(String type) {
        return registrationDao.getAllUsers(type);
    }

}
