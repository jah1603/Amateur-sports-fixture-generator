package models;

import com.sun.javafx.beans.IDProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name ="fixtures")

public class Fixture {
    private int id;
    private List<Team> teams;
    private String homeGoals;
    private String awayGoals;
    private int week;
    private Integer match;
    private String venue;
    private MatchReport matchReport;
    private League league;
    private Team homeTeam;
    private Team awayTeam;

    public Fixture() {
    }

    public Fixture(int week, Integer match, League league, Team homeTeam, Team awayTeam) {
        this.teams = new ArrayList<Team>();
        this.homeGoals = "";
        this.awayGoals = "";
        this.week = week;
        this.match = match;
        this.league = league;
        this.venue = venue;
        this.homeTeam = new FootballTeam();
        this.awayTeam = new FootballTeam();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "teams_fixtures")
    @Fetch(FetchMode.SELECT)
    public List<Team> getTeams() {
        return teams;
    }

    public String teamNames() {
        String teamNames = "";
        for (Team team : this.teams) {
            teamNames += team.getName() + " ";
        }
        return teamNames;
    }

    public void setTeams(List<Team> teams) {
        this.teams = Collections.synchronizedList(teams);
    }

    @Column(name = "home_goals")
    public String getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(String homeGoals) {
        this.homeGoals = homeGoals;
    }

    @Column(name = "away_goals")
    public String getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(String awayGoals) {
        this.awayGoals = awayGoals;
    }

    public int convertGoalsToInteger(String goals) {
        return Integer.parseInt(goals);
    }

    @Column(name = "week")
    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    @Column(name = "match")
    public Integer getMatch() {
        return match;
    }

    public void setMatch(Integer match) {
        this.match = match;
    }

    @Column(name = "venue")
    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @OneToOne(mappedBy = "fixture", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public MatchReport getMatchReport() {
        return matchReport;
    }

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public void setMatchReport(MatchReport matchReport) {
        this.matchReport = matchReport;
    }

    public void addTeamsToFixture(Team homeTeam, Team awayTeam) {
        this.teams.add(homeTeam);
        this.teams.add(awayTeam);
    }

    @ManyToOne
    @JoinColumn(name="home_team_id")
    @Fetch(FetchMode.SELECT)
    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    @ManyToOne
    @JoinColumn(name="away_team_id")
    @Fetch(FetchMode.SELECT)
    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void addHomeTeamToFixture(Team homeTeam) {
        this.teams.add(homeTeam);
    }

    public void addAwayTeamToFixture(Team awayTeam) {
        this.teams.add(awayTeam);
    }

    public int countTeams() {
        return this.teams.size();
    }

    public Team returnHomeTeam() {
        return this.homeTeam;
    }

    public Team returnAwayTeam() {
        return this.awayTeam;
    }


    public void inputGoalsToGenerateResult(int homeGoals, int awayGoals) {
        if (homeGoals > awayGoals) {
            homeTeam.addPointsToTeam(3);
            homeTeam.incrementWins();
            awayTeam.incrementLosses();
//            teams.get(0).addPointsToTeam(3);
//            teams.get(0).incrementWins();
//            teams.get(1).incrementLosses();
        } else if (awayGoals > homeGoals) {
            awayTeam.addPointsToTeam(3);
            awayTeam.incrementWins();
            homeTeam.incrementLosses();
//            teams.get(1).addPointsToTeam(3);
//            teams.get(1).incrementWins();
//            teams.get(0).incrementLosses();
        } else {
//            teams.get(0).addPointsToTeam(1);
//            teams.get(1).addPointsToTeam(1);
//            teams.get(0).incrementDraws();
//            teams.get(1).incrementDraws();
            homeTeam.addPointsToTeam(1);
            awayTeam.addPointsToTeam(1);
            homeTeam.incrementDraws();
            awayTeam.incrementDraws();

        }


    }

    public void updateGamesPlayed(String homeGoals, String awayGoals) {
        if (homeGoals != "") {
//            teams.get(0).incrementGamesPlayed();
////            teams.get(1).incrementGamesPlayed();
            homeTeam.incrementGamesPlayed();
            awayTeam.incrementGamesPlayed();
        }


    }

    public boolean hasGhost(League league) {
        int numberOfTeams = league.getTeams().size();
        Team lastTeam = league.getTeams().get(numberOfTeams - 1);
        String managerName = lastTeam.getManager().getName();
        if (managerName.equals("Ghost manager")) {
        }
        return true;

    }
}
