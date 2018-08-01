package controllers;

import db.DBHelper;
import db.Seeds;
import models.FootballTeam;
import models.League;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ObjLongConsumer;

import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

public class MainController {
    public static void main(String[] args) {

        staticFileLocation("/public");


        Seeds.seedData();
        ManagerController managerController = new ManagerController();

        FootballTeamController footballTeamController = new FootballTeamController();

        MatchReportController matchReportController = new MatchReportController();

        LeagueController leagueController = new LeagueController();

        FixtureController fixtureController = new FixtureController();





        get("/all", (req,res)-> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/index.vtl");
            List<League> leagues = DBHelper.getAll(League.class);
            model.put("leagues", leagues);

            return new ModelAndView(model, "templates/layout.vtl");

    }, new VelocityTemplateEngine());

    }
}
