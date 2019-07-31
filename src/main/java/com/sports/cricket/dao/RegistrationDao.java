package com.sports.cricket.dao;


import com.sports.cricket.dto.Teams;
import com.sports.cricket.model.Register;
import com.sports.cricket.model.UserLogin;

import java.util.List;

public interface RegistrationDao {

    UserLogin loginUser(UserLogin userLogin);

    Register getUser(String emailId);

    List<Teams> getAllUsers(String type);

}
