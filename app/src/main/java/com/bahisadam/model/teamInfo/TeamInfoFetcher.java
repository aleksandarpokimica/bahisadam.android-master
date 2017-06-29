package com.bahisadam.model.teamInfo;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.bahisadam.interfaces.Constant;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class TeamInfoFetcher {  //need to rewrite!!!!!!!!!!! by using retrofit

    private Uri mUriMatches;
    private Uri mUriFixtures;
    private static final String TAG = "TeamInfoFetcher";
    public static final String TEAM_INFO_MATCHES = "team_info_matches";
    public static final String TEAM_INFO_FIXTURES = "team_info_fixtures";
    private String mTeamId;

    public TeamInfoFetcher(String id) {
        mTeamId = id;
        mUriMatches = Uri.parse(Constant.ROOT + "/match/getteammatches/"+id);
        mUriFixtures = Uri.parse(Constant.ROOT + "/match/getnextmatches/"+id);

    }

    private byte[] getUrlBytes(String urlSpec)throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK) throw new IOException(connection.getResponseMessage()+": with" + urlSpec);
            int bytesRead;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return  out.toByteArray();
        }
        finally {
            connection.disconnect();
        }

    }

    private String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    public Bundle getTeamInfo(){
        String url1 = mUriMatches.toString();
        String url2 = mUriFixtures.toString();
        return downloadItems(url1, url2);
    }

    private Bundle downloadItems(String urlMatches, String urlFixtures) {
        Bundle teamInfo = new Bundle();

        try {
            String jsonStringMatches = getUrlString(urlMatches);
            String jsonStringFixtures = getUrlString(urlFixtures);
            JSONObject jsonBodyMatches = new JSONObject(jsonStringMatches);
            JSONObject jsonBodyFixtures = new JSONObject(jsonStringFixtures);
            ArrayList<TeamMatch> matches = parseMatchesInfo(jsonBodyMatches);
            ArrayList<TeamFixture> fixtures = parseFixturesInfo(jsonBodyFixtures);
            teamInfo.putParcelableArrayList(TEAM_INFO_MATCHES,matches);
            teamInfo.putParcelableArrayList(TEAM_INFO_FIXTURES,fixtures);
        } catch (JSONException je){
            Log.e(TAG,"Failed to parse Json",je);
        } catch (IOException ioe){
            Log.e(TAG,"Failed to fetch items",ioe);
        }
        return teamInfo;
    }

    private ArrayList<TeamMatch> parseMatchesInfo(JSONObject jsonBody) throws JSONException{
        ArrayList<TeamMatch> matches = new ArrayList<>();
        Iterator<?> keys = jsonBody.keys();
        while(keys.hasNext()) {
            String key = (String) keys.next();
            JSONArray matchArray = jsonBody.getJSONObject(key).getJSONArray("matches");

            for (int i = 0; i < matchArray.length(); i++) {
                JSONObject jsonMatch = matchArray.getJSONObject(i);
                TeamMatch match = new TeamMatch();
                match.setMatchId(jsonMatch.optString("_id"));

                JSONObject hometeam = jsonMatch.getJSONObject("home_team");
                JSONObject away_team = jsonMatch.getJSONObject("away_team");

                match.setHomeTeamName(hometeam.optString("team_name_tr"));
                    match.setHomeTeamId(hometeam.optInt("_id"));
                match.setHomeTeamImageUrl("http://static.bahisadam.com" + hometeam.getJSONArray("team_logos").optString(0));
                match.setHomeTeamGoals(jsonMatch.optString("home_goals"));
                match.setAwayTeamName(away_team.optString("team_name_tr"));
                    match.setAwayTeamId(away_team.optInt("_id"));
                match.setAwayTeamImageUrl("http://static.bahisadam.com" + away_team.getJSONArray("team_logos").optString(0));
                match.setAwayTeamGoals(jsonMatch.optString("away_goals"));
                match.setLeagueName(jsonMatch.getJSONObject("league_id").optString("league_name_tr"));
                match.setLeagueId(jsonMatch.getJSONObject("league_id").optString("_id"));
                String dateString = jsonMatch.optString("match_date");
                match.setDate(dateString);
                matches.add(match);
            }
        }
        return matches;
    }

    private ArrayList<TeamFixture> parseFixturesInfo(JSONObject jsonBody) throws JSONException{
        ArrayList<TeamFixture> matches = new ArrayList<>();
        Iterator<?> keys = jsonBody.keys();
        while(keys.hasNext()) {
            String key = (String) keys.next();
            JSONArray matchArray = jsonBody.getJSONObject(key).getJSONArray("matches");
            for (int i = 0; i < matchArray.length(); i++) {
                JSONObject jsonMatch = matchArray.getJSONObject(i);
                TeamFixture match = new TeamFixture();
                match.setMatchId(jsonMatch.optString("_id"));
                match.setHomeTeamName(jsonMatch.getJSONObject("home_team").optString("team_name_tr"));
                match.setHomeTeamImageUrl("http://static.bahisadam.com" + jsonMatch.getJSONObject("home_team").getJSONArray("team_logos").optString(0));
                match.setAwayTeamName(jsonMatch.getJSONObject("away_team").optString("team_name_tr"));
                match.setAwayTeamImageUrl("http://static.bahisadam.com" + jsonMatch.getJSONObject("away_team").getJSONArray("team_logos").optString(0));
                match.setLeagueName(jsonMatch.getJSONObject("league_id").optString("league_name_tr"));
                match.setLeagueId(jsonMatch.getJSONObject("league_id").optString("_id"));
                String dateString = jsonMatch.optString("match_date");
                Date date = new DateTime(dateString).toDate();
                match.setDate(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date));
                matches.add(match);
            }
        }
        return matches;
    }

}
