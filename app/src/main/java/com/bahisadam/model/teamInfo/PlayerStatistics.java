package com.bahisadam.model.teamInfo;


public class PlayerStatistics
{
    private String id;

    private String _id;

    private String name;

    private String matches_played;

    private String squadNumber;

    private Statistics statistics;

    private String role;

    private String nationality;

    private String country_code;

    private int goals;

    private int assists;

    private int cards;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getMatches_played ()
    {
        return matches_played;
    }

    public void setMatches_played (String matches_played)
    {
        this.matches_played = matches_played;
    }

    public Statistics getStatistics ()
    {
        return statistics;
    }

    public void setStatistics (Statistics statistics)
    {
        this.statistics = statistics;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", _id = "+_id+", name = "+name+", matches_played = "+matches_played+", statistics = "+statistics+"]";
    }

    public String getSquadNumber() {
        return squadNumber;
    }

    public void setSquadNumber(String squadNumber) {
        this.squadNumber = squadNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getCards() {
        return cards;
    }

    public void setCards(int cards) {
        this.cards = cards;
    }
}

