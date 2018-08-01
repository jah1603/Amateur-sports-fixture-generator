package models;

public enum LeagueType {
    PROFESSIONAL("PROFESSIONAL"),
    SCHOOLS("SCHOOLS"),
    AMATEUR("AMATEUR"),
    NATIONAL("NATIONAL"),
    INTERNATIONAL("INTERNATIONAL");

    private final String leagueType;

     LeagueType(String type){
        this.leagueType = type;
    }

    public String getLeagueType() {
        return leagueType;
    }

    public LeagueType getLeagueType(String string){
         return LeagueType.valueOf(string);
    }



}
