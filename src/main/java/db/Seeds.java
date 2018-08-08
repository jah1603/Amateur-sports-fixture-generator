package db;

import models.*;

import java.util.List;

public class Seeds {

    public static void seedData() {

        DBHelper.deleteAll(FootballTeam.class);
        DBHelper.deleteAll(MatchReport.class);
        DBHelper.deleteAll(League.class);
        DBHelper.deleteAll(Fixture.class);
        DBHelper.deleteAll(Manager.class);


        Manager manager = new Manager("Zsolt Poboda-Szalai", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager);

        Manager manager2 = new Manager("Duncan Bain", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager2);

        Manager manager3 = new Manager("Lesley Archer", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager3);

        Manager manager4 = new Manager("Lionel Spencer-Clark", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager4);

        Manager manager5 = new Manager("D. Baxter", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager5);

        Manager manager6 = new Manager("Humphrey Bogart", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager6);

        Manager manager7 = new Manager("Erik Svensson", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager7);

        Manager manager8 = new Manager("Tim Maxwell", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager8);

        Manager manager9 = new Manager("Ellington Harrington", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager9);

        Manager manager10 = new Manager("Zach Fields", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager10);

        League league = new League("Northern Regional Division", LeagueType.NATIONAL, "United Kingdom");
        DBHelper.save(league);

        FootballTeam homefootballTeam = new FootballTeam("Selkirk Wanderers", manager, league, "/images/selkirk.png", "Selkirkshire Indoor Ground");
        DBHelper.save(homefootballTeam);

        FootballTeam awayfootballTeam = new FootballTeam("Wester Ross Colts", manager2, league, "/images/westerross.png", "Ocean Promenade, Kyle of Lochalsh");
        DBHelper.save(awayfootballTeam);

        FootballTeam thirdFootballTeam = new FootballTeam("Broxburn Albion", manager3, league, "/images/broxburn.jpeg", "Albion Crossway");
        DBHelper.save(thirdFootballTeam);

        FootballTeam fourthfootballTeam = new FootballTeam("Edinburgh North", manager4, league, "/images/edinburgh.png", "Leith Walk Cemetery Stadium");
        DBHelper.save(fourthfootballTeam);

        FootballTeam fithfootballTeam = new FootballTeam("West Clydeside", manager5, league, "/images/glasgow.jpeg", "Easterhouse Playing Field, Glasgow");
        DBHelper.save(fithfootballTeam);

        FootballTeam sixthfootballTeam = new FootballTeam("Tayside Blades", manager6, league, "/images/tayside.png", "Stadium of Discovery");
        DBHelper.save(sixthfootballTeam);

        FootballTeam seventhfootballTeam = new FootballTeam("Aberdeen Oil FC", manager7, league, "/images/aberdeen.jpeg", "Granite Paradise");
        DBHelper.save(seventhfootballTeam);

        FootballTeam eighthfootballTeam = new FootballTeam("Fort William Red", manager8, league, "/images/fortwilliam.png", "Nevis Training Facility");
        DBHelper.save(eighthfootballTeam);

        FootballTeam ninethfootballTeam = new FootballTeam("City of Inverness", manager9, league, "/images/inverness.png", "Ness Walk Stadium, Inverness");
        DBHelper.save(ninethfootballTeam);

        FootballTeam tenthfootballTeam = new FootballTeam("Grampian Rovers", manager10, league, "/images/speyside.png", "Speyside Fishing Stadium, Aberlour");
        DBHelper.save(tenthfootballTeam);

        League league2 = new League("Highland Schools Hockey Championship", LeagueType.SCHOOLS, "Highlands and Islands");


        league.addToTeams(homefootballTeam);
        league.addToTeams(awayfootballTeam);
        league.addToTeams(thirdFootballTeam);
        league.addToTeams(fourthfootballTeam);
        league.addToTeams(fithfootballTeam);
        league.addToTeams(sixthfootballTeam);
        league.addToTeams(seventhfootballTeam);
        league.addToTeams(eighthfootballTeam);
        league.addToTeams(ninethfootballTeam);
        league.addToTeams(tenthfootballTeam);


        league.generateFixtures(true);
//        DBHelper.update(league);

        for (Fixture fixture : league.getFixtures()) {
            DBHelper.save(fixture);
        }

//        Fixture f = new Fixture(4, 2, league);
//        f.addAwayTeamToFixture(awayfootballTeam);
//        f.addHomeTeamToFixture(fourthfootballTeam);
//        DBHelper.save(f);
//
//        MatchReport report1 = new MatchReport(f, "Dagenham pull off shock victory at Edinburgh", "Against all expectations, Dagenham and Redbridge eked out a battling win away at Edinburgh, their first away points of the season.", "n");
//        DBHelper.save(report1);
//        f.setMatchReport(report1);
//        DBHelper.update(f);

//        List<Fixture> allFixtures = DBHelper.getAll(Fixture.class);
//        List<Fixture> sortedFix = DBFixture.sortFixturesByWeeks();
////
////
//        Fixture fixtureForFirstReport = sortedFix.get(1);
//        Fixture fixtureForSecondReport = sortedFix.get(2);
//        MatchReport reportForSite = new MatchReport(fixtureForFirstReport, "Experienced Wanderers side make light work of Highland upstarts.", "Selkirk: Spencer-Clark (1), Lowe (2)", "");
//        MatchReport secondReportForSite = new MatchReport(fixtureForSecondReport, "League minnows play out a drab stalemate as the relegation dogfight gets into full swing.", "Broxburn Albion: Davies (red card)", "");
//       reportForSite.setFixture(fixtureForFirstReport);
//        secondReportForSite.setFixture(fixtureForSecondReport);
//        DBHelper.save(reportForSite);
//        DBHelper.save(secondReportForSite);
//        fixtureForFirstReport.setMatchReport(reportForSite);
//        fixtureForSecondReport.setMatchReport(secondReportForSite);
//        DBHelper.update(fixtureForFirstReport);
//        DBHelper.update(fixtureForSecondReport);
//        fixtureForFirstReport.setHomeGoals("3");
//        fixtureForFirstReport.setAwayGoals("0");
//        fixtureForSecondReport.setHomeGoals("0");
//        fixtureForSecondReport.setAwayGoals("0");
//        FootballTeam homeTeam = (FootballTeam)fixtureForFirstReport.returnHomeTeam();
//        FootballTeam awayTeam = (FootballTeam)fixtureForFirstReport.returnAwayTeam();
//        FootballTeam homeTeam2 = (FootballTeam)fixtureForSecondReport.returnHomeTeam();
//        FootballTeam awayTeam2 = (FootballTeam)fixtureForSecondReport.returnAwayTeam();
//
//        homeTeam.setGoalsScored(Integer.parseInt(fixtureForFirstReport.getHomeGoals()));
//        homeTeam2.setGoalsScored(Integer.parseInt(fixtureForSecondReport.getHomeGoals()));
//        awayTeam.setGoalsScored(Integer.parseInt(fixtureForFirstReport.getAwayGoals()));
//        awayTeam2.setGoalsScored(Integer.parseInt(fixtureForSecondReport.getAwayGoals()));
//        homeTeam.setGoalsConceded(Integer.parseInt(fixtureForFirstReport.getAwayGoals()));
//        homeTeam2.setGoalsConceded(Integer.parseInt(fixtureForSecondReport.getAwayGoals()));
//        awayTeam.setGoalsConceded(Integer.parseInt(fixtureForFirstReport.getHomeGoals()));
//        awayTeam2.setGoalsConceded(Integer.parseInt(fixtureForSecondReport.getHomeGoals()));
//
//        homeTeam.setGoalDifference(homeTeam.getGoalsScored() - homeTeam.getGoalsConceded());
//        awayTeam.setGoalDifference(awayTeam.getGoalsScored() - awayTeam.getGoalsConceded());
//        homeTeam2.setGoalDifference(homeTeam2.getGoalsScored() - homeTeam2.getGoalsConceded());
//        awayTeam2.setGoalDifference(awayTeam2.getGoalsScored() - awayTeam2.getGoalsConceded());
//        DBHelper.update(homeTeam);
//        DBHelper.update(homeTeam2);
//        DBHelper.update(awayTeam);
//        DBHelper.update(awayTeam2);
//
//        fixtureForFirstReport.inputGoalsToGenerateResult(Integer.parseInt(fixtureForFirstReport.getHomeGoals()), Integer.parseInt(fixtureForFirstReport.getAwayGoals()));
//        fixtureForSecondReport.inputGoalsToGenerateResult(Integer.parseInt(fixtureForSecondReport.getHomeGoals()), Integer.parseInt(fixtureForSecondReport.getAwayGoals()));
//
//        fixtureForFirstReport.updateGamesPlayed(fixtureForFirstReport.getHomeGoals(), fixtureForFirstReport.getAwayGoals());
//        fixtureForSecondReport.updateGamesPlayed(fixtureForSecondReport.getHomeGoals(), fixtureForSecondReport.getAwayGoals());
//
//        DBHelper.update(fixtureForFirstReport);
//        DBHelper.update(fixtureForSecondReport);
//
//        DBHelper.update(league);
//
//
//
//        List<Manager> allManagers = DBHelper.getAll(Manager.class);
//        List<MatchReport> allReports = DBHelper.getAll(MatchReport.class);
//        List<FootballTeam> allFootballTeams = DBHelper.getAll(FootballTeam.class);
//        List<League> allLeagues = DBHelper.getAll(League.class);
//
//        Manager foundManager = DBHelper.find(manager.getId(), Manager.class);
//
//        List<Fixture> FixturesForOurLeague = DBLeague.getFixturesForLeague(league);
//
//
//
////        DBLeague.getFullSeasonFixtures(2, league);
//
//        List<Fixture> updatedLeagueFixtures = DBLeague.getFixturesForLeague(league);
//
//        List<Manager> managers = DBHelper.getAll(Manager.class);
//
////        //CHECK SORTED LEAGUE
////        eighthfootballTeam.setPoints(20);
////        DBHelper.update(eighthfootballTeam);
////        fithfootballTeam.setPoints(10);
////        DBHelper.update(fithfootballTeam);
////        List<Team> sortedLeague = DBLeague.sortLeagueByPoints(league);


    }
}
