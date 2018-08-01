package db;

import models.Fixture;
import models.League;
import models.Team;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.awt.print.Book;
import java.util.List;

public class DBFixture {

    private static Session session;

    public static List<Fixture> sorFixturesByPWeek() {


        List<Fixture> fixtures = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria cr = session.createCriteria(Fixture.class);
            cr.addOrder(Order.desc("week"));
            fixtures = cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return fixtures;
    }


    public static List<Fixture> sortFixturesByWeeks() {

        List<Fixture> fixture = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria cr = session.createCriteria(Fixture.class);
            cr.addOrder(Order.asc("week"));
            cr.addOrder(Order.asc("match"));
            fixture =  cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return fixture;
    }

    public static List<Fixture> sortLeaguesFixturesByWeeks(League league) {

        List<Fixture> fixture = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria cr = session.createCriteria(Fixture.class);
            cr.add(Restrictions.eq("league", league));
            cr.addOrder(Order.asc("week"));
            cr.addOrder(Order.asc("match"));
            fixture =  cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return fixture;
    }



    public static void saveFixturesForLeague(League league){
        for (Fixture fixture : league.getFixtures()){
            DBHelper.save(fixture);
        }


    }








}

