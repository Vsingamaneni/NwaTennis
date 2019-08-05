package com.sports.cricket.dao;

import com.sports.cricket.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ScheduleDaoImpl implements ScheduleDao, Serializable {

    DataSource dataSource;
    JdbcTemplate jdbcTemplate;

    private String MIXED = "SCHEDULE_MIXED";

    private String MENS = "SCHEDULE_MENS";

    private String MIXED_KNOCKOUT = "SCHEDULE_MIXED_KNOCKOUTS";

    private String MENS_KNOCKOUT = "SCHEDULE_MENS_KNOCKOUTS";

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Schedule> findAll(String name) {
        String sql = "SELECT * FROM " + name + " where isActive = TRUE";

        List<Schedule> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Schedule.class));

        return result;
    }

    @Override
    public List<Fixtures> fixturesAndResults(String type) {
        if (type.equalsIgnoreCase("mixed")) {
            type = MIXED;
        } else if (type.equalsIgnoreCase("mens")) {
            type = MENS;
        } else if (type.equalsIgnoreCase("mixed_knockouts")) {
            type = MIXED_KNOCKOUT;
        } else if (type.equalsIgnoreCase("mens_knockouts")) {
            type = MENS_KNOCKOUT;
        }

        String sql = "SELECT * FROM " + type;
        List<Fixtures> result = null;
        try {
             result = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Fixtures.class));
        }catch (Exception ex){
            System.out.println(ex);
        }

        return result;
    }

    @Override
    public List<Fixtures> fixture(String type, String key) {
        if (type.equalsIgnoreCase("mixed")) {
            type = MIXED;
        } else {
            type = MENS;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("matchKey", key);

        String sql = "SELECT * FROM " + type + " where matchKey = ?";

        List<Fixtures> result = jdbcTemplate.query(sql, new Object[]{key}, new BeanPropertyRowMapper(Fixtures.class));

        return result;
    }

    @Override
    public List<Fixtures> multiFixture(String table, String key) {
        Map<String, Object> params = new HashMap<>();

        params.put("matchKey", key);

        if (table.equalsIgnoreCase("mixed")) {
            table = MIXED_KNOCKOUT;
        } else {
            table = MENS_KNOCKOUT;
        }

        String knockOutSql = "SELECT * FROM " + table + " where matchKey = ?";

        List<Fixtures> result = jdbcTemplate.query(knockOutSql, new Object[]{key}, new BeanPropertyRowMapper(Fixtures.class));

        return result;
    }

    @Override
    public boolean updateFixture(String type, Fixtures fixtures) {
        if (type.equalsIgnoreCase("mixed")) {
            type = MIXED;
        } else {
            type = MENS;
        }

        String sql = "UPDATE " + type + " SET homeTeamScore = ? , awayTeamScore = ?  where matchNumber = ?";


        Connection conn = null;

        boolean isSuccess = false;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fixtures.getHomeTeamScore());
            ps.setString(2, fixtures.getAwayTeamScore());
            ps.setInt(3, fixtures.getMatchNumber());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    isSuccess = true;
                } catch (SQLException e) {
                }
            }
        }

        return isSuccess;
    }

    @Override
    public boolean updateMultiFixture(String type, Fixtures fixtures) {
        if (type.equalsIgnoreCase("mixed")) {
            type = MIXED_KNOCKOUT;
        } else {
            type = MENS_KNOCKOUT;
        }

        String sql = "UPDATE " + type + " SET homeTeamScore = ? , awayTeamScore = ?, homeTeamScore1 = ? , awayTeamScore1 = ?, homeTeamScore2 = ? , awayTeamScore2 = ?, homeTeamScore3 = ? , awayTeamScore3 = ?  where matchNumber = ?";


        Connection conn = null;

        boolean isSuccess = false;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fixtures.getHomeTeamScore());
            ps.setString(2, fixtures.getAwayTeamScore());
            ps.setString(3, fixtures.getHomeTeamScore1());
            ps.setString(4, fixtures.getAwayTeamScore1());
            ps.setString(5, fixtures.getHomeTeamScore2());
            ps.setString(6, fixtures.getAwayTeamScore2());
            ps.setString(7, fixtures.getHomeTeamScore3());
            ps.setString(8, fixtures.getAwayTeamScore3());
            ps.setInt(9, fixtures.getMatchNumber());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    isSuccess = true;
                } catch (SQLException e) {
                }
            }
        }

        return isSuccess;
    }

    @Override
    public List<Fixtures> playerFixtures(String type, String key) {
        Map<String, Object> params = new HashMap<>();
        params.put("matchKey", key);
        params.put("matchKey", key);

        if (type.equalsIgnoreCase("mens")) {
            type = MENS;
        } else {
            type = MIXED;
        }

        String sql = "SELECT * FROM " + type + " where team1 = ? or team2 = ?";

        List<Fixtures> result = jdbcTemplate.query(sql, new Object[]{key, key}, new BeanPropertyRowMapper(Fixtures.class));

        return result;

    }

    @Override
    public List<Fixtures> playerKnockOutFixtures(String type, String teamId) {
        if (type.equalsIgnoreCase("mens")) {
            type = MENS_KNOCKOUT;
        } else {
            type = MIXED_KNOCKOUT;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("matchKey", teamId);
        params.put("matchKey", teamId);

        String sql = "SELECT * FROM " + type + " where team1 = ? or team2 = ?";

        List<Fixtures> result = jdbcTemplate.query(sql, new Object[]{teamId, teamId}, new BeanPropertyRowMapper(Fixtures.class));

        return result;
    }
}
