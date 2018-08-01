package models;

import javax.persistence.*;

@Entity
@Table( name ="football_teams")

public class FootballTeam extends Team {
    private int goalsScored;
    private int goalsConceded;


    public FootballTeam(String name, Manager manager, League league, String teamLogo, String location){
        super(name, manager,league,teamLogo,location);
        this.goalsScored = 0;
        this.goalsConceded = 0;
    }

    public FootballTeam(){};

    @Column(name = "goals_scored")
    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    @Column(name = "goals_conceded")
    public int getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    public int goalDifference(){
        return this.goalsScored - this.goalsConceded;
    }
}
