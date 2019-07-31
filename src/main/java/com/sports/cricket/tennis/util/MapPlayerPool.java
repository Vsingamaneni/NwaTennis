package com.sports.cricket.tennis.util;

import com.sports.cricket.dto.Teams;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapPlayerPool {

    public static Map<String, List<Teams>> getPool(List<Teams> teamsList){
        Map<String, List<Teams>> poolDetails = new HashMap<>();

        if (!CollectionUtils.isEmpty(teamsList)){
            for (Teams team : teamsList){
                if (poolDetails.containsKey(team.getPool())){
                    List<Teams> teamsInPool = poolDetails.get(team.getPool());
                    teamsInPool.add(team);
                } else {
                    List<Teams> newPool = new ArrayList<>();
                    newPool.add(team);
                    poolDetails.putIfAbsent(team.getPool(), newPool);
                }
            }
        }
    return poolDetails;
    }
}
