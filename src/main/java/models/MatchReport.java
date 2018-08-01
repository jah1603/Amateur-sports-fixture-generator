package models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;

@Entity
@Table(name = "match_reports")

public class MatchReport {


    private int id;
    private Fixture fixture;
    private String headline;
    private String blurb;
    private String picture;

    public MatchReport(){}

    public MatchReport(Fixture fixture, String headline, String blurb, String picture){
        this.headline = headline;
        this.blurb = blurb;
        this.fixture = fixture;
        this.picture = picture;
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

    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="fixture_id", nullable = false)
    @Fetch(FetchMode.SELECT)
    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    @Column(name="headline")
    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    @Column(name="blurb")
    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    @Column(name= "picture")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int returnWeekForMatchReport(){
        return this.getFixture().getWeek();
    }

    public String returnHomeTeamName(){
        return this.getFixture().returnHomeTeam().getName();
    }

    public String returnAwayTeamName(){
        return this.getFixture().returnAwayTeam().getName();
    }
}
