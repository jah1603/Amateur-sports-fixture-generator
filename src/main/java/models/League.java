package models;

import db.DBHelper;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import javax.persistence.*;
import java.util.ArrayList;
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
        this.teams = teams;
    }

    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @OneToMany(mappedBy = "league", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public List<Fixture> getFixtures() {
        return fixtures;
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

    public void generateFixtures(Boolean reverse) {
        //THIS ESTABLISHES IF WE NEED A GHOST (imaginary team when there is an odd number of teams)
        //Set integer variable called number of teams = teams.size()
        int numberofTeams = teams.size();
        //ghost = false
        Boolean ghost = false;
        // if number of teams is odd increment no of teams by 1 and set ghost to true (imaginary team to preserve method logic)
        if (teams.size() % 2 != 0) {
            ghost = true;
            numberofTeams += 1;
            Manager ghostManager = new Manager("Ghost manager", "", "");
            DBHelper.save(ghostManager);
            Team ghostTeam = new FootballTeam("Ghost", ghostManager, this, "", "Ghost Ground");
            DBHelper.save(ghostTeam);
            teams.add(numberofTeams-1, ghostTeam);
        }
        //ESTABLISH HOW MANY ROUNDS OF FIXTURES THERE WILL BE
        //Set int number of weeks = teams.size - 1
        int numberOfWeeks = (teams.size() - 1);
        //ESTABLISH HOW MANY MATCHES A WEE THERE WILL BE
        // Set matches per week = number of teams / 2
        int matchesPerWeek = (teams.size() / 2);
        //SET UP AN EMPTY FIXTURE LIST
        //List<<List> Fixtures> = new List<List<Fixture>>
        List<List<Fixture>> fixturesList = new ArrayList<List<Fixture>>();
        //GENERATE A LIST OF FIXTURES THAT STILL NEED TO BE REFACTORED TO MEET OUR NEEDS(WHOLE BLOCK)
        //GENERATES THE NUMBER OF ROUNDS OF FIXTURES
        //Initiate for loop stating at week = 0, week < number of weeks, week++
        for (int week = 0; week < numberOfWeeks; week++) {
            //MAKES AN EMPTY LIST OF FIXTURES THAT WE CAN ADD FIXTURES TO
            List<Fixture> roundOfFixtures = new ArrayList<Fixture>();
            //GENERATES THE CORRECT AMOUNT OF FIXTURES PER ROUND
            // Iterate nested for loop set match to 0 then while match is less than matches per week increment match by 1
            for (int match = 0; match < matchesPerWeek; match++) {
                //SET INTEGER OF HOME TEAM TO USE AS AN INDEX FOR THE TEAMS ARRAY
                //Set integer home equal to the remainder (week + match) divided by (number of teams -1 )
                int home = (week + match) % (numberofTeams - 1);
                //SETSINTEGER OF AWAY TEAM TO USE AS AN INDEX FOR THE TEAMS ARRAY
                // Set integer away equal to the remainder of (number of teams - 1 - match + week) devided by number of teams -1
                int away = ((numberofTeams - 1) - match + week) % (numberofTeams - 1);
                // If match is equal 0 set away to equal number of teams - 1
                if (match == 0) {
                    away = numberofTeams - 1;
                }
                //  Create new fixture with home and away teams retrieved from the array of teams. Using the integers home and away.
                Fixture fixture = new Fixture((week + 1), match + 1, this);
                fixture.addTeamsToFixture(this.teams.get(home), this.teams.get(away));
                //Add the fixture above to the main list of round of fixtures
                roundOfFixtures.add(fixture);
            }
            fixturesList.add(roundOfFixtures);
        }
        //WE NOW HAVE A LIST OF FIXTURES AND WE WANT TO MAKE SURE THAT THE SAME TEAM ISN'T PLAYING HOME TWO OR THREE WEEKS IN A ROW.
        //Make a new list of list of fixtures(as above)
        List<List<Fixture>> filteredFixtures = new ArrayList<List<Fixture>>();
        //Creating two integer variables, even and odd. Even will be 0 and odd will be the no of teams devided by 2.
        int even = 0;
        int odd = numberofTeams / 2;
        //Start a for loop to get weeks for the list of lists with the aim of evening out a team playing away all the time. i Starts at 0, while i is less than our list of list of fixtures , i is incremented by          one.
        for (int i = 0; i < fixturesList.size(); i++) {
            //If i is divisible by 2 with no remainder (ie even), then get the list of weekly fixtures positioned at the even integer index from the fixtureList List of Lists
            if (i % 2 == 0) {
                filteredFixtures.add(fixturesList.get(even++));
            }
            //ELse i is not divisible by 2 with no remainder (ie odd), then get the list of weekly fixtures positioned at the odd integer index from the fixtureList List of Lists
            else filteredFixtures.add(fixturesList.get(odd++));
        }
        fixturesList = filteredFixtures;
        //SO THE LAST ITEM IN THE TEAM ARRAY IS NOT ALWAYS SET AS THE AWAY TEAM
        //Initiate for loop stating at week = 0, week < number of weeks, week++
        for (int week = 0; week < numberOfWeeks; week++) {
            //if week is odd set a new fixture which reassigns the zeroth position in the weekly fixtures array.
            if (week % 2 != 0) {
                Fixture flippedFixture = fixturesList.get(week).get(0);
                Collections.reverse(flippedFixture.getTeams());
            }
            // if reverse fixtures are desired then set new object reverseFixtures to a new empty list of empty lists of fixtures
            // start for loop which loops through every weekly list of fixtures in our current list of lists (fixturesList)
            // set new object WeekOfReversedFixtures to a new empty Array of fixtures.
            // start for loop looping through every Fixture fixture in the current list of weekly fixtures
            // set new object reverseFixture equal to a new Fixture and assign it teams
            // Outside second loop, add current WeekOfReversedFixtures to the list of lists of fixtures (reverseFixtures)
            // addAll of the reverseFixtures to the list of lists of fixtures (fixturesList)
        }
        if (reverse) {
            List<List<Fixture>> reverseFixtures = new ArrayList<List<Fixture>>();
            for (List<Fixture> weekOfFixtures : fixturesList) {
                List<Fixture> reversedWeek = new ArrayList<Fixture>();
                for (Fixture fixture : weekOfFixtures) {
                    Fixture tempfix = new Fixture((fixture.getWeek() + numberofTeams - 1), (fixture.getMatch()), this);
                    tempfix.addTeamsToFixture(fixture.returnAwayTeam(), fixture.returnHomeTeam());
                    reversedWeek.add(tempfix);
                }
                reverseFixtures.add(reversedWeek);
            }
            fixturesList.addAll(reverseFixtures);
        }

        // ADDING ANOTHER SUB-METHOD TO RESET THE WEEK LABEL OF FIXTURES TO THE PROPER VALUE

        List<List<Fixture>> fixtureListCorrectlyNumberedByWeek = new ArrayList<List<Fixture>>();

        for (int week = 0; week < numberOfWeeks * 2; week++) {

            List<Fixture> weeklyFixtures = fixturesList.get(week);

            for (int match = 0; match < matchesPerWeek; match++) {
                Fixture retrievedFixture = weeklyFixtures.get(match);
                retrievedFixture.setWeek(week + 1); // RESETS THE WEEK ATTRIBUTE FOR EACH FIXTURE
            }
            fixtureListCorrectlyNumberedByWeek.add(weeklyFixtures); //ADDS NEWLY RESET WEEKLY FIXTURES TO A NEW LIST OF LISTS
        }
        fixturesList = fixtureListCorrectlyNumberedByWeek;


        // Reassign the fixturesList

        this.season = fixtureListCorrectlyNumberedByWeek;
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
        this.generateFixtures(true);
        if (this.getTeams().get(this.teamCount() - 1).getManager().getName().equals("Ghost manager")) {
            return true;
        }
        return false;
    }

    public boolean ghostInNewLeague(){
        this.generateFixtures(true);
        if (this.getTeams().get(0).getManager().getName().equals("Ghost manager")) {
            return true;
        }
        return false;
    }
}






