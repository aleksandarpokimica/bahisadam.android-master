package com.bahisadam.interfaces;

public interface Constant {
    String NEWS_ID = "news_id";
    int SPLASH_TIME_OUT = 1000;
    int RESPONSE_CODE = 200;
    String[] SHORTMONTH = {"Oca","Şub","Mar","Nis","May","Haz","Tem","Ağu","Eyl","Eki","Kas","Ara"};
    int FRAGMENT_ONE = 0;
    int FRAGMENT_TWO = 1;
    int FRAGMENT_THREE = 2;
    int FRAGMENT_FOUR = 3;
    int FRAGMENT_FIVE = 4;

    boolean SUCCESS = true;
    boolean FALSE = false;
    boolean TRUE = true;

    String EVENT_GOAL = "Goal";
    String EVENT_ASSIST = "Asist";
    String EVENT_CARD = "Card";
    String EVENT_YELLOWCARD = "YellowCard";
    String EVENT_YELLOWREDCARD = "YellowRedCard";
    String EVENT_REDCARD = "RedCard";
    String EVENT_SUBSTITUTION = "Substitution";


    String fontPath = "fonts/ProximaNova-Light.otf";
    String fontPath2 = "fonts/UVF ProximaNova-Regular.ttf";

    String PLAYED = "Played";
    String PLAYING = "Playing";
    String NOT_PLAYED = "NotPlayed";
    String CANCELLED = "Canceled";
    String POSTPONED = "Postponed";
    String DELAYED = "Delayed";
    String INTERRUPTED = "Interrupted";
    String LINEUP = "Lineup";
    String CUP = "Cup";
    String PLAYOFF  = "playoff";
 //   String ROOT = "http://192.168.122.1:9000/api/";
    //String ROOT = "http://192.168.0.10:9000/api/";
    //192.168.122.255
    String ROOT = "http://www.skoradam.com/api/";
    String BASE_IMAGE_ASSET = "http://static.bahisadam.com/uploads/news/";
    String APPENDED_PATH = "matches/";
    String MATCH_DETAIL_PATH = "match/detail/";
    String SLASH = "/";
    String IMAGES_ROOT = "http://static.bahisadam.com";
    String LOGO_PATH = "/images/logo/teams/";
    String LOGO_EXTENSION = "_logo.png";
    String SOCKET_URL  = "http://www.skoradam.com";
    int TURKEY_SUPER_LEAGUE = 1;

    String PREF_FAVORITEMATCHES = "favorites";
}