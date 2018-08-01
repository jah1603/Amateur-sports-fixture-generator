package db;

import models.League;
import models.Team;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class DBTeam {
    private static Transaction transaction;
    private  static Session session;


    public static League teamsLeauge(Team team){
        session = HibernateUtil.getSessionFactory().openSession();
        League teamsLeague = null;
        try{
            Criteria cr = session.createCriteria(League.class);
            cr.add(Restrictions.eq("team_id", team.getId()));
            teamsLeague =(League)cr.uniqueResult();
        }
        catch (HibernateException e){

        }
        finally {
            session.close();
        }
        return teamsLeague;
    }

}


