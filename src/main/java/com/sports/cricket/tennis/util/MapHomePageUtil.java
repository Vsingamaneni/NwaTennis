package com.sports.cricket.tennis.util;

import com.sports.cricket.model.Fixtures;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class MapHomePageUtil {

    public static int getGameWeek(List<Fixtures> fixturesList){
        if (!CollectionUtils.isEmpty(fixturesList)){
            for (Fixtures fixtures : fixturesList){
                if (fixtures.getIsActive() == 0){
                    continue;
                }
                return Integer.valueOf(fixtures.getMatchDay());
            }
        }
        return 0;
    }

    public static int getMixedDoublesGames(List<Fixtures> mixedDoublesFixtures){
        int count = 0;
        if (!CollectionUtils.isEmpty(mixedDoublesFixtures)){
            for (Fixtures fixtures : mixedDoublesFixtures){
                if (null != fixtures.getHomeTeamScore()){
                    count = count +1;
                }
            }
        }
        return count;
    }
}
