import db.DBHelper;
import models.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertSame;

public class FixtureTest {

    Fixture fixture;
    FootballTeam homefootballTeam;
    FootballTeam awayfootballTeam;
    FootballTeam thirdFootballTeam;
    FootballTeam fourthfootballTeam;
    FootballTeam fithfootballTeam;
    FootballTeam sixthfootballTeam;
    FootballTeam seventhfootballTeam;
    FootballTeam eighthfootballTeam;
    FootballTeam ninethfootballTeam;
    FootballTeam tenthfootballTeam;
    Manager manager;
    League league;
    MatchReport matchReport1;


    @Before
    public void before() {
        manager = new Manager("Zsolt Poboda-Salai", "07610335467", "Zsolt@hungarianmail.hu");
        league = new League("North of England Regional Division", LeagueType.NATIONAL, "Lancashire");
        homefootballTeam = new FootballTeam("Bolton Wanderers", manager, league, "nothing", "Bolton");
        awayfootballTeam = new FootballTeam("Dagenham and Redbridge", manager, league, "yes", "Dagenham");
        thirdFootballTeam = new FootballTeam("Burton Albion", manager, league, "yes", "Burton");
        fourthfootballTeam = new FootballTeam("Edinburgh City", manager, league, "no", "Edinburgh");
        fithfootballTeam = new FootballTeam("Glasgow City", manager, league, "no", "Edinburgh");
        sixthfootballTeam = new FootballTeam("Dundee City", manager, league, "no", "Edinburgh");
        seventhfootballTeam = new FootballTeam("Aberdeen City", manager, league, "no", "Edinburgh");
        eighthfootballTeam = new FootballTeam("FortWilliam City", manager, league, "no", "Edinburgh");
        ninethfootballTeam = new FootballTeam("Inverness City", manager, league, "no", "Edinburgh");
        fixture = new Fixture(1, 2, league);
        matchReport1 = new MatchReport(fixture, "Wanderers slaughtered in humiliating rout!", "Same", "logo");
    }

    @Test
    public void hasWeek(){
        assertEquals(1, fixture.getWeek());
    }

    @Test
    public void hasVenue(){
        assertEquals("Liberty Stadium", fixture.getVenue());
    }

    @Test
    public void hasLeague(){
        assertEquals(league, fixture.getLeague());
    }

    @Test
    public void canChangeVenue(){
        fixture.setVenue("Ibrox");
        assertEquals("Ibrox", fixture.getVenue());
    }

    @Test
    public void canSetWeek(){
        fixture.setWeek(2);
        assertEquals(2, fixture.getWeek());
    }

    @Test
    public void canConvertHomeGoalsToInteger(){
        fixture.setHomeGoals("3");
        fixture.convertGoalsToInteger(fixture.getHomeGoals());
        assertEquals(3, fixture.getHomeGoals());
    }

    @Test
    public void canConvertAwayGoalsToInteger(){
        fixture.setAwayGoals("3");
        fixture.convertGoalsToInteger(fixture.getAwayGoals());
        assertEquals(3, fixture.getAwayGoals());
    }

    @Test
    public void canGetTeamNames(){
        fixture.addTeamsToFixture(thirdFootballTeam,fithfootballTeam);
        assertEquals("Burton Albion Glasgow City", fixture.teamNames());

    }

    @Test
    public void canSetMatchReport(){
        fixture.setMatchReport(matchReport1);
        assertEquals(matchReport1, fixture.getMatchReport());
    }

    @Test
    public void canSetTeams(){
        fixture.addTeamsToFixture(homefootballTeam, awayfootballTeam);
        assertEquals(awayfootballTeam, fixture.returnAwayTeam());
        assertEquals(homefootballTeam, fixture.returnAwayTeam());
    }

    @Test
    public void canGetFixtureList(){
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
//        System.out.println(league.fixtureGenerator(false, league.getTeams(), league));
//        System.out.println(league.getFixtures());
//        league.getFixtureFromListOfListOfFixturesAndAddToFixtureList(league);
        System.out.println(league.getFixtures());
    }

}
