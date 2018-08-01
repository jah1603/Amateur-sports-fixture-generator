
import models.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class LeagueTest {
    League league;
    Fixture fixture;
    Team team1;
    Team team2;
    LeagueType leagueType;
    Manager manager;

    @Before
    public void setUp(){
        league = new League("Edinburgh Schools", LeagueType.SCHOOLS, "Midlothian");
        fixture = new Fixture(1, 2, league);
        manager = new Manager("David", "077507545", "haggishunters@football.com");
        team1 = new FootballTeam("Edinburgh Haggis Hunters", manager, league, "logo.jpg","Edinburgh");
        team2 = new FootballTeam("Dundee Dodgers", manager, league, "logo.jpg","Dundee");
        }


    @Test
    public void hasName(){
        assertEquals("Edinburgh Schools", league.getName());
    }

    @Test
    public void hasLeagueType(){
        assertEquals(LeagueType.SCHOOLS, league.getLeagueType());
    }

    @Test
    public void hasRegion(){
        assertEquals("Midlothian", league.getRegion());
    }

    @Test
    public void teamListStartEmpty(){
        assertEquals(0, league.teamCount());
    }

    @Test
    public void fixtureListStartsEmpty(){
        assertEquals(0,league.fixtureCount());
    }

    @Test
    public void canAddToTeam(){
        league.addToTeams(team1);
        assertEquals(1, league.teamCount());
    }

    @Test
    public void canRemoveTeam(){
        league.addToTeams(team1);
        league.addToTeams(team2);
        league.removeTeams(team1);
        assertEquals(1,league.teamCount());
    }

    @Test
    public void canClearTeamList(){
        league.addToTeams(team1);
        league.addToTeams(team2);
        league.clearTeams();
        assertEquals(0,league.teamCount());

    }
//
//    @Test
//    public void canAddFixture(){
//        league.addToFixtures(fixture);
//        assertEquals(1,league.fixtureCount());
//    }
//
//    @Test
//    public void canRemoveFixture(){
//        league.addToFixtures(fixture);
//        league.removeFromFixtures(fixture);
//        assertEquals(0,league.fixtureCount());
//    }
//
//    @Test
//    public void canRemoveAllFixtures(){
//        league.addToFixtures(fixture);
//        league.clearFixtures();
//        assertEquals(0,league.fixtureCount());
//    }
//
//    @Test
//    public void canTellIfLeagueContainsFixture__ture(){
//        league.addToFixtures(fixture);
//        assertEquals(true, league.leagueContiansFixture(fixture));
//    }
//
//    @Test
//    public void canTellIfLeagueContainsFixture__false(){
//        league.addToFixtures(fixture);
//        league.removeFromFixtures(fixture);
//        assertEquals(false, league.leagueContiansFixture(fixture));
//    }
}
