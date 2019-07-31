package com.sports.cricket.dao;

import com.sports.cricket.dto.Teams;
import com.sports.cricket.model.Register;
import com.sports.cricket.model.UserLogin;
import com.sports.cricket.password.VerifyProvidedPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationDaoImpl implements RegistrationDao, Serializable {

    private static final String MIXED = "TEAMS_MIXED";
    private static final String MENS = "TEAMS_MENS";


    DataSource dataSource;
    JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserLogin loginUser(UserLogin userLogin) {
        System.out.println("Inside Member Login");

        Map<String, Object> params = new HashMap<>();
        params.put("email", userLogin.getEmail());

        String selectSQL = "SELECT * FROM REGISTER where email = '" + userLogin.getEmail() + "'";

        Connection conn = null;
        Statement statement;
        ResultSet resultSet;
        Register register =null;

        try {
            conn = dataSource.getConnection();
            conn.prepareStatement(selectSQL);

            statement = conn.createStatement();
            resultSet = statement.executeQuery(selectSQL);

            while(resultSet.next()){
                register = new Register();
                register.setfName(resultSet.getString("fName"));
                register.setlName(resultSet.getString("lName"));
                register.setMemberId(resultSet.getInt("memberId"));
                register.setRegisteredTime(resultSet.getString("registeredTime"));
                register.setIsActive(resultSet.getString("isActive"));
                register.setRole(resultSet.getString("role"));
                register.setEncryptedPass(resultSet.getString("encryptedPass"));
                register.setSaltKey(resultSet.getString("saltKey"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }


        if(null != register
            && null != register.getEncryptedPass() && null != register.getSaltKey()){
               if(!VerifyProvidedPassword.decryptPassword(userLogin.getPassword(), register)){
                   System.out.println("Passwords mismatch");
               }else{
                   System.out.println("Correct Password");
                   userLogin.setFirstName(register.getfName());
                   userLogin.setLastName(register.getlName());
                   userLogin.setMemberId(register.getMemberId());
                   userLogin.setLoginSuccess(true);
                   userLogin.setIsActive(register.getIsActive());
                   userLogin.setRole(register.getRole());
                   userLogin.setRegisteredTime(register.getRegisteredTime());
                   userLogin.setChoice(register.getChoice());
               }
           }
        return userLogin;
    }

    @Override
    public Register getUser(String email) {
        System.out.println("Inside retrieving User");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);

        String getUser = "SELECT * FROM REGISTER where email = '" + email + "'";

        Connection conn = null;
        Statement statement;
        ResultSet resultSet;
        Register register =null;

        try {
            conn = dataSource.getConnection();
            conn.prepareStatement(getUser);

            statement = conn.createStatement();
            resultSet = statement.executeQuery(getUser);

            while(resultSet.next()){
                register = new Register();
                register.setfName(resultSet.getString("fName"));
                register.setlName(resultSet.getString("lName"));
                register.setEmailId(resultSet.getString("email"));
                register.setMemberId(resultSet.getInt("memberId"));
                register.setIsActive(resultSet.getString("isActive"));
                register.setRegisteredTime(resultSet.getString("registeredTime"));
                register.setRole(resultSet.getString("role"));
                register.setEncryptedPass(resultSet.getString("encryptedPass"));
                register.setSaltKey(resultSet.getString("saltKey"));
                register.setSecurityQuestion(resultSet.getString("securityQuestion"));
                register.setSecurityAnswer(resultSet.getString("securityAnswer"));
                register.setChoice(resultSet.getString("choice"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }

        if(null != register
                && null != register.getSecurityAnswer()
                && null != register.getSecurityQuestion()){
            System.out.println("Retrieved user successfully");
        }

        return register;
    }

    @Override
    public List<Teams> getAllUsers(String type) {

        String team = "TEAMS_MIXED";

        if (type.equalsIgnoreCase("mixed")){
            team = MIXED;
        } else if (type.equalsIgnoreCase("mens")) {
            team = MENS;
        }

        String sql = "Select * from " + team;

        List<Teams> teamsList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(Teams.class));

        return teamsList;
    }

}
