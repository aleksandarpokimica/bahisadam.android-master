package com.bahisadam.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.model.PlayerRolesModel;
import com.bahisadam.model.PlayerRolesModelTeam;
import com.bahisadam.utility.RoundedCornersTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;

public class PlayerDetailsAdapter extends ArrayAdapter<PlayerRolesModel> {

    private ArrayList<PlayerRolesModel> playerDetails = new ArrayList<>();
    private LayoutInflater mInflater;

    public PlayerDetailsAdapter(Context context, ArrayList<PlayerRolesModel> playerDetails) {
        super(context, 0, playerDetails);
        this.playerDetails = playerDetails;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PlayerRolesModel getItem(int position) {
        return playerDetails.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.player_details_row, parent, false);
            vh = new ViewHolder();
            vh.tvRoleSeason = (TextView) convertView.findViewById(R.id.tvRoleSeason);
            vh.tvRoleTeam = (TextView) convertView.findViewById(R.id.tvRoleTeam);
            vh.tvRoleContractType = (TextView) convertView.findViewById(R.id.tvRoleContractType);
            vh.ivTeamLogoImage = (ImageView) convertView.findViewById(R.id.ivTeamLogoImage);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();

        }

        PlayerRolesModel item = getItem(position);
        PlayerRolesModelTeam team = item.prmTeam;

        String endDate = "";
        if(item.endDate == null){
            endDate = "Mevcut";
        }else{
            endDate = item.endDate.substring(0, 4);
        }
        vh.tvRoleSeason.setText(item.startDate.substring(0, 4) + "-" + endDate);
        vh.tvRoleTeam.setText(team.prmTeamName);
        vh.tvRoleContractType.setText(item.Type);
        loadLogoToView(vh.ivTeamLogoImage, item.prmTeam.prmId, true);
        return convertView;
    }

    public static void loadLogoToView(ImageView iv, int teamId, boolean transform) {
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
        TextView tvRoleSeason;
        TextView tvRoleTeam;
        TextView tvRoleContractType;
        ImageView ivTeamLogoImage;
    }
}
