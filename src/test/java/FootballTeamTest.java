import models.FootballTeam;
import models.League;
import models.LeagueType;
import models.Manager;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class FootballTeamTest {

    FootballTeam footballTeam;
    Manager manager;
    League league;

    @Before
    public void setUp(){
        manager = new Manager("Neil Lennon", "07929897362", "neil.lennon@topmanager.com");
        league = new League("5s amateur league", LeagueType.AMATEUR, "Midlothian");
        footballTeam = new FootballTeam("Team 1", manager, league, "logo.jpg", "Midlothian" );
    }

    @Test
    public void canGetName(){
        assertEquals("Team 1",footballTeam.getName() );
    }

    @Test
    public void canGetManager(){
        assertEquals(manager,footballTeam.getManager() );
    }

    @Test
    public void canGetLeague(){
        assertEquals(league, footballTeam.getLeague() );
    }

    @Test
    public void canGetLogo(){
        assertEquals("logo.jpg", footballTeam.getTeamLogo() );
    }

    @Test
    public void canGetLocation(){
        assertEquals("Midlothian", footballTeam.getLocation() );
    }

    @Test
    public void canGetGoalsScored(){
        assertEquals(0, footballTeam.getGoalsScored() );
    }

    @Test
    public void canGetGoalsConceded(){
        assertEquals(0, footballTeam.getGoalsConceded() );
    }




}
