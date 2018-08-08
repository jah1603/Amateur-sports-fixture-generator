package controllers;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import db.DBFixture;
import db.DBHelper;
import db.DBLeague;
import models.*;
import models.MatchReport;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static db.DBLeague.getFixturesForLeague;
import static spark.Spark.get;
import static spark.Spark.post;

public class MatchReportController {

    public MatchReportController() {
        this.setUpEndpoints();
    }

    private void setUpEndpoints() {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        //READ
        get("/matchreports", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/matchreports/index.vtl");

            List<MatchReport> matchreports = DBHelper.getAll(MatchReport.class);
            model.put("matchreports", matchreports);

            List<League> allLeauges = DBHelper.getAll(League.class);
            model.put("leagues", allLeauges);

            List<Fixture> fixtureGhostsToKill = new ArrayList<>();

            for (MatchReport report1 : matchreports) {
                Fixture fixture = report1.getFixture();
            }

            List<Fixture> fixtures = DBHelper.getAll(Fixture.class);
            model.put("fixtures", fixtures);

            for (MatchReport report: matchreports){
                if (report.getFixture().getLeague().ghostInLeague())
                {
                    report.getFixture().setMatch(report.getFixture().getMatch() - 1);
                }
            }

            for (MatchReport report: matchreports){
                if (report.getFixture().getLeague().ghostInNewLeague())
                {
                    report.getFixture().setMatch(report.getFixture().getMatch() - 1);
                }
            }

            List<FootballTeam> footballTeams = DBHelper.getAll(FootballTeam.class);
            model.put("footballteams", footballTeams);

            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        get("/matchreports/:id", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/matchreports/view.vtl");

            int matchreportId = Integer.parseInt(req.params(":id"));
            MatchReport matchreport= DBHelper.find(matchreportId, MatchReport.class);

            List<Fixture> fixtures = DBHelper.getAll(Fixture.class);
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

            model.put("matchreport", matchreport);
            return new ModelAndView(model, "templates/layout.vtl");

        }, velocityTemplateEngine);


        //CREATE
        get("/:id/matchreports/new", (req, res) -> {

            int fixtureId = Integer.parseInt(req.params(":id"));
            Fixture fixture = DBHelper.find(fixtureId, Fixture.class);

            HashMap<String, Object> model = new HashMap<>();

           fixture.setMatch(fixture.getMatch()-1);

            model.put("fixture", fixture);

            List<MatchReport> matchReports = DBHelper.getAll(MatchReport.class);


            model.put("matchreports", matchReports);
            model.put("template", "templates/matchreports/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);



        post("/:id/matchreports", (req, res) -> {

            int fixtureId = Integer.parseInt(req.params(":id"));
            Fixture fixture = DBHelper.find(fixtureId, Fixture.class);

            String headline = req.queryParams("headline");

            String blurb = req.queryParams("blurb");

            String picture = req.queryParams("picture");

            MatchReport newMatchReport = new MatchReport( fixture, headline, blurb, picture);
            DBHelper.save(newMatchReport);
            fixture.setMatchReport(newMatchReport);

            newMatchReport.setFixture(fixture);

            fixture.setMatch(fixture.getMatch()-1);

            res.redirect("/fixtures");
            return null;
        }, velocityTemplateEngine);




        //UPDATE
        get("/matchreports/:id/edit", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<MatchReport> matchreports = DBHelper.getAll(MatchReport.class);

            int matchreportId = Integer.parseInt(req.params(":id"));
            MatchReport matchreport = DBHelper.find(matchreportId, MatchReport.class);

            List<Fixture> fixtures = DBHelper.getAll(Fixture.class);

            for (Fixture fixture1: fixtures){
                fixture1.setMatch(fixture1.getMatch() - 1);
            }

            model.put("matchreport", matchreport);
            model.put("template", "templates/matchreports/edit.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        post("/matchreports/:id", (req, res) -> {
            String headline = (req.queryParams("headline"));
            String blurb = (req.queryParams("blurb"));

            int matchreportId = Integer.parseInt(req.params(":id"));
            MatchReport matchreport = DBHelper.find(matchreportId, MatchReport.class);
            matchreport.setHeadline(headline);
            matchreport.setBlurb(blurb);
            DBHelper.update(matchreport);


            res.redirect("/matchreports");
            return null;
        }, velocityTemplateEngine);
    }
}
