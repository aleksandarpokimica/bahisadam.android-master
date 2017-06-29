package com.bahisadam.model;

import com.bahisadam.MyApplication;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class LeagueListComparator {

    private String leagueName;

    public LeagueListComparator(String leagueName) {
        this.leagueName = leagueName;
    }

    /*Comparator for sorting the list by League Name*/
    public static Comparator<MatchPOJO.Match> leagueNameComparator = new Comparator<MatchPOJO.Match>() {

        public int compare(MatchPOJO.Match l1, MatchPOJO.Match l2) {
            String leagueName1 = l1.getLeagueId().getLeagueName().toUpperCase();
            String leagueName2 = l2.getLeagueId().getLeagueName().toUpperCase();

            //ascending order
            return leagueName1.compareTo(leagueName2);
            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };

    /*Comparator for sorting the list by League Name*/
    public static Comparator<MatchPOJO.LeagueId> countryNameComparator = new Comparator<MatchPOJO.LeagueId>() {

        public int compare(MatchPOJO.LeagueId l1, MatchPOJO.LeagueId l2) {
            Locale locale = new Locale(MyApplication.sDefSystemLanguage);
            Collator coll = Collator.getInstance(locale);
            coll.setStrength(Collator.PRIMARY);

            String c1 = l1.country.toUpperCase();
            String c2 = l2.country.toUpperCase();

            //ascending order
            return coll.compare(c1, c2);
            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };


    /*Comparator for sorting the list by League Name*/
    public static Comparator<LeagueMatchList> countryNameComparator2 = new Comparator<LeagueMatchList>() {

        public int compare(LeagueMatchList l1, LeagueMatchList l2) {
            if(l1.getLeauge().country == null || l2.getLeauge().country == null) { return -1; }
            String c1 = l1.getLeauge().country. toUpperCase();
            String c2 = l2.getLeauge().country.toUpperCase();

            //ascending order
            return c1.compareTo(c2);
        }
    };

    public  static Comparator<MatchPOJO.LeagueId> leagueOrderComparator =  new Comparator<MatchPOJO.LeagueId>() {
        @Override
        public int compare(MatchPOJO.LeagueId l1, MatchPOJO.LeagueId l2) {
            Integer leagueOrder1 = l1.getOrder();
            Integer leagueOrder2 = l2.getOrder();
            if(leagueOrder1 == null) {
                return -1;
            }

            if(leagueOrder2 == null) {
                return 1;
            }

            return leagueOrder1.compareTo(leagueOrder2);
        }
    };
}
