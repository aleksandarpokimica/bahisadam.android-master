package com.bahisadam.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.model.PlayerRolesModel;

import java.util.ArrayList;

public class PlayerDetailsAdapter extends ArrayAdapter<PlayerRolesModel>{

    private Activity activity;
    public ArrayList<PlayerRolesModel> playerDetails = new ArrayList<>();
    Context context;
    private LayoutInflater mInflater;

    public PlayerDetailsAdapter(Activity activity, ArrayList<PlayerRolesModel> playerDetails) {
        super(activity, 0, playerDetails);
        this.playerDetails = playerDetails;
        this.activity = activity;
    }

    @Override
    public PlayerRolesModel getItem(int position) {
        return playerDetails.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if (convertView == null) {
            View view = mInflater.inflate(R.layout.player_details_row, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        PlayerRolesModel item = getItem(position);

        vh.tvJerseyNumber.setText(item.jerseyNumber);

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final TextView tvJerseyNumber;

        private ViewHolder(RelativeLayout rootView, TextView tvJerseyNumber) {
            this.rootView = rootView;
            this.tvJerseyNumber = tvJerseyNumber;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView tv = (TextView) rootView.findViewById(R.id.tvJerseyNumber);
            return new ViewHolder(rootView, tv);
        }
    }

}
