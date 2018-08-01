package controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import db.DBHelper;
import db.DBLeague;
import models.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class FootballTeamController {

    public FootballTeamController() {
        this.setUpEndPoints();

    }

    //Get index list of teams//

    private void setUpEndPoints() {
        get("/footballteams", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            model.put("template", "templates/footballteams/index.vtl");
            List<League> leages = DBHelper.getAll(League.class);
            model.put("leagues", leages);
            List<FootballTeam> footballTeamList = DBHelper.getAll(FootballTeam.class);
            model.put("teamList", footballTeamList);

            List<Manager> managers = DBHelper.getAll(Manager.class);
            model.put("manager", managers);

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        //new team

        get("/footballteam/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<League> leagues = DBHelper.getAll(League.class);
            model.put("leagues", leagues);

            List<Manager> managers = DBHelper.getAll(Manager.class);
            model.put("managers", managers);

            model.put("template", "templates/footballteams/create.vtl");

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


        //post new team
        post("/footballteams", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            //getting league id
            int leagueId = Integer.parseInt(req.queryParams("league"));

            //find league by id
            League league = DBHelper.find(leagueId, League.class);

            //Set the other class requirements
            int managerId = Integer.parseInt(req.queryParams("manager"));
            Manager manager = DBHelper.find(managerId, Manager.class);

            String name = req.queryParams("name");
            String teamlogo = req.queryParams("team_logo");
            String location = req.queryParams("location");

            FootballTeam newFootballTeam = new FootballTeam(name, manager, league, teamlogo, location);

            league.addToTeams(newFootballTeam);

            DBLeague.getTeamsForALeaugue(league);

            league.addToTeams(newFootballTeam);
            DBHelper.update(league);

            DBHelper.save(newFootballTeam);
            DBHelper.update(league)

            ;


            res.redirect("/footballteams");

            return null;
        }, new VelocityTemplateEngine());

        //CREATE TEAM FOR LEAGUE

        get("/:id/teams/new", (req,res)->{

            int leagueId = Integer.parseInt(req.params(":id"));
            League league = DBHelper.find(leagueId, League.class);

            Map<String, Object> model = new HashMap<>();
            model.put("template","templates/footballteams/createForLeague.vtl");
            model.put("league", league);
            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());

        post("/:id/footballteams", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            //getting league id
            int leagueId = Integer.parseInt(req.params("id"));

            //find league by id
            League league = DBHelper.find(leagueId, League.class);


            String name1 = req.queryParams("name1");

            String teamlogo1 = req.queryParams("team_logo1");

            String location1 = req.queryParams("location1");

            String managerName1 = req.queryParams("mgrname1");
            String managerEmail1 = req.queryParams("email1");
            String managerPhone1 = req.queryParams("phone1");

            Manager newManager1 = new Manager(managerName1, managerEmail1, managerPhone1);
            DBHelper.save(newManager1);

            FootballTeam newFootballTeam1 = new FootballTeam(name1, newManager1, league, teamlogo1, location1);
            DBHelper.save(newFootballTeam1);

            league.addToTeams(newFootballTeam1);


            String name2 = req.queryParams("name2");

            String teamlogo2 = req.queryParams("team_logo2");

            String location2 = req.queryParams("location2");

            String managerName2 = req.queryParams("mgrname2");
            String managerEmail2 = req.queryParams("email2");
            String managerPhone2 = req.queryParams("phone2");

            Manager newManager2 = new Manager(managerName2, managerEmail2, managerPhone2);
            DBHelper.save(newManager2);

            FootballTeam newFootballTeam2 = new FootballTeam(name2, newManager2, league, teamlogo2, location2);
            DBHelper.save(newFootballTeam2);

            league.addToTeams(newFootballTeam2);


            String name3 = req.queryParams("name3");

            String teamlogo3 = req.queryParams("team_logo3");

            String location3 = req.queryParams("location3");

            String managerName3 = req.queryParams("mgrname3");
            String managerEmail3= req.queryParams("email3");
            String managerPhone3 = req.queryParams("phone3");

            Manager newManager3 = new Manager(managerName3, managerEmail3, managerPhone3);
            DBHelper.save(newManager3);

            FootballTeam newFootballTeam3 = new FootballTeam(name3, newManager3, league, teamlogo3, location3);
            DBHelper.save(newFootballTeam3);

            league.addToTeams(newFootballTeam3);

            String name4 = req.queryParams("name4");

            String teamlogo4 = req.queryParams("team_logo4");

            String location4 = req.queryParams("location4");

            String managerName4 = req.queryParams("mgrname4");
            String managerEmail4= req.queryParams("email4");
            String managerPhone4 = req.queryParams("phone4");

            Manager newManager4 = new Manager(managerName4, managerEmail4, managerPhone4);
            DBHelper.save(newManager4);

            FootballTeam newFootballTeam4 = new FootballTeam(name4, newManager4, league, teamlogo4, location4);
            DBHelper.save(newFootballTeam4);

            league.addToTeams(newFootballTeam4);



            String name5 = req.queryParams("name5");

            String teamlogo5 = req.queryParams("team_logo5");

            String location5 = req.queryParams("location5");

            String managerName5 = req.queryParams("mgrname5");
            String managerEmail5= req.queryParams("email5");
            String managerPhone5 = req.queryParams("phone5");

            Manager newManager5 = new Manager(managerName5, managerEmail5, managerPhone5);
            DBHelper.save(newManager5);

            FootballTeam newFootballTeam5 = new FootballTeam(name5, newManager5, league, teamlogo5, location5);
            DBHelper.save(newFootballTeam5);

            league.addToTeams(newFootballTeam5);

            DBHelper.save(league);

            res.redirect("/leagues");

            return null;
        }, new VelocityTemplateEngine());





        //view for a team
        get("/footballteams/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int footballTeamId = Integer.parseInt(req.params(":id"));
            FootballTeam footballTeam = DBHelper.find(footballTeamId, FootballTeam.class);
//            int leagueId = Integer.parseInt(req.queryParams("league"));
            //find league by id
            List<League> league = DBHelper.getAll(League.class);
//            int managerId = Integer.parseInt(req.queryParams("manager"));
//            Manager manager = DBHelper.find(managerId, Manager.class);
            List<Manager> manager = DBHelper.getAll(Manager.class);
            model.put("leauge", league);
            model.put("manage", manager);
            model.put("footballTeam", footballTeam);
            model.put("template", "templates/footballteams/view.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


        get("/footballteams/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            int footballTeamId = Integer.parseInt(req.params(":id"));

            FootballTeam selectedFootballTeam = DBHelper.find(footballTeamId, FootballTeam.class);
            model.put("selectedTeam", selectedFootballTeam);

            List<League> leagues = DBHelper.getAll(League.class);
            List<Manager> managers = DBHelper.getAll(Manager.class);

            model.put("footballTeam", selectedFootballTeam);
            model.put("leagues", leagues);
            model.put("managers", managers);
            model.put("template", "templates/footballteams/edit.vtl");

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/footballteams/:id/update", (req, res) -> {
            int leagueId = Integer.parseInt(req.queryParams("league"));
            League league = DBHelper.find(leagueId, League.class);


            String name = req.queryParams("name");

            String logo = req.queryParams("logo");

            int points = Integer.parseInt(req.queryParams("points"));

            String location = req.queryParams("location");

            int footballTeamId = Integer.parseInt(req.params(":id"));
            FootballTeam footballTeam = DBHelper.find(footballTeamId, FootballTeam.class);
            footballTeam.setName(name);
            footballTeam.setTeamLogo(logo);
            footballTeam.setLocation(location);
            footballTeam.setPoints(points);
            DBHelper.update(footballTeam);

            res.redirect("/footballteams");
            return null;
        }, new VelocityTemplateEngine());

        post("/footballteams/:id/delete", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            FootballTeam teamToDelete = DBHelper.find(id, FootballTeam.class);

//            League teamsLeague = teamToDelete.getLeague();
//            teamsLeague.removeTeams(teamToDelete);
//
//            DBHelper.update(teamsLeague);
            DBHelper.delete(teamToDelete);

            res.redirect("/footballteams");
            return null;
        }, new VelocityTemplateEngine());


    }
}


