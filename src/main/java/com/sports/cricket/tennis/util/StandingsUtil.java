package com.sports.cricket.tennis.util;

import com.sports.cricket.dto.Teams;
import com.sports.cricket.model.Fixtures;
import com.sports.cricket.model.TennisStandings;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class StandingsUtil {

    public static  Map<String, List<TennisStandings>> setTennisPoolStandings(List<Teams> teamsList){
        Map<String, List<TennisStandings>> standingsMap = new HashMap<>();
        TennisStandings tennisStandings;
        if (!CollectionUtils.isEmpty(teamsList)){
            for (Teams teams : teamsList){
                if (standingsMap.containsKey(teams.getPool())){
                    List<TennisStandings> newPoolStandings = standingsMap.get(teams.getPool());
                    tennisStandings = new TennisStandings();
                    tennisStandings.setTeamId(teams.getTeamId());
                    tennisStandings.setTeam(teams.getFirstName() + " - " + teams.getFirstName1());
                    newPoolStandings.add(tennisStandings);
                    if (null != teams.getRank()){
                        tennisStandings.setRank(Integer.valueOf(teams.getRank()));
                    }
                } else {
                    tennisStandings = new TennisStandings();
                    tennisStandings.setTeamId(teams.getTeamId());
                    tennisStandings.setTeam(teams.getFirstName() + " - " + teams.getFirstName1());

                    if (null != teams.getRank()){
                        tennisStandings.setRank(Integer.valueOf(teams.getRank()));
                    }

                    List<TennisStandings> newPoolStandings = new ArrayList<>();
                    newPoolStandings.add(tennisStandings);

                    standingsMap.putIfAbsent(teams.getPool(), newPoolStandings);
                }

            }
        }
        return standingsMap;
    }

    public static Map<String, List<TennisStandings>> mapFixturesToStandings(List<Fixtures> fixturesList, Map<String, List<TennisStandings>> fixturesHashMap){

        fixturesList = getResultsFixtures(fixturesList);

        if (!CollectionUtils.isEmpty(fixturesList)){
            for (Fixtures fixtures : fixturesList){
                if (fixturesHashMap.containsKey(fixtures.getPool())){
                    List<TennisStandings> tennisStandings = fixturesHashMap.get(fixtures.getPool());

                    if (!CollectionUtils.isEmpty(tennisStandings)){
                        for (TennisStandings team : tennisStandings) {
                            if (fixtures.getSets() == 1) {
                                if (team.getTeam().equalsIgnoreCase(fixtures.getTeam1())) {
                                    team.setPlayed(team.getPlayed() + 1);
                                    team.setMargin(team.getMargin() + (Integer.valueOf(fixtures.getHomeTeamScore()) - Integer.valueOf(fixtures.getAwayTeamScore())));
                                    if (Integer.valueOf(fixtures.getHomeTeamScore()) > Integer.valueOf(fixtures.getAwayTeamScore())) {
                                        team.setWon(team.getWon() + 1);
                                        team.setPoints(team.getPoints() + 2);
                                    } else {
                                        team.setLost(team.getLost() + 1);
                                    }
                                } else if (team.getTeam().equalsIgnoreCase(fixtures.getTeam2())) {
                                    team.setPlayed(team.getPlayed() + 1);
                                    team.setMargin(team.getMargin() + (Integer.valueOf(fixtures.getAwayTeamScore()) - Integer.valueOf(fixtures.getHomeTeamScore())));
                                    if (Integer.valueOf(fixtures.getAwayTeamScore()) > Integer.valueOf(fixtures.getHomeTeamScore())) {
                                        team.setWon(team.getWon() + 1);
                                        team.setPoints(team.getPoints() + 2);
                                    } else {
                                        team.setLost(team.getLost() + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return fixturesHashMap;
    }

    public static List<Fixtures> getResultsFixtures(List<Fixtures> fixturesList){
        List<Fixtures> completedFixtures = new ArrayList<>();

        if (!CollectionUtils.isEmpty(fixturesList)){
            for (Fixtures fixtures : fixturesList){
                if (fixtures.getSets() == 1) {
                    if (null != fixtures.getHomeTeamScore() && null != fixtures.getAwayTeamScore()) {
                        completedFixtures.add(fixtures);
                    }
                }
            }
        }
        return completedFixtures;
    }

    public static void rankStandings(Map<String, List<TennisStandings>> standings){

        for (List<TennisStandings>  standingsList : standings.values()) {
            boolean isSetRank = false;
            for (TennisStandings tennisStandings : standingsList) {
                if (tennisStandings.getRank() == 0) {
                    isSetRank = true;
                }
            }
            if (isSetRank) {
                sortAndSetRank(standingsList);
            } else {
                Collections.sort(standingsList, RANK);
            }
        }
    }

    public static void sortAndSetRank(List<TennisStandings>  standingsList){
        Collections.sort(standingsList, SORT_POINTS_MARGIN);
        AtomicInteger rank = new AtomicInteger(1);
        standingsList.stream().forEach(tennisStandings -> {
            tennisStandings.setRank(rank.get());
            rank.getAndIncrement();
        });
    }

    private static final Comparator<TennisStandings> SORT_POINTS_MARGIN = Comparator
            .comparing(TennisStandings::getPoints)
            .thenComparingInt(TennisStandings::getMargin).reversed();

    private static final Comparator<TennisStandings> RANK = Comparator
            .comparing(TennisStandings::getRank);

}
