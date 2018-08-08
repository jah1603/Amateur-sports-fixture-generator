package db;

import com.sun.tools.corba.se.idl.constExpr.Or;
import models.*;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import java.util.List;
import java.util.Map;

public class DBLeague {

    private static Session session;
    private static Transaction transaction;


    public static List<Fixture> getFixturesForLeague(League league){
            session = HibernateUtil.getSessionFactory().openSession();
            List<Fixture> results = null;
            try {
                Criteria cr = session.createCriteria(Fixture.class);
                cr.add(Restrictions.eq("league", league));
                cr.addOrder(Order.asc("week"));
                cr.addOrder(Order.asc("match"));
                results = cr.list();
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        for (Fixture f : results){
            f.setLeague(league);
        }
            return results;
        }

        public static List<Team> getTeamsForALeaugue(League league){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Team> teams = null;
                try{
                    Criteria cr = session.createCriteria(Team.class);
                    cr.add(Restrictions.eq("league", league));
                    cr.addOrder(Order.asc("id"));
                    teams = cr.list();
                }
                catch (HibernateException e){
            e.printStackTrace();
                }
                finally {
            session.close();
                }
                return teams;
        }


            public static List<FootballTeam> sortLeagueByPoints(League league) {

                List<FootballTeam> teams = null;
                session = HibernateUtil.getSessionFactory().openSession();
                try {
                    Criteria cr = session.createCriteria(FootballTeam.class);
//                    cr.setProjection(Projections
//                                    .projectionList()
//                                    .add(Projections.property("id").as("prop1"))
//                                    .add(Projections.property("name").as("prop2"))
//                                    .add(Projections.property("manager").as("prop3"))
//                                    .add(Projections.property("league").as("prop4"))
//                                    .add(Projections.property("teamLogo").as("prop5"))
//                                    .add(Projections.property("location").as("prop6"))
//                                    .add(Projections.property("goalsScored").as("prop7"))
//                                    .add(Projections.property("goalsConceded").as("prop8"))
//                                    .add(Projections.sqlProjection(
//                                            "(goals_scored) - (goals_conceded) as goal_difference",
//                                            new String[] { "goal_difference" },
//                                            new org.hibernate.type.Type[] { StandardBasicTypes.INTEGER }), "goal_difference"));
                    cr.add(Restrictions.eq("league", league));
                    cr.addOrder(Order.desc("points"));
                    cr.addOrder(Order.desc("goalDifference"));
                    cr.addOrder(Order.desc("goalsScored"));
                    teams = cr.list();
                } catch (HibernateException e) {
                    e.printStackTrace();
                } finally {
                    session.close();
                }
                return teams;
            }



        }
