package com.sports.cricket.tennis.util;

import com.sports.cricket.model.Fixtures;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapFixturesUtil {

    public static final String TENNIS_NIK ="http://ts.nik.space/game/";

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

    public static void setResults(HashMap<String, List<Fixtures>> fixturesList){
        if (fixturesList.size() > 0){
            for (Map.Entry<String, List<Fixtures>> entry : fixturesList.entrySet()) {
                mapListFixtures(entry.getValue());
            }
        }
    }

    public static void mapListFixtures(List<Fixtures> fixtures){
        if (!CollectionUtils.isEmpty(fixtures)){
            for (Fixtures game : fixtures){
                game.setViewStats(TENNIS_NIK+game.getStats());
                if (null != game.getAwayTeamScore() && null != game.getAwayTeamScore()){
                    game.setResult(game.getHomeTeamScore() + " - " + game.getAwayTeamScore());
                } else {
                    game.setResult("vs");
                }
            }
        }
    }

    public static void mapResults(List<Fixtures> fixturesList){
        if (!CollectionUtils.isEmpty(fixturesList)){
            for (Fixtures fixtures : fixturesList){
                if (null == fixtures.getHomeTeamScore() && null == fixtures.getAwayTeamScore()){
                    fixtures.setResult("vs");
                } else {
                    fixtures.setResult(fixtures.getHomeTeamScore() + " - " + fixtures.getAwayTeamScore());
                }
            }
        }
    }

    public static void mapKnockOutResults(List<Fixtures> knockOutFixtures) {
        if (!CollectionUtils.isEmpty(knockOutFixtures)) {
            for (Fixtures fixtures : knockOutFixtures) {
                if ((null == fixtures.getAwayTeamScore() && null == fixtures.getAwayTeamScore())
                        || (null == fixtures.getHomeTeamScore1() && null == fixtures.getAwayTeamScore1())) {
                    fixtures.setResult("vs");
                } else {
                    if (null != fixtures.getAwayTeamScore() && null != fixtures.getAwayTeamScore()) {
                        fixtures.setResult(fixtures.getHomeTeamScore() + " - " + fixtures.getAwayTeamScore());
                    } else if (null == fixtures.getHomeTeamScore1() && null == fixtures.getAwayTeamScore1()) {
                        fixtures.setResult(fixtures.getHomeTeamScore1() + " - " + fixtures.getAwayTeamScore1() + " <br />"
                                        + fixtures.getHomeTeamScore2() + " - " + fixtures.getAwayTeamScore2() + " <br />");
                        if (null != fixtures.getHomeTeamScore3()){
                            fixtures.setResult(fixtures.getResult() + fixtures.getHomeTeamScore3() + " - " + fixtures.getAwayTeamScore3());
                        }
                    }
                }
            }
        }
    }

    public static List<Fixtures> addAllFixtures(List<Fixtures> knockoutFixtures, List<Fixtures> scheduleFixtures){
        for (Fixtures fixtures : knockoutFixtures){
            scheduleFixtures.add(fixtures);
        }
        return scheduleFixtures;
    }

    public static LinkedHashMap<String, List<Fixtures>> mapSessionFixtures(List<Fixtures> fixturesList){
        Map<Integer, List<Fixtures>> sessionFixtures = new HashMap<>();
        if (!CollectionUtils.isEmpty(fixturesList)){
            for (Fixtures fixtures : fixturesList){
                if (fixtures.getSession() == 0 ){
                    continue;
                }
                if (sessionFixtures.containsKey(fixtures.getSession())){
                    sessionFixtures.get(fixtures.getSession()).add(fixtures);
                } else {
                    List<Fixtures> sessionDetails = new ArrayList<>();
                    sessionDetails.add(fixtures);
                    sessionFixtures.put(fixtures.getSession(), sessionDetails);
                }
            }
        }

        sessionFixtures = sortByKeys(sessionFixtures);

        return replaceSessionNumbers(sessionFixtures);
    }

    public static <K, V> Map<K,V> sortByKeys(Map<K,V> unsortedMap)
    {
        // construct a TreeMap from given Map and return it
        return new TreeMap<>(unsortedMap);
    }

    public static LinkedHashMap<String, List<Fixtures>> replaceSessionNumbers(Map<Integer, List<Fixtures>> sessionFixtures){
        LinkedHashMap<String, List<Fixtures>> finalSessionDetails = new LinkedHashMap<>();
        for (Map.Entry<Integer, List<Fixtures>> entry : sessionFixtures.entrySet()){
            List<Fixtures> fixturesList = entry.getValue();
            if (fixturesList.size() > 0){
                String key = null;
                for (Fixtures fixtures : fixturesList){
                    key = fixtures.getMatchdate() + getTimeSlot(fixtures);
                    break;
                }
                if (!finalSessionDetails.containsKey(key)){
                    finalSessionDetails.putIfAbsent(key,fixturesList );
                }
            }
        }
        return finalSessionDetails;
    }

    public static String getTimeSlot(Fixtures fixtures){
        if (fixtures.getTime().contains("AM")){
            return " (Morning)";
        } else if (fixtures.getTime().contains("PM")){
            return " (Evening)";
        }
        return "";
    }

}
