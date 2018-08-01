import models.Manager;
import models.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ManagerTest {

    FootballTeam team;
    Manager manager;
    League league;

    @Before

    public void before(){
        League league = new League("North of England Regional Division", LeagueType.NATIONAL, "Lancashire");
        manager = new Manager("Lionel Spencer", "01847 873245", "lionel@btinternet.com");
        team = new FootballTeam("Bury", manager, league, "d", "Bury Saint Edmonds");}


    @Test
    public void hasPhoneNumber(){
        assertEquals("01847 873245", manager.getPhoneNo());
    }

    @Test
    public void hasEmailAddress(){
        assertEquals("lionel@btinternet.com", manager.getEmail());
    }

    @Test
    public void teamStartsNull(){
        assertEquals(null, manager.getTeam());
    }

    @Test
    public void canSetTeam(){
        manager.setTeam(team);
        assertEquals(team, manager.getTeam());
    }

    @Test
    public void canUpdatePhoneNumber(){
        manager.setPhoneNo("01847 873244");
        assertEquals("01847 873244", manager.getPhoneNo());
    }

    @Test
    public void canUpdateEmailAddress(){
        manager.setEmail("lionel.spencer@aol.com");
        assertEquals("lionel.spencer@aol.com", manager.getEmail());
    }

}
