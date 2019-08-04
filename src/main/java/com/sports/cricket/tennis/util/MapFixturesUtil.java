package com.sports.cricket.tennis.util;

import com.sports.cricket.model.Fixtures;
import org.springframework.util.CollectionUtils;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

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
                List<Fixtures> fixtures = entry.getValue();
                if (!CollectionUtils.isEmpty(fixtures)){
                    for (Fixtures game : fixtures){
                        if (null != game.getAwayTeamScore() && null != game.getAwayTeamScore()){
                            game.setResult(game.getHomeTeamScore() + " - " + game.getAwayTeamScore());
                            game.setViewStats(TENNIS_NIK+game.getStats());
                        } else {
                            game.setResult("vs");
                        }
                    }
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

    public static List<Map.Entry<String, Map<String, List<Fixtures>>>>  getSessionFixtures(List<Fixtures> fixturesList){
        Map<String, Map<String, List<Fixtures>>> sessionFixtures = new HashMap<>();
        String key;
        String time = null;
        if (!CollectionUtils.isEmpty(fixturesList)){
            for (Fixtures fixtures : fixturesList){
                if (fixtures.getTime().contains("AM")){
                    time = "Morning";
                } else if (fixtures.getTime().contains("PM")){
                    time = "Evening";
                }
                key = fixtures.getMatchdate();
                if (sessionFixtures.containsKey(key)){
                    Map<String, List<Fixtures>> currentSession = sessionFixtures.get(key);
                    if (currentSession.containsKey(time)){
                        List<Fixtures> timeFixtures = currentSession.get(time);
                        timeFixtures.add(fixtures);
                    } else {
                        List<Fixtures> sessionGames = new ArrayList<>();
                        sessionGames.add(fixtures);
                        currentSession.putIfAbsent(time, sessionGames);
                    }
                } else {
                    Map<String, List<Fixtures>> newSessionMap = new HashMap<>();
                    List<Fixtures> currentSession = new ArrayList<>();
                    currentSession.add(fixtures);
                    newSessionMap.putIfAbsent(time, currentSession);
                    sessionFixtures.putIfAbsent(fixtures.getMatchdate(), newSessionMap);
                }
            }
        }

        Collator collator = Collator.getInstance(Locale.US);
        collator.setStrength(Collator.PRIMARY);

        List<Map.Entry<String, Map<String, List<Fixtures>>>> result =
                sessionFixtures.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey(collator))
                        .collect(Collectors.toList());

        return result;
    }
}
