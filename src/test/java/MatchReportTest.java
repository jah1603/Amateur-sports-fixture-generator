import models.Fixture;
import models.League;
import models.LeagueType;
import models.MatchReport;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

public class MatchReportTest{

    MatchReport matchReport;
    Fixture fixture1;
    Fixture fixture2;
    League league;

    @Before
    public void setUp(){
        fixture1 = new Fixture(1, 2,  league);
        fixture2 = new Fixture(1,2 ,league);
        league = new League("Edinburgh Schools", LeagueType.SCHOOLS, "Midlothian");
        matchReport = new MatchReport(fixture1,"one", "two", "pic.jpg");
    }
    @Test
    public void hasFixture(){
        assertEquals(fixture1, matchReport.getFixture());
    }

    @Test
    public void canSetFixture(){
        matchReport.setFixture(fixture2);
        assertEquals(fixture2,matchReport.getFixture());

    }

    @Test
    public void hasHeadLine(){
        assertEquals("one", matchReport.getHeadline());
    }

    @Test
    public void canSetHeadline(){
        matchReport.setHeadline("New game");
        assertEquals("New game", matchReport.getHeadline());
    }

    @Test
    public void hasBlurb(){
        assertEquals("two",matchReport.getBlurb());
    }

    @Test
    public void canSetBlurb(){
        matchReport.setBlurb("another defeat");
        assertEquals("another defeat", matchReport.getBlurb());
    }

    @Test
    public void hasLogo(){
        assertEquals("pic.jpg", matchReport.getPicture());
    }

    @Test
    public void canSetLogo(){
        matchReport.setPicture("pic_one.png");
        assertEquals("pic_one.png", matchReport.getPicture());
    }

}
