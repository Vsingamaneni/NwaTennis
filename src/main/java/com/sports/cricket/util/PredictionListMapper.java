package com.sports.cricket.util;

import com.sports.cricket.model.*;
import com.sports.cricket.service.RegistrationService;
import com.sports.cricket.service.ScheduleService;
import com.sports.cricket.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

public class PredictionListMapper implements Serializable {

    /*
     * get schedule deadlines
     * get match result updated
     * get standings updated
     */
    public static List<MatchDetails> generateNewsFeed(List<Schedule> scheduleList) throws ParseException {
        List<MatchDetails> matchDetailsList = new ArrayList<>();
        MatchDetails matchDetails;
        for (Schedule schedule : scheduleList){
            if (null == schedule.getWinner()){
                if (ValidateDeadline.isDeadLineReached(schedule.getStartDate())){
                    matchDetails = new MatchDetails();
                    matchDetails.setMatch("Predictions for "+ schedule.getHomeTeam() + " vs " + schedule.getAwayTeam() + " is now closed");
                    matchDetailsList.add(matchDetails);
                } else {
                    matchDetails = new MatchDetails();
                    matchDetails.setMatch("Predictions for " + schedule.getHomeTeam() + " vs " + schedule.getAwayTeam() + " is open");
                    matchDetailsList.add(matchDetails);
                }
            } else {

                matchDetails = new MatchDetails();
                matchDetails.setMatch("Predictions for "+ schedule.getHomeTeam() + " vs " + schedule.getAwayTeam() + " is now closed");
                matchDetailsList.add(matchDetails);

                matchDetails = new MatchDetails();
                matchDetails.setMatch("Result for " + schedule.getHomeTeam() + " vs " + schedule.getAwayTeam() + " is updated");
                matchDetailsList.add(matchDetails);

            }
        }
        return matchDetailsList;
    }

}
