import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import controllers.FixtureController;
import db.DBFixture;
import db.DBHelper;
import db.DBLeague;
import db.DBTeam;
import models.*;

import java.util.List;

public class Runner {



    public static void main(String[] args) {

        DBHelper.deleteAll(Manager.class);
        DBHelper.deleteAll(Team.class);
        DBHelper.deleteAll(FootballTeam.class);
        DBHelper.deleteAll(MatchReport.class);
        DBHelper.deleteAll(League.class);
        DBHelper.deleteAll(Fixture.class);
        DBHelper.deleteAll(League.class);
        DBHelper.deleteAll(Fixture.class);
        DBHelper.deleteAll(Manager.class);


        Manager manager = new Manager("Zsolt Poboda-Salai", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager);

        Manager manager2 = new Manager("Zsolt Poboda-Salai", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager2);

        Manager manager3 = new Manager("Zsolt Poboda-Salai", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager3);

        Manager manager4 = new Manager("Zsolt Poboda-Salai", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager4);

        Manager manager5 = new Manager("Zsolt Poboda-Salai", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager5);

        Manager manager6 = new Manager("Zsolt Poboda-Salai", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager6);

        Manager manager7 = new Manager("Zsolt Poboda-Salai", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager7);

        Manager manager8 = new Manager("Zsolt Poboda-Salai", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager8);

        Manager manager9 = new Manager("Zsolt Poboda-Salai", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager9);

        Manager manager10 = new Manager("Zsolt Poboda-Salai", "07610335467", "Zsolt@hungarianmail.hu");
        DBHelper.save(manager10);

        League league = new League("North of England Regional Division", LeagueType.NATIONAL, "Lancashire");
        DBHelper.save(league);

        FootballTeam homefootballTeam = new FootballTeam("Bolton Wanderers", manager, league, "nothing", "The Reebok Stadium");
        DBHelper.save(homefootballTeam);

        FootballTeam awayfootballTeam = new FootballTeam("Dagenham and Redbridge", manager2, league, "yes", "Dagenham Meadows Ground");
        DBHelper.save(awayfootballTeam);

        FootballTeam thirdFootballTeam = new FootballTeam("Burton Albion", manager3, league, "yes", "Albion Crossway");
        DBHelper.save(thirdFootballTeam);

        FootballTeam fourthfootballTeam = new FootballTeam("Edinburgh City", manager4, league, "no", "Leith Walk Cemetery Stadium");
        DBHelper.save(fourthfootballTeam);

        FootballTeam fithfootballTeam = new FootballTeam("Glasgow City", manager5, league, "no", "Easterhouse Playing Field");
        DBHelper.save(fithfootballTeam);

        FootballTeam sixthfootballTeam = new FootballTeam("Dundee City", manager6, league, "no", "Tayside Mews");
        DBHelper.save(sixthfootballTeam);

        FootballTeam seventhfootballTeam = new FootballTeam("Aberdeen City", manager7, league, "no", "Granite Paradise");
        DBHelper.save(seventhfootballTeam);

        FootballTeam eighthfootballTeam = new FootballTeam("FortWilliam City", manager8, league, "no", "Nevis Training Facility");
        DBHelper.save(eighthfootballTeam);

        FootballTeam ninethfootballTeam = new FootballTeam("Inverness City", manager9, league, "no", "Ness Walk Stadium");
        DBHelper.save(ninethfootballTeam);

        FootballTeam tenthfootballTeam = new FootballTeam("Random City", manager10, league, "no", "Random Ground");
        DBHelper.save(tenthfootballTeam);

        League league2 = new League("Highland Schools Hockey Championship", LeagueType.SCHOOLS, "Highlands and Islands");

        league.addToTeams(awayfootballTeam);
        league.addToTeams(homefootballTeam);
        league.addToTeams(thirdFootballTeam);
        league.addToTeams(fourthfootballTeam);
        league.addToTeams(fithfootballTeam);
        league.addToTeams(sixthfootballTeam);
        league.addToTeams(seventhfootballTeam);
        league.addToTeams(eighthfootballTeam);
        league.addToTeams(ninethfootballTeam);


        league.generateFixtures(true);
        DBHelper.update(league);

        for (Fixture fixture : league.getFixtures()) {
            DBHelper.save(fixture);
        }



        Fixture f = new Fixture(4, 2, league);
        f.addAwayTeamToFixture(awayfootballTeam);
        f.addHomeTeamToFixture(fourthfootballTeam);
        DBHelper.save(f);

        MatchReport report1 = new MatchReport(f, "Dagenham pull off shock victory at Edinburgh", "Against all expectations, Dagenham and Redbridge eked out a battling win away at Edinburgh, their first away points of the season.", "n");
        DBHelper.save(report1);
        f.setMatchReport(report1);
        DBHelper.update(f);


        List<Manager> allManagers = DBHelper.getAll(Manager.class);
        List<FootballTeam> allFootballTeams = DBHelper.getAll(FootballTeam.class);
        List<League> allLeagues = DBHelper.getAll(League.class);
        List<Fixture> allFixtures = DBHelper.getAll(Fixture.class);

        Manager foundManager = DBHelper.find(manager.getId(), Manager.class);
        Fixture Foundfixture = DBHelper.find(f.getId(), Fixture.class);

        List<Fixture> FixturesForOurLeague = DBLeague.getFixturesForLeague(league);
        List<Fixture> sortedFix = DBFixture.sortFixturesByWeeks();

        boolean ghost = sortedFix.get(0).getLeague().ghostInLeague();

        Fixture fixtureForFirstReport = sortedFix.get(1);
        Fixture fixtureForSecondReport = sortedFix.get(2);
        MatchReport reportForSite = new MatchReport(fixtureForFirstReport, "Experienced Wanderers side make light work of Highland upstarts.", "Bolton: Spencer-Clark (1), Lowe (2)", "");
        MatchReport secondReportForSite = new MatchReport(fixtureForSecondReport, "League minnows play out a drab stalemate as the relegation dogfights gets into full swing.", "Burton Albion: Davies (red card)", "");
        DBHelper.save(reportForSite);
        DBHelper.save(secondReportForSite);
        fixtureForFirstReport.setMatchReport(reportForSite);
        fixtureForSecondReport.setMatchReport(secondReportForSite);
        DBHelper.update(fixtureForFirstReport);
        DBHelper.update(fixtureForSecondReport);
        fixtureForFirstReport.setHomeGoals("3");
        fixtureForFirstReport.setAwayGoals("0");
        fixtureForSecondReport.setHomeGoals("0");
        fixtureForSecondReport.setAwayGoals("0");

        List<MatchReport> allReports = DBHelper.getAll(MatchReport.class);

//        DBLeague.getFullSeasonFixtures(2, league);

        List<Fixture> updatedLeagueFixtures = DBLeague.getFixturesForLeague(league);

        List<Manager> managers = DBHelper.getAll(Manager.class);

        //CHECK SORTED LEAGUE
        eighthfootballTeam.setPoints(20);
        DBHelper.update(eighthfootballTeam);
        fithfootballTeam.setPoints(10);
        DBHelper.update(fithfootballTeam);
        List<Team> sortedLeague = DBLeague.sortLeagueByPoints(league);


        List<Fixture> leaguesFixture = DBLeague.getFixturesForLeague(league);

    }
}
