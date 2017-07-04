package com.bahisadam.adapter;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.model.PlayerStatsTeamModel;
import com.bahisadam.model.PlayerStatsTeamModelSeasons;
import com.bahisadam.utility.RoundedCornersTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;

/**
 * Created by Aleksandar on 6/30/2017.
 */

public class PlayerSeasonsAdapter extends ArrayAdapter<PlayerStatsTeamModel> {

    private ArrayList<PlayerStatsTeamModel> playerStatsTeam = new ArrayList<PlayerStatsTeamModel>();
    private LayoutInflater mInflater;
    private ArrayList<PlayerStatsTeamModelSeasons> alPlayerSeasonsStats;

    public PlayerSeasonsAdapter(Context context, ArrayList<PlayerStatsTeamModel> playerStatsTeam) {
        super(context, 0, playerStatsTeam);
        this.playerStatsTeam = playerStatsTeam;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PlayerStatsTeamModel getItem(int position) {
        return playerStatsTeam.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.player_seasons_row, parent, false);
            vh = new ViewHolder();
            vh.lvPlayerSeasonsStats = (ListView) convertView.findViewById(R.id.lvPlayerSeasonsStats);
            vh.tvTeamName = (TextView) convertView.findViewById(R.id.tvPSRTeamName);
            vh.ivTeamLogoImage = (ImageView) convertView.findViewById(R.id.ivPSRTeamLogo);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();

        }
        PlayerStatsTeamModel item = getItem(position);
        alPlayerSeasonsStats = new ArrayList<PlayerStatsTeamModelSeasons>();
        alPlayerSeasonsStats.addAll(getItem(position).pstmsModel);

        loadLogoToView(vh.ivTeamLogoImage, item.pstmtModel.id, true);
        vh.tvTeamName.setText(item.pstmtModel.teamName);
        PlayerSeasonsStatsAdapter pssAdapter = new PlayerSeasonsStatsAdapter(getContext(), alPlayerSeasonsStats);
        vh.lvPlayerSeasonsStats.setAdapter(pssAdapter);
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
        ImageView ivTeamLogoImage;
        TextView tvTeamName;
        ListView lvPlayerSeasonsStats;
    }
}
