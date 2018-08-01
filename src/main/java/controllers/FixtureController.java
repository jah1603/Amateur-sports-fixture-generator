package controllers;

import db.DBFixture;
import db.DBHelper;
import db.DBLeague;
import models.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class FixtureController {

    {
        this.setUpEndpoints();
    }

    private void setUpEndpoints() {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        //READ
        get("/fixtures", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/fixtures/index.vtl");

            List<Fixture> fixtures = DBFixture.sortFixturesByWeeks();
            model.put("fixtures", fixtures);

            for (Fixture fixture: fixtures){
                if (fixture.getLeague().ghostInLeague())
                {
                    fixture.setMatch(fixture.getMatch() - 1);
                }
            }

            for (Fixture fixture: fixtures){
                if (fixture.getLeague().ghostInNewLeague())
                {
                    fixture.setMatch(fixture.getMatch() - 1);
                }
            }

            List<League> leagues = DBHelper.getAll(League.class);
            model.put("leagues", leagues);

            List<MatchReport> matchReports = DBHelper.getAll(MatchReport.class);
            model.put("matchreports", matchReports);

            List<FootballTeam> footballTeams = DBHelper.getAll(FootballTeam.class);
            model.put("footballteams", footballTeams);


            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);


        //READ
        get("/:id/fixtures", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/fixtures/leagueindex.vtl");

            List<Fixture> fixtures = DBFixture.sortFixturesByWeeks();


            for (Fixture fixture: fixtures){
                fixture.setMatch(fixture.getMatch() - 1);
            }

            model.put("fixtures", fixtures);

            int leagueId = Integer.parseInt(req.params(":id"));
            League league = DBHelper.find(leagueId, League.class);

            model.put("league", league);

            List <League> allLeagues = DBHelper.getAll(League.class);
            model.put("allLeagues", allLeagues);

            List <Fixture> leaguesFixtures = league.getFixtures();

            model.put("leaguesFixtures", leaguesFixtures);


            List<MatchReport> matchReports = DBHelper.getAll(MatchReport.class);
            model.put("matchreports", matchReports);

            List<FootballTeam> footballTeams = DBHelper.getAll(FootballTeam.class);
            model.put("footballteams", footballTeams);


            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);



        //UPDATE
        post("/fixtures/:id", (req, res) -> {
            String homegoals = (req.queryParams("homegoals"));
            String awaygoals = (req.queryParams("awaygoals"));


            int fixtureId = Integer.parseInt(req.params(":id"));
            Fixture fixture = DBHelper.find(fixtureId, Fixture.class);

            fixture.setHomeGoals(homegoals);
            fixture.setAwayGoals(awaygoals);
            DBHelper.update(fixture);


            FootballTeam homeTeam = (FootballTeam) fixture.returnHomeTeam();
            FootballTeam awayTeam = (FootballTeam) fixture.returnAwayTeam();
            homeTeam.setGoalsScored(Integer.parseInt(homegoals));
            awayTeam.setGoalsScored(Integer.parseInt(awaygoals));
            homeTeam.setGoalsConceded(Integer.parseInt(awaygoals));
            awayTeam.setGoalsConceded(Integer.parseInt(homegoals));
            fixture.inputGoalsToGenerateResult(Integer.parseInt(homegoals), Integer.parseInt(awaygoals));

            fixture.updateGamesPlayed(homegoals,awaygoals);


            DBHelper.update(homeTeam);
            DBHelper.update(awayTeam);


            League league = homeTeam.getLeague();
            DBHelper.update(league);


            res.redirect("/fixtures");
            return null;
        }, velocityTemplateEngine);

        get("/fixtures/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int legueId = Integer.parseInt(req.params(":id"));
            League league = DBHelper.find(legueId, League.class);
            List <Fixture> leaguesFixtures = DBLeague.getFixturesForLeague(league);
            leaguesFixtures = DBFixture.sortFixturesByWeeks();
            model.put("league", league);
            model.put("fixtures", leaguesFixtures);
            model.put("template", "templates/fixtures/leagueindex.vtl");
            return new ModelAndView(model,"templates/layout.vtl");


        }, new VelocityTemplateEngine());


        //UPDATE
        post("/:leagueId/fixtures/:id", (req, res) -> {

            int leagueId = Integer.parseInt(req.params(":leagueid"));
            League league = DBHelper.find(leagueId, League.class);
            String homegoals = (req.queryParams("homegoals"));
            String awaygoals = (req.queryParams("awaygoals"));


            int fixtureId = Integer.parseInt(req.params(":id"));
            Fixture fixture = DBHelper.find(fixtureId, Fixture.class);

            fixture.setHomeGoals(homegoals);
            fixture.setAwayGoals(awaygoals);
            DBHelper.update(fixture);


            FootballTeam homeTeam = (FootballTeam) fixture.returnHomeTeam();
            FootballTeam awayTeam = (FootballTeam) fixture.returnAwayTeam();
            homeTeam.setGoalsScored(Integer.parseInt(homegoals));
            awayTeam.setGoalsScored(Integer.parseInt(awaygoals));
            homeTeam.setGoalsConceded(Integer.parseInt(awaygoals));
            awayTeam.setGoalsConceded(Integer.parseInt(homegoals));
            fixture.inputGoalsToGenerateResult(Integer.parseInt(homegoals), Integer.parseInt(awaygoals));

            fixture.updateGamesPlayed(homegoals,awaygoals);

            fixture.updateGamesPlayed(homegoals, awaygoals);


            DBHelper.update(homeTeam);
            DBHelper.update(awayTeam);


            league = homeTeam.getLeague();
            DBHelper.update(league);


            res.redirect("/" +leagueId + "/fixture");
            return null;
        }, velocityTemplateEngine);





    }



}



