package com.sports.cricket.tennis.util;

import com.sports.cricket.model.Fixtures;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapFixturesUtil {

    public static HashMap<String, List<Fixtures>> getFixtures(List<Fixtures> fixturesList){
        HashMap<String, List<Fixtures>> fixturesHashMap = new HashMap<>();

        if (!CollectionUtils.isEmpty(fixturesList)){
            for (Fixtures fixtures : fixturesList){
                if (fixturesHashMap.containsKey(fixtures.getPool())){
                    List<Fixtures> addedFixtures = fixturesHashMap.get(fixtures.getPool());
                    addedFixtures.add(fixtures);
                } else {
                    List<Fixtures> newFixtures = new ArrayList<>();
                    newFixtures.add(fixtures);
                    fixturesHashMap.putIfAbsent(fixtures.getPool(), newFixtures);
                }
            }
        }
        return fixturesHashMap;
    }

    public static HashMap<String, List<Fixtures>> mapRoundOfSixteenFixtures(List<Fixtures> fixturesList){
        HashMap<String, List<Fixtures>> roundOfSixteen = new HashMap<>();
        if (!CollectionUtils.isEmpty(fixturesList)){
            for (Fixtures fixtures : fixturesList){
                if (fixtures.getMatchType().equalsIgnoreCase("r16")) {
                    if (!roundOfSixteen.containsKey(fixtures.getPool())) {
                        List<Fixtures> newFixtures = new ArrayList<>();
                        newFixtures.add(fixtures);
                        roundOfSixteen.putIfAbsent(fixtures.getPool(), newFixtures);
                    } else {
                        List<Fixtures> addedFixtures = roundOfSixteen.get(fixtures.getPool());
                        addedFixtures.add(fixtures);
                    }
                }
            }
        }
        return roundOfSixteen;
    }

    public static List<Fixtures> getQuartersFixtures(List<Fixtures> fixturesList, String round){
        List<Fixtures> quartersFixtures = new ArrayList<>();
        if (!CollectionUtils.isEmpty(fixturesList)){
            for (Fixtures fixtures : fixturesList){
                if (fixtures.getMatchType().equalsIgnoreCase(round)){
                    quartersFixtures.add(fixtures);
                }
            }
        }
        return quartersFixtures;
    }
}