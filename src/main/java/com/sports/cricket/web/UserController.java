package com.sports.cricket.web;

import java.io.Serializable;
import java.util.*;

import javax.servlet.http.HttpSession;

import com.sports.cricket.dto.Teams;
import com.sports.cricket.model.*;
import com.sports.cricket.service.RegistrationService;
import com.sports.cricket.service.ScheduleService;
import com.sports.cricket.tennis.util.MapFixturesUtil;
import com.sports.cricket.tennis.util.MapHomePageUtil;
import com.sports.cricket.tennis.util.MapPlayerPool;
import com.sports.cricket.tennis.util.StandingsUtil;
import com.sports.cricket.validations.ErrorDetails;
import com.sports.cricket.validations.FormValidator;
import com.sports.cricket.validator.LoginValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import static com.sports.cricket.tennis.util.MapFixturesUtil.getQuartersFixtures;
import static com.sports.cricket.tennis.util.MapFixturesUtil.mapRoundOfSixteenFixtures;
import static com.sports.cricket.tennis.util.StandingsUtil.rankStandings;

@Controller
@ControllerAdvice
public class UserController implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    LoginValidator loginValidator;

    FormValidator formValidator = new FormValidator();

    private ScheduleService scheduleService;
    private RegistrationService registrationService;

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    // Show index page
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, HttpSession httpSession) {
        model.addAttribute("session", httpSession.getAttribute("session"));
        return "redirect:/index";
    }

    // Show index page
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String showHomePage(Model model, HttpSession httpSession) {
        model.addAttribute("session", httpSession.getAttribute("session"));

        List<Teams> mixedDoublesUsers = registrationService.getAllUsers("mixed");
        List<Teams> mensDoublesUsers = registrationService.getAllUsers("mens");

        List<Fixtures> mixedDoublesFixtures = scheduleService.fixturesAndResults("mixed");
        List<Fixtures> mensDoublesFixtures = scheduleService.fixturesAndResults("mens");

        int mixedCompletedCount = MapHomePageUtil.getMixedDoublesGames(mixedDoublesFixtures);
        int mensCompletedCount = MapHomePageUtil.getMixedDoublesGames(mensDoublesFixtures);

        int matchDay = MapHomePageUtil.getGameWeek(mensDoublesFixtures);

        model.addAttribute("matchDay", matchDay);

        model.addAttribute("mixedDoublesUsers", mixedDoublesUsers.size());
        model.addAttribute("mensDoublesUsers", mensDoublesUsers.size());

        model.addAttribute("mixedCompletedCount", mixedCompletedCount);
        model.addAttribute("mensCompletedCount", mensCompletedCount);

        model.addAttribute("mixedNonCompletedCount", mixedDoublesFixtures.size() - mixedCompletedCount);
        model.addAttribute("mensNonCompletedCount", mensDoublesFixtures.size() - mensCompletedCount);

        return "users/welcome";
    }

    // Validate the login details
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String user(ModelMap model
            , HttpSession httpSession) {

        model.addAttribute("session", httpSession.getAttribute("session"));
        logger.debug("saveOrUpdateLogin() : {}", "");

        UserLogin loginDetails = new UserLogin();

        model.addAttribute("loginDetails", loginDetails);

        return "users/login/login";
    }

    // Validate the login details
    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public String loginUser(@ModelAttribute("userLogin") UserLogin userLogin, ModelMap model
            , HttpSession httpSession) {

        if (null != httpSession.getAttribute("loginErrorDetails")) {
            httpSession.removeAttribute("loginErrorDetails");
        }

        if (null != model.get("loginErrorDetails")) {
            model.remove("loginErrorDetails");
        }

        List<ErrorDetails> loginErrorDetails = formValidator.isLoginValid(userLogin);

        if (null != loginErrorDetails
                && loginErrorDetails.size() > 0) {
            model.addAttribute("loginErrorDetails", loginErrorDetails);
            httpSession.setAttribute("loginErrorDetails", loginErrorDetails);
            return "redirect:/login";
        }

        logger.debug("saveOrUpdateLogin() : {}", "");

        UserLogin loginDetails = registrationService.loginUser(userLogin);

        if ( null != loginDetails
                && loginDetails.isLoginSuccess()) {

            model.addAttribute("session", loginDetails);
            httpSession.setAttribute("user", loginDetails.getFirstName());
            httpSession.setAttribute("userLastName", loginDetails.getLastName());
            httpSession.setAttribute("role", loginDetails.getRole());
            httpSession.setAttribute("session", userLogin);

            return "redirect:/";
        } else {
            List<ErrorDetails> errorDetailsList = new ArrayList<>();
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setErrorField("Password");
            errorDetails.setErrorMessage("Invalid email or password");
            errorDetailsList.add(errorDetails);
            httpSession.setAttribute("loginErrorDetails", errorDetailsList);
            return "redirect:/login";
        }
    }

    // Show All Users
    @RequestMapping(value = "/pool/{type}", method = RequestMethod.GET)
    public String showPool(@PathVariable("type") String type, Model model, HttpSession httpSession) {

        List<Teams> usersList = registrationService.getAllUsers(type);
        Map<String, List<Teams>> poolDetails = MapPlayerPool.getPool(usersList);
        model.addAttribute("poolDetails", poolDetails);
        model.addAttribute("session", httpSession.getAttribute("session"));
        return "users/members/users_pool";
    }

    // Show fixtures page
    @RequestMapping(value = "/fixtures/{type}", method = RequestMethod.GET)
    public String schedule(ModelMap model, @PathVariable("type") String type, HttpSession httpSession) {
        logger.debug("schedule()");

        model.addAttribute("msg", httpSession.getAttribute("msg"));
        httpSession.removeAttribute("msg");

        List<Fixtures> fixtures = scheduleService.fixturesAndResults(type);
        HashMap<String, List<Fixtures>> fixturesList = MapFixturesUtil.getFixtures(fixtures);
        MapFixturesUtil.setResults(fixturesList);
        model.addAttribute("fixturesList", fixturesList);
        model.addAttribute("session", httpSession.getAttribute("session"));

        List<Fixtures> knockOutFixtures;
        if (type.equalsIgnoreCase("mixed")) {
            knockOutFixtures = scheduleService.fixturesAndResults("mixed_knockouts");
        } else {
            knockOutFixtures = scheduleService.fixturesAndResults("mens_knockouts");
        }

        if (knockOutFixtures.size() > 0) {
            HashMap<String, List<Fixtures>> roundOfSixteen = mapRoundOfSixteenFixtures(knockOutFixtures);
            if (roundOfSixteen.size() > 0) {
                model.addAttribute("roundOfSixteen", roundOfSixteen);
            }

            List<Fixtures> quartersFixtures = getQuartersFixtures(knockOutFixtures, "qf");
            if (quartersFixtures.size() > 0) {
                model.addAttribute("quartersFixtures", quartersFixtures);
            }

            List<Fixtures> semisFixtures = getQuartersFixtures(knockOutFixtures, "sf");
            if (semisFixtures.size() > 0) {
                model.addAttribute("semisFixtures", semisFixtures);
            }

            List<Fixtures> finals = getQuartersFixtures(knockOutFixtures, "final");
            if (finals.size() == 1) {
                model.addAttribute("finals", finals.get(0));
            }

        }

        return "users/fixtures";
    }

    // Show fixtures page
    @RequestMapping(value = "/session/fixtures", method = RequestMethod.GET)
    public String fixturesPerSession(ModelMap model, HttpSession httpSession) {
        logger.debug("fixturesPerSession()");

        if (null != httpSession.getAttribute("session")) {

        List<Fixtures> fixtures = scheduleService.fixturesAndResults("mens");
        MapFixturesUtil.mapResults(fixtures);

        List<Fixtures> knockOutFixtures = scheduleService.fixturesAndResults("mens_knockouts");
        MapFixturesUtil.mapKnockOutResults(knockOutFixtures);

        List<Fixtures> combinedFixtures = new ArrayList<>(fixtures);
        if (knockOutFixtures.size() > 0) {
            combinedFixtures = MapFixturesUtil.addAllFixtures(knockOutFixtures, combinedFixtures);
        }

        LinkedHashMap<String, List<Fixtures>> mensSessionFixtures = MapFixturesUtil.mapSessionFixtures(combinedFixtures);

        List<Fixtures> mixedFixtures = scheduleService.fixturesAndResults("mixed");
        MapFixturesUtil.mapResults(mixedFixtures);

        List<Fixtures> mixedKnockOutFixtures = scheduleService.fixturesAndResults("mixed_knockouts");
        MapFixturesUtil.mapKnockOutResults(mixedKnockOutFixtures);

        List<Fixtures> mixedCombinedFixtures = new ArrayList<>(mixedFixtures);
        if (knockOutFixtures.size() > 0) {
            mixedCombinedFixtures = MapFixturesUtil.addAllFixtures(mixedKnockOutFixtures, mixedCombinedFixtures);
        }

        LinkedHashMap<String, List<Fixtures>> mixedSessionFixtures = MapFixturesUtil.mapSessionFixtures(mixedCombinedFixtures);

        model.addAttribute("session", httpSession.getAttribute("session"));
        model.addAttribute("mensSessionFixtures", mensSessionFixtures);
        model.addAttribute("mixedSessionFixtures", mixedSessionFixtures);

        return "users/sessionFixtures";
        } else {
            return "redirect:/index";
        }
    }

    // Show Standings page
    @RequestMapping(value = "/standings/{type}", method = RequestMethod.GET)
    public String standings(@PathVariable("type") String type, ModelMap model, HttpSession httpSession) {

        List<Fixtures> fixturesList = scheduleService.fixturesAndResults(type);

        List<Teams> usersList = registrationService.getAllUsers(type);

        Map<String, List<TennisStandings>> poolGroups = StandingsUtil.setTennisPoolStandings(usersList);
        poolGroups = StandingsUtil.mapFixturesToStandings(fixturesList, poolGroups);
        rankStandings(poolGroups);
        model.addAttribute("standingsMap", poolGroups);
        model.addAttribute("type", type);
        model.addAttribute("session", httpSession.getAttribute("session"));

        return "users/standings";
    }

    // Show Fixtures page
    @RequestMapping(value = "/fixtures/{type}/{teamId}", method = RequestMethod.GET)
    public String standings(@PathVariable("type") String type, @PathVariable("teamId") String teamId, ModelMap model, HttpSession httpSession) {

        List<Fixtures> groupStageFixture = scheduleService.playerFixtures(type, teamId);
        MapFixturesUtil.mapResults(groupStageFixture);

        List<Fixtures> knockOutFixture = scheduleService.playerKnockOutFixtures(type, teamId);

        if (knockOutFixture.size() > 0) {
            List<Fixtures> r16 = MapFixturesUtil.getQuartersFixtures(knockOutFixture, "r16");
            List<Fixtures> qf = MapFixturesUtil.getQuartersFixtures(knockOutFixture, "qf");
            List<Fixtures> sf = MapFixturesUtil.getQuartersFixtures(knockOutFixture, "sf");
            List<Fixtures> finals = MapFixturesUtil.getQuartersFixtures(knockOutFixture, "final");

            model.addAttribute("r16", r16);
            model.addAttribute("qf", qf);
            model.addAttribute("sf", sf);
            model.addAttribute("finals", finals.get(0));
        }

        model.addAttribute("fixturesList", groupStageFixture);
        model.addAttribute("type", type);
        model.addAttribute("session", httpSession.getAttribute("session"));

        return "users/playerFixtures";
    }

    // Save Result
    @RequestMapping(value = "/saveResult/{type}", method = RequestMethod.GET)
    public String saveResult(@PathVariable("type") String type, ModelMap model, HttpSession httpSession) {

        logger.debug("saveResult() : {}");
        model.addAttribute("msg", httpSession.getAttribute("msg"));
        httpSession.removeAttribute("msg");

        Fixtures fixtures = new Fixtures();
        fixtures.setMatchType(type);

        model.addAttribute("fixtures", fixtures);
        model.addAttribute("type", type);
        model.addAttribute("session", httpSession.getAttribute("session"));

        httpSession.setMaxInactiveInterval(5 * 60);
        return "users/updateResult";
    }

    @RequestMapping(value = "/fixture", method = RequestMethod.POST)
    public String updateMatchResult(@ModelAttribute("fixtures") Fixtures fixtures, ModelMap model, HttpSession httpSession) {
        model.addAttribute("session", httpSession.getAttribute("session"));

        if (null != fixtures
                && null != fixtures.getMatchKey()
                && !fixtures.getMatchKey().trim().equalsIgnoreCase("")) {

            List<Fixtures> matchDayFixture = scheduleService.fixture(fixtures.getMatchType(), fixtures.getMatchKey());

            if (!CollectionUtils.isEmpty(matchDayFixture) && matchDayFixture.size() > 0) {
                Fixtures retrievedFixture = matchDayFixture.get(0);
                retrievedFixture.setMatchType(fixtures.getMatchType());
                if (null != retrievedFixture.getHomeTeamScore()){
                    model.addAttribute("msg", retrievedFixture.getTeam1() + " (" + retrievedFixture.getHomeTeamScore() + ")   (" + retrievedFixture.getAwayTeamScore() + ") " + retrievedFixture.getTeam2() + " result is updated.  <br /> Contact Core team to modify/update.");
                }
                model.addAttribute("retrievedFixture", retrievedFixture);
            } else {
                List<Fixtures> knockOutFixture = scheduleService.multiFixture(fixtures.getMatchType(), fixtures.getMatchKey());
                if (!CollectionUtils.isEmpty(knockOutFixture)) {
                    Fixtures retrievedFixture = knockOutFixture.get(0);

                    retrievedFixture.setUpdateType("knockouts");

                    if (retrievedFixture.getMatchType().equalsIgnoreCase("r16")){
                        retrievedFixture.setMatchType(fixtures.getMatchType());
                        model.addAttribute("retrievedFixture", retrievedFixture);
                        return "users/matchResult";
                    }

                    if (null != retrievedFixture.getHomeTeamScore1()){
                        model.addAttribute("msg", "Match Result is already Updated. Contact Core team to modify/update.");
                    } else if (null != retrievedFixture) {
                        retrievedFixture.setMatchType(fixtures.getMatchType());
                        if (retrievedFixture.getSets() > 1) {
                            model.addAttribute("retrievedFixture", retrievedFixture);
                            return "users/multipleMatchResult";
                        }
                    }
                } else {
                    model.addAttribute("msg", "Invalid key. Enter the correct key or contact the core team!!");
                }
            }
        } else {
            model.addAttribute("msg", "Invalid key. Enter the correct key or contact the core team!!");
        }

        return "users/matchResult";
    }

    @RequestMapping(value = "/matchResult/update", method = RequestMethod.POST)
    public String saveMatchResult(@ModelAttribute("retrievedFixture") Fixtures fixtures, ModelMap model, HttpSession httpSession) {
        model.addAttribute("session", httpSession.getAttribute("session"));

        boolean isSuccess;
        if (null != fixtures && (null != fixtures.getHomeTeamScore() && !fixtures.getHomeTeamScore().equalsIgnoreCase("")
                && null != fixtures.getAwayTeamScore() && !fixtures.getAwayTeamScore().equalsIgnoreCase(""))) {
            isSuccess = scheduleService.updateFixture(fixtures.getMatchType(), fixtures);
        } else {
            httpSession.setAttribute("msg", "Invalid Input. Enter the correct score.!");
            return "redirect:/saveResult/" + fixtures.getMatchType();
        }

        if (!isSuccess) {
            return "redirect:/standings/" + fixtures.getMatchType();
        } else {
            httpSession.setAttribute("msg", "Match Result is updated successfully!!");
            return "redirect:/fixtures/" + fixtures.getMatchType();
        }
    }

    @RequestMapping(value = "/matchResult/multiUpdate", method = RequestMethod.POST)
    public String saveMultiSetsMatchResult(@ModelAttribute("retrievedFixture") Fixtures fixtures, ModelMap model, HttpSession httpSession) {
        model.addAttribute("session", httpSession.getAttribute("session"));
        boolean isSuccess = false;
        if (null != fixtures &&
                (null != fixtures.getHomeTeamScore() && !fixtures.getHomeTeamScore().equalsIgnoreCase("")
                        && null != fixtures.getAwayTeamScore() && !fixtures.getAwayTeamScore().equalsIgnoreCase("")) ||
                (null != fixtures.getHomeTeamScore1() && !fixtures.getHomeTeamScore1().equalsIgnoreCase(""))){
            isSuccess = scheduleService.updateMultiFixture(fixtures.getMatchType(), fixtures);
        } else {
            model.addAttribute("msg", "Please enter valid score.!");
            return "redirect:/saveResult/"+fixtures.getMatchType();
        }

        if (!isSuccess) {
            return "redirect:/standings/"+fixtures.getMatchType();
        } else {
            return "redirect:/fixtures/"+fixtures.getMatchType();
        }
    }

    @RequestMapping(value = "/knockouts/{type}", method = RequestMethod.GET)
    public String saveMultiSetsMatchResult(@PathVariable("type") String type, ModelMap model, HttpSession httpSession) {
        model.addAttribute("session", httpSession.getAttribute("session"));

        List<Fixtures> fixturesList = scheduleService.fixturesAndResults(type);
        List<Teams> usersList = registrationService.getAllUsers(type);
        Map<String, List<TennisStandings>> poolGroups = StandingsUtil.setTennisPoolStandings(usersList);
        poolGroups = StandingsUtil.mapFixturesToStandings(fixturesList, poolGroups);
        rankStandings(poolGroups);

        if (type.equalsIgnoreCase("mens")) {
            List<TennisStandings> topStandings = StandingsUtil.getTop16(poolGroups);
            List<TennisStandings> topHalf = StandingsUtil.getTop8(topStandings, 0, 7);
            List<TennisStandings> bottomHalf = StandingsUtil.getBottom8(topStandings, 8, 15);

            model.addAttribute("topHalf", topHalf);
            model.addAttribute("bottomHalf", bottomHalf);
            return "users/knockouts/mensKnockOuts";
        } else {
            List<TennisStandings> topStandings = StandingsUtil.getTop16(poolGroups);

            List<TennisStandings> topHalf = StandingsUtil.getTop8(topStandings, 0, 1);
            List<TennisStandings> bottomHalf = StandingsUtil.getBottom8(topStandings, 2, 3);

            model.addAttribute("topHalf", topHalf);
            model.addAttribute("bottomHalf", bottomHalf);
            return "users/knockouts/mixedKnockOuts";

        }
    }

    // Show Rules
    @RequestMapping(value = "/rules", method = RequestMethod.GET)
    public String showRules(ModelMap model, HttpSession httpSession){
        logger.debug("show Rules");
        UserLogin userLogin = (UserLogin) httpSession.getAttribute("session");
        model.addAttribute("session", userLogin);
        httpSession.setAttribute("session", userLogin);
        httpSession.setMaxInactiveInterval(5 * 60);
        return "users/rules";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logoutUser(Model model, HttpSession httpSession) {
        logger.debug("logoutUser()");
        httpSession.invalidate();
        return "redirect:/";
    }

}