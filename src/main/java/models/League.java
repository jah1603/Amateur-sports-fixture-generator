package models;

import db.DBHelper;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "leagues")
public class League {
    private int id;
    private String name;
    private LeagueType leagueType;
    private List<Team> teams;
    private List<Fixture> fixtures;
    private List<List<Fixture>> season;
    private String region;

    public League() {
    }

    public League(String name, LeagueType leagueType, String region) {
        this.name = name;
        this.leagueType = leagueType;
        this.teams = new ArrayList<Team>();
        this.fixtures = new ArrayList<Fixture>();
        this.region = region;
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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "league_type")
    public LeagueType getLeagueType() {
        return leagueType;
    }

    public void setLeagueType(LeagueType leagueType) {
        this.leagueType = leagueType;
    }

    public String returnLeagueTypeFromEnum() {
        return this.leagueType.getLeagueType();
    }

    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @OneToMany(mappedBy = "league", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = Collections.synchronizedList(teams);
    }

    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @OneToMany(mappedBy = "league", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public List<Fixture> getFixtures() {
        return fixtures;
    }

    public List<List<Fixture>> returnSeason(){
        return this.season;
    }

    public void setFixtures(List<Fixture> fixtures) {
        this.fixtures = fixtures;
    }

    @Column(name = "region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int teamCount() {
        return this.teams.size();
    }

    public void addToTeams(Team team) {
        this.teams.add(team);
    }

    public void removeTeams(Team team) {
        this.teams.remove(team);
    }

    public void clearTeams() {
        this.teams.clear();
    }

    public boolean leagueContainsTeam(Team team) {
        if (this.teams.contains(team)) {
            return true;
        } else return false;
    }

    public int fixtureCount() {
        return this.fixtures.size();
    }

    public void addToFixtures(Fixture fixture) {
        this.fixtures.add(fixture);
    }

    public void removeFromFixtures(Fixture fixture) {
        this.fixtures.remove(fixture);
    }

    public void clearFixtures() {
        this.fixtures.clear();
    }

    public boolean leagueContiansFixture(Fixture fixture) {
        if (this.fixtures.contains(fixture)) {
            return true;
        } else return false;
    }

    public int numberOfTeams(){
        return teams.size();
    }

    public boolean requireGhost(){
        return numberOfTeams() % 2 != 0;
    }

    public void createGhostData(){
        if (requireGhost()) {
            Manager ghostManager = new Manager("Ghost manager", "", "");
            DBHelper.save(ghostManager);
            Team ghostTeam = new FootballTeam("Ghost", ghostManager, this, "", "Ghost Ground");
            DBHelper.save(ghostTeam);
            teams.add(ghostTeam);
        }
    }

    public int numberOfWeeks(){
        return numberOfTeams() - 1;
    }

    public int matchesPerWeek(){
        return numberOfTeams() / 2;
    }

    public List<List<Fixture>> createUnfilteredFixtureList(){
        List<List<Fixture>> fixturesList = new ArrayList<List<Fixture>>();

        if (requireGhost()){
            createGhostData();
        }

        for (int week = 0; week < numberOfWeeks(); week++) {

            List<Fixture> roundOfFixtures = new ArrayList<Fixture>();

            for (int match = 0; match < matchesPerWeek(); match++) {

                int home = (week + match) % (numberOfTeams() - 1);

                int away = ((numberOfTeams() - 1) - match + week) % (numberOfTeams() - 1);

                if (match == 0) {
                    away = numberOfTeams() - 1;
                }

                Fixture fixture = new Fixture((week + 1), match + 1, this, this.teams.get(home), this.teams.get(away));
                fixture.setHomeTeam(this.teams.get(home));
                fixture.setAwayTeam(this.teams.get(away));

                roundOfFixtures.add(fixture);
            }
            fixturesList.add(roundOfFixtures);
        }
        return fixturesList;
    }


    public List<List<Fixture>> evenOutHomeAndAwayGames(){

        List<List<Fixture>> filteredFixtures = new ArrayList<List<Fixture>>();

        int even = 0;
        int odd = numberOfTeams()/ 2;

        for (int i = 0; i < createUnfilteredFixtureList().size(); i++) {

            if (i % 2 == 0) {
                filteredFixtures.add(createUnfilteredFixtureList().get(even++));
            }

            else filteredFixtures.add(createUnfilteredFixtureList().get(odd++));
        }
    return filteredFixtures;
    }


    public void lastTeamInArrayNotAlwaysAway(){

        for (int week = 0; week < numberOfWeeks(); week++) {

            if (week % 2 != 0) {
                Fixture flippedFixture = evenOutHomeAndAwayGames().get(week).get(0);
                Collections.reverse(flippedFixture.getTeams());
                Team currentHome = flippedFixture.getHomeTeam();
                flippedFixture.setHomeTeam(flippedFixture.getAwayTeam());
                flippedFixture.setAwayTeam(currentHome);

            }
        }
    }

    public List<List<Fixture>> addSecondHalfOfSeason(){

        List<List<Fixture>> fixtureList = evenOutHomeAndAwayGames();
        lastTeamInArrayNotAlwaysAway();

        List<List<Fixture>> reverseFixtures = new ArrayList<List<Fixture>>();
        for (List<Fixture> weekOfFixtures : fixtureList) {
            List<Fixture> reversedWeek = new ArrayList<Fixture>();
            for (Fixture fixture : weekOfFixtures) {
                Fixture tempfix = new Fixture((fixture.getWeek() + numberOfTeams() - 1), (fixture.getMatch()), this, fixture.returnAwayTeam(), fixture.returnHomeTeam());
                int homeTeamIndex = this.teams.indexOf(fixture.returnHomeTeam());
                int awayTeamIndex = this.teams.indexOf(fixture.returnAwayTeam());
                ;tempfix.setAwayTeam(this.teams.get(homeTeamIndex));
                tempfix.setHomeTeam(this.teams.get(awayTeamIndex));
                reversedWeek.add(tempfix);
            }
            reverseFixtures.add(reversedWeek);
        }
       fixtureList.addAll(reverseFixtures);

        return fixtureList;

    }


    public List<List<Fixture>> fixturesWithReassignedWeekAndMatchNumbers(){

        List<List<Fixture>> fixtureList = addSecondHalfOfSeason();

        List<List<Fixture>> fixtureListCorrectlyNumberedByWeek = new ArrayList<List<Fixture>>();

        for (int week = 0; week < numberOfWeeks() * 2; week++) {

            List<Fixture> weeklyFixtures = fixtureList.get(week);

            for (int match = 0; match < matchesPerWeek(); match++) {
                Fixture retrievedFixture = weeklyFixtures.get(match);
                retrievedFixture.setWeek(week + 1);
            }
            fixtureListCorrectlyNumberedByWeek.add(weeklyFixtures);
        }
        return fixtureListCorrectlyNumberedByWeek;

    }


    public void generateFixtures(Boolean reverse){

        this.season = fixturesWithReassignedWeekAndMatchNumbers();
        this.fixtures = seasonsFixtures(reverse);

    }


    public ArrayList<Fixture> seasonsFixtures(Boolean reverse) {

        ArrayList<Fixture> newFixtures = new ArrayList<Fixture>();
        int matchesPerWeek = (teams.size() / 2);
        int numberOfWeeks = (teams.size() - 1);

//        FOR EACH WEEK, GO INTO THE LIST OF LISTS AND ACCESS THE LIST FOR THAT WEEK. THEN, TAKE EACH FIXTURE FROM THAT LIST ONE BY ONE AND ADD IT TO THE FIXTURE ATTRIBUTE OF THE LEAGUE CLASS.

        if (reverse) {
            for (int week = 0; week < numberOfWeeks * 2; week++) {

                for (int match = 0; match < matchesPerWeek; match++) {
                    List<Fixture> weeklyFixtures = season.get(week);
                    Fixture retrievedFixture = weeklyFixtures.get(match);
                    newFixtures.add(retrievedFixture);
                }

            }
            return newFixtures;
        }

        else {
            for (int week = 0; week < numberOfWeeks; week++) {

                for (int match = 0; match < matchesPerWeek; match++) {
                    List<Fixture> weeklyFixtures = season.get(week);
                    Fixture retrievedFixture = weeklyFixtures.get(match);
                    newFixtures.add(retrievedFixture);
                }

            }
            return newFixtures;
        }
    }

    public boolean ghostInLeague(){
       for (Team team : this.getTeams()){
           if (team.getManager().getName().equals("Ghost manager")){
               return true;
           }
        }
        return false;
    }

    public boolean ghostInNewLeague(){
        if (this.getTeams().get(0).getManager().getName().equals("Ghost manager")) {
            return true;
        }
        return false;
    }
}
