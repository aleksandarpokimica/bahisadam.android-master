package com.bahisadam.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.model.PlayerRolesModel;
import com.bahisadam.model.PlayerRolesModelTeam;
import com.bahisadam.model.PlayerStatsModel;
import com.bahisadam.model.PlayerStatsTeamModel;
import com.bahisadam.model.PlayerStatsTeamModelSeasons;
import com.bahisadam.utility.RoundedCornersTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;

/**
 * Created by Aleksandar on 7/3/2017.
 */

public class PlayerSeasonsStatsAdapter extends ArrayAdapter{
    private ArrayList<PlayerStatsTeamModelSeasons> playerSeasons = new ArrayList<PlayerStatsTeamModelSeasons>();
    private LayoutInflater mInflater;

    public PlayerSeasonsStatsAdapter(Context context, ArrayList<PlayerStatsTeamModelSeasons> playerSeasons) {
        super(context, 0, playerSeasons);
        this.playerSeasons = playerSeasons;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PlayerStatsTeamModelSeasons getItem(int position) {
        return playerSeasons.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.player_seasons_stats_row, parent, false);
            vh = new ViewHolder();
            vh.tvPSSRCompetition = (TextView) convertView.findViewById(R.id.tvPSSRCompetition);
            vh.tvPSSRMatchesPlayed = (TextView) convertView.findViewById(R.id.tvPSSRMatchesPlayed);
            vh.tvPSSRGoalsScored = (TextView) convertView.findViewById(R.id.tvPSSRGoalsScored);
            vh.tvPSSRAssists = (TextView) convertView.findViewById(R.id.tvPSSRAssists);
            vh.tvPSSRYellowCards = (TextView) convertView.findViewById(R.id.tvPSSRYellowCards);
            vh.tvPSSRRedCards = (TextView) convertView.findViewById(R.id.tvPSSRRedCards);
            vh.ivPSSRCompetitionLogo = (ImageView) convertView.findViewById(R.id.ivPSSRCompetitionLogo);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();

        }
        PlayerStatsTeamModelSeasons pstmsModel = getItem(position);

        vh.tvPSSRCompetition.setText(pstmsModel.pstmSeasonModel.name);
        vh.tvPSSRMatchesPlayed.setText(pstmsModel.pstmsModel.matchesPlayed);
        vh.tvPSSRGoalsScored.setText(pstmsModel.pstmsModel.goalsScored);
        vh.tvPSSRAssists.setText(pstmsModel.pstmsModel.assists);
        vh.tvPSSRYellowCards.setText(pstmsModel.pstmsModel.yellowCards);
        int totalRedCards = Integer.parseInt(pstmsModel.pstmsModel.yellowRedCards) + Integer.parseInt(pstmsModel.pstmsModel.redCards);
        vh.tvPSSRRedCards.setText(totalRedCards + "");
        //loadLogoToView(vh.ivPSSRCompetitionLogo, pstmsModel.pstmSeasonModel.id, true);
        return convertView;
    }

    public static void loadLogoToView(ImageView iv, String teamId, boolean transform) {
        String image = Constant.IMAGES_ROOT +
                Constant.LOGO_PATH +
                teamId +
                Constant.LOGO_EXTENSION;
        RequestCreator img = Picasso.with(iv.getContext())
                .load(image);
        if (transform) img.transform(new RoundedCornersTransform(2));
        img.into(iv);
    }

    static class ViewHolder {
        ImageView ivPSSRCompetitionLogo;
        TextView tvPSSRCompetition;
        TextView tvPSSRMatchesPlayed;
        TextView tvPSSRGoalsScored;
        TextView tvPSSRAssists;
        TextView tvPSSRYellowCards;
        TextView tvPSSRRedCards;
    }
}
