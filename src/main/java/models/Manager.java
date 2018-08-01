package models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "managers")

public class Manager {

    private int id;
    private String name;
    private String phoneNo;
    private String email;
    private Team team;

public Manager(){}

public Manager(String name, String phoneNo, String email){
    this.name = name;
    this.phoneNo = phoneNo;
    this.email = email;
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

    @Column(name = "phone_number")
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToOne(mappedBy = "manager", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
