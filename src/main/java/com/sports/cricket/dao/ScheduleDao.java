package com.sports.cricket.dao;

import com.sports.cricket.model.*;

import java.util.List;

public interface ScheduleDao {

    List<Schedule> findAll(String name);

    List<Fixtures> fixturesAndResults(String type);

    List<Fixtures> fixture(String type, String key);

    List<Fixtures> multiFixture(String type, String key);

    boolean updateFixture(String type, Fixtures fixtures);

    boolean updateMultiFixture(String type, Fixtures fixtures);
}
