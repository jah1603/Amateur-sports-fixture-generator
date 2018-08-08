package controllers;

import db.DBFixture;
import db.DBHelper;
import db.DBLeague;
import models.*;
import org.apache.velocity.runtime.log.LogSystem;
import org.junit.Test;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

import static java.util.EnumSet.allOf;
import static spark.Spark.get;
import static spark.Spark.post;

public class LeagueController {
    public LeagueController(){
        this.setUpEndPoints();
        }

        private void setUpEndPoints() {

            get("/leagues", (req, res) -> {
                Map<String, Object> model = new HashMap<>();
                List<League> leagues = DBHelper.getAll(League.class);
                model.put("leagues", leagues);
                List<Fixture> fixtures = DBHelper.getAll(Fixture.class);
                model.put("fixtures", fixtures);
                List<Team> teams = DBHelper.getAll(Team.class);
                model.put("teams", teams);
                model.put("template", "templates/leagues/index.vtl");
                return new ModelAndView(model, "templates/layout.vtl");
            }, new VelocityTemplateEngine());

            get("/leagues/new", (req, res) -> {
                Map<String, Object> model = new HashMap<>();
                model.put("template", "templates/leagues/new.vtl");

                List<Team> allTeams = DBHelper.getAll(Team.class);
                model.put("allTeams", allTeams);

                EnumSet leagueTypes = EnumSet.allOf(LeagueType.class);
                model.put("leagueTypes", leagueTypes);

                return new ModelAndView(model, "templates/layout.vtl");

            }, new VelocityTemplateEngine());


            post("/leagues", (req, res) -> {
                String name = req.queryParams("name");
                LeagueType leagueType = LeagueType.valueOf(req.queryParams("league_type"));
                String region = req.queryParams("region");
                League leagueToSave = new League(name, LeagueType.valueOf(req.queryParams("league_type")), region);
                DBHelper.save(leagueToSave);

                res.redirect("/leagues");
                return null;

            }, new VelocityTemplateEngine());

            get("/leagues/:id", (req, res) -> {
                Map<String, Object> model = new HashMap<>();
                int leagueId = Integer.parseInt(req.params(":id"));

                List<League> allLeauges = DBHelper.getAll(League.class);
                model.put("leagues", allLeauges);

                League league = DBHelper.find(leagueId, League.class);
                model.put("league", league);
                List <FootballTeam> sortedLeague =  DBLeague.sortLeagueByPoints(league);
                model.put("teams", sortedLeague);

                for (FootballTeam team : sortedLeague){
                    team.setLeaguePosition(sortedLeague.indexOf(team) + 1);
                }


                List<Fixture> fixtures = DBLeague.getFixturesForLeague(league);
                model.put("fixtures", fixtures);

//                for (Fixture fixture: fixtures){
//                    fixture.setLeague(league);
//                }

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

//                for (int week = 0; week < (2 * ((league.teamCount()) -1)); week++) {
//                    for (int match = 0; week < (((league.teamCount()) / 2) - 1); match++) {
//                            if (league.ghostInNewLeague()) {
//                                league.returnSeason().get(week).get(match).setMatch(match - 1);
//                            }
//                        }
//                    }

//                for (int week = 0; week < (2 * ((league.teamCount()) -1)); week++) {
//                    for (int match = 0; week < (((league.teamCount()) / 2) - 1); match++) {
//                        if (league.ghostInLeague()) {
//                            league.returnSeason().get(week).get(match).setMatch(match - 1);
//                        }
//                    }
//                }


                model.put("template", "templates/leagues/view.vtl");

                DBLeague.sortLeagueByPoints(league);

                return new ModelAndView(model, "templates/layout.vtl");


            }, new VelocityTemplateEngine());


            get(":id/leagues/:id", (req, res) -> {
                Map<String, Object> model = new HashMap<>();
                int leagueId = Integer.parseInt(req.params(":id"));

                List<League> allLeauges = DBHelper.getAll(League.class);
                model.put("leagues", allLeauges);

                League league = DBHelper.find(leagueId, League.class);
                model.put("league", league);
                List <FootballTeam> sortedLeague =  DBLeague.sortLeagueByPoints(league);
                model.put("teams", sortedLeague);

                for (FootballTeam team : sortedLeague){
                    team.setLeaguePosition(sortedLeague.indexOf(team) + 1);
                }

                List<Fixture> fixtures = DBLeague.getFixturesForLeague(league);
                model.put("fixtures", fixtures);

                for (Fixture fixture: fixtures){
                    fixture.setLeague(league);
                }

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

//                for (int week = 0; week < (2 * ((league.teamCount()) -1)); week++) {
//                    for (int match = 0; week < (((league.teamCount()) / 2) - 1); match++) {
//                            if (league.ghostInNewLeague()) {
//                                league.returnSeason().get(week).get(match).setMatch(match - 1);
//                            }
//                        }
//                    }

//                for (int week = 0; week < (2 * ((league.teamCount()) -1)); week++) {
//                    for (int match = 0; week < (((league.teamCount()) / 2) - 1); match++) {
//                        if (league.ghostInLeague()) {
//                            league.returnSeason().get(week).get(match).setMatch(match - 1);
//                        }
//                    }
//                }


                model.put("template", "templates/leagues/view.vtl");

                DBLeague.sortLeagueByPoints(league);

                return new ModelAndView(model, "templates/layout.vtl");


            }, new VelocityTemplateEngine());



            get("/leagues/:id/edit", (req, res) -> {
                Map<String, Object> model = new HashMap<>();
                int leagueId = Integer.parseInt(req.params(":id"));
                League leagueToEdit = DBHelper.find(leagueId, League.class);
                model.put("league", leagueToEdit);

                List<Team> allTeams = DBHelper.getAll(Team.class);
                model.put("allTeams", allTeams);

                EnumSet leagueTypes = EnumSet.allOf(LeagueType.class);
                model.put("leagueTypes", leagueTypes);


                List<Team> teams = DBLeague.getTeamsForALeaugue(leagueToEdit);
                model.put("leaguesTeams", teams);

                List<Fixture> leaguesFixtures = DBLeague.getFixturesForLeague(leagueToEdit);
                model.put("leaguesFixtures", leaguesFixtures);

                model.put("template", "/templates/leagues/edit.vtl");
                return new ModelAndView(model, "templates/layout.vtl");

            }, new VelocityTemplateEngine());

            post("/leagues/:id/update", (req, res) -> {

                EnumSet leagueTypes = EnumSet.allOf(LeagueType.class);

                String name = req.queryParams("name");
                LeagueType leagueType = LeagueType.valueOf("league_type");
                String region = req.queryParams("region");

                int leagueId = Integer.parseInt(req.params(":id"));
                League league = DBHelper.find(leagueId, League.class);

                league.setName(name);
                league.getLeagueType();
                league.setLeagueType(leagueType);
                league.setRegion(region);
                DBHelper.update(league);

                res.redirect("/leagues");
                return null;

            }, new VelocityTemplateEngine());

            post("/leagues/:id/addTeams", (req, res) -> {
                int leagueId = Integer.parseInt(req.params(":id"));
                League league = DBHelper.find(leagueId, League.class);


                res.redirect("/leagues/:id");
                return null;

            }, new VelocityTemplateEngine());

            post("/leagues/:id/delete", (req, res) -> {
                int leagueToDeleteId = Integer.parseInt(req.params("id"));
                League leagueToDelete = DBHelper.find(leagueToDeleteId, League.class);
                List<Team> teams = DBLeague.getTeamsForALeaugue(leagueToDelete);
                List<Fixture> fixtures = DBLeague.getFixturesForLeague(leagueToDelete);
                DBHelper.delete(leagueToDelete);
                DBHelper.update(teams);
                DBHelper.update(fixtures);

                res.redirect("/leagues");
                return null;
            }, new VelocityTemplateEngine());


            get("/leagues/:id/generate", (req, res) -> {
                Map<String, Object> model = new HashMap<>();
                int leagueId = Integer.parseInt(req.params("id"));
                League league = DBHelper.find(leagueId, League.class);

                league.generateFixtures(true);
                DBFixture.saveFixturesForLeague(league);

                List<Fixture> fixtures = DBFixture.sortLeaguesFixturesByWeeks(league);
                model.put("fixtures", fixtures);

                for (Fixture fixtureToUpdate : fixtures){
                    fixtureToUpdate.setLeague(league);
                }

                List<Team> teams = DBLeague.getTeamsForALeaugue(league);
                model.put("teams", teams);
                res.redirect("/leagues/"+ leagueId);
                return null;
            }, new VelocityTemplateEngine());

            get("/leagues/:id/fixtures", (req,res) -> {
                Map<String, Object> model = new HashMap<>();
                int leagueId = Integer.parseInt(req.params(":id"));
                League league = DBHelper.find(leagueId, League.class);
                model.put("leagues", league);
                List<Team> teams = DBLeague.getTeamsForALeaugue(league);
                model.put("teams", teams);

                List<Fixture> leaguesFixtures = DBLeague.getFixturesForLeague(league);
                model.put("fixtures", leaguesFixtures);
                res.redirect("/" +leagueId +"/fixtures");
                return null;
            }, new VelocityTemplateEngine());

        }

}
