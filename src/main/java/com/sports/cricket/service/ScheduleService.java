package com.sports.cricket.service;

import com.sports.cricket.model.*;

import java.util.List;

public interface ScheduleService {

    List<Schedule> findAll(String name);

    List<Fixtures> fixturesAndResults(String type);

    List<Fixtures> fixture(String type, String key);

    List<Fixtures> multiFixture(String type, String key);

    boolean updateFixture(String type, Fixtures fixtures);

    boolean updateMultiFixture(String type, Fixtures fixtures);

    List<Fixtures> playerFixtures(String type, String key);

    List<Fixtures> playerKnockOutFixtures(String type, String key);

}
