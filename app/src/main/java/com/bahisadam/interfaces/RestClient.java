package com.bahisadam.interfaces;


import com.bahisadam.model.*;
import com.bahisadam.model.LiveResponse;
import com.bahisadam.model.MatchPOJO;
import com.bahisadam.model.StandingsRequest;
import com.bahisadam.model.TournamentListRequest;
import com.bahisadam.model.UpdateForecastBody;
import com.bahisadam.model.news.newsdetailmodel.NewsDetailModel;
import com.bahisadam.model.news.newsmodel.NewsModel;
import com.bahisadam.model.predictionleague.PredictionLeagueModel;
import com.bahisadam.model.predictionleague.UserPredictionModel;
import com.bahisadam.model.requests.*;

import java.util.List;

import com.bahisadam.model.teamInfo.TeamDetailResponseModel;
import com.bahisadam.model.teamInfo.TeamStats;
import retrofit2.Call;
import retrofit2.http.*;

public interface RestClient {
    @GET("./")
    Call<MatchPOJO> matchRequest(@Query("live") int live);
    @GET("match/detail/{matchId}")
    Call<MatchPOJO.MatchDetailed> matchDetailRequest(@Path("matchId") String matchId);

    @GET("league/standings/{leagueId}")
    Call<StandingsModel> leagueStandingsRequest(@Path("leagueId") Integer leagueId);

    @GET("standings/{leagueId}")
    Call<MatchPOJO.StandingsRequest> standingsRequest(@Path("leagueId") Integer leagueId);

    @GET("league/standings/{leagueId}")
    Call<StandingsRequest> standings(@Path("leagueId") Integer leagueId);

    @GET("standings/homeaway/{leagueId}")
    Call<MatchPOJO.HomeAwayRequest> homeAwayRequest(@Path("leagueId") Integer leagueId);
    @GET("forecast/match-forecast/list/{matchId}")
    Call<List<MatchPOJO.Comment>> commentsRequest(@Path("matchId") String matchId);
    @GET("forecast/like/{matchId}")
    Call<MatchPOJO.LikeUpdate> likeUpdateRequest(@Path("matchId") String matchId);
    @POST("match/updateforecast")
    Call<MatchPOJO.ForecastUpdate> updateForecastRequest( @Body UpdateForecastBody body);
    @GET("stats/byLeague/{leagueId}")
    Call<MatchPOJO.LeagueStats> statsByLeague(@Path("leagueId") Integer leagueId);
    @GET("stats/team/{teamId}/{leagueId}")
    Call<BaseResponse<TeamStats>> teamStatsByLeague(@Path("teamId") Integer teamId, @Path("leagueId") Integer leagueId);
    @GET("team/detail/{teamId}")
    Call<TeamDetailResponseModel> teamDetails(@Path("teamId") Integer teamId);
    @GET("player/profile/{playerId}")
    Call<PlayerResponseModel> playerDetails(@Path("playerId") String playerId);
    @GET("fixture/{leagueId}/{round}")
    Call<MatchPOJO.Fixture> fixture(@Path("leagueId") Integer leagueId,@Path("round") Integer roound);
    @GET("fixture/{leagueId}")
    Call<MatchPOJO.Fixture> fixture(@Path("leagueId") Integer leagueId);
    @GET("live-scores")
    Call<LiveResponse> liveMatches();
    @GET("league/tournament-list")
    Call<TournamentListRequest> tournamentList();
    @GET("forecast/weekly/accumulated")
    Call<PredictionLeagueModel>predictionLeague();
    @GET("forecast/user-forecast/list/{userId}")
    Call<List<UserPredictionModel>> userPredictionsRequest(@Path("userId") String userId);
    @GET("news/list")
    Call<NewsModel>getNews();
    @GET("news/detail/{id}")
    Call<NewsDetailModel> getDetailNews(@Path("id") String id);
    @POST("auth/login")
    Call<BaseAuthRequest.Response> loginRequest( @Body UserLoginRequest body);
    @POST("auth/device-login")
    Call<BaseAuthRequest.Response> loginRequest(@Body DeviceLoginRequest body);
    @POST("auth/register")
    Call<BaseAuthRequest.Response> registerRequest(@Body RegisterRequest body);
    @POST("forecast/update-myforecast")
    Call<UpdatePredictionRequest.Response> updatePrediction(@Body UpdatePredictionRequest body);
    @POST("device/favorite/list")
    Call<ListDeviceFavoritesRequest.Response> listFavorites(@Body ListDeviceFavoritesRequest body);
    @POST("device/favorite/addremove")
    Call<AddRemoveFavoriteRequest.Response> addRemoveFavorite(@Body AddRemoveFavoriteRequest body);
}

