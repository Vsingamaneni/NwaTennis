package com.sports.cricket.service;

import com.sports.cricket.dao.ScheduleDao;
import com.sports.cricket.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService, Serializable {

    ScheduleDao scheduleDao;

    @Autowired
    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    @Override
    public List<Schedule> findAll(String name) {
        return scheduleDao.findAll(name);
    }

    @Override
    public List<Fixtures> fixturesAndResults(String type) {
        return scheduleDao.fixturesAndResults(type);
    }

    @Override
    public List<Fixtures> fixture(String type, String key){
        return scheduleDao.fixture(type, key);
    }

    @Override
    public List<Fixtures> multiFixture(String type, String key) {
        return scheduleDao.multiFixture(type, key);
    }

    @Override
    public boolean updateFixture(String type, Fixtures fixtures) {
        return scheduleDao.updateFixture(type, fixtures);
    }

    @Override
    public boolean updateMultiFixture(String type, Fixtures fixtures) {
        return scheduleDao.updateMultiFixture(type, fixtures);
    }

}
