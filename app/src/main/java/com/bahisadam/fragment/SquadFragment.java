package com.bahisadam.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.adapter.FavoriteMatchesAdapter;
import com.bahisadam.holder.CardHolderBase;
import com.bahisadam.model.MatchPOJO;
import com.bahisadam.model.teamInfo.PlayerStatistics;
import com.bahisadam.model.teamInfo.TeamDetailModel;
import com.bahisadam.utility.RoundedCornersTransform;
import com.bahisadam.utility.Utilities;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class SquadFragment extends Fragment {

    public static final String ARG_TEAM_INFO = "team_info";
    public static final String ARG_TEAM_MANAGER = "team_manager";
    public static final String ARG_TEAM_MANAGER_NATIONLITY = "team_manager_nationality";
    public static final String ARG_TEAM_MANAGER_COUNTRYCODE= "team_manager_country_code";
    public static final String ARG_TEAM_NAME = "team_name";
    public static final String ARG_COL1 = "team_col1";
    public static final String ARG_COL2 = "team_col2";
    public ArrayList<PlayerStatistics> mPlayers = new ArrayList<>();
    public ArrayList<PlayerStatistics> mKeyPlayers = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView mKeyPlayersRecyclerView;

    private String manager;
    private String managerNationality;
    private String col1, col2;

    ArrayList<squad> list;

    public static SquadFragment newInstance(TeamDetailModel model, ArrayList<PlayerStatistics> keyPlayers) {

        SquadFragment fragment = new SquadFragment();
        Bundle args = new Bundle();
        if(model.getManager() != null ) {
            args.putString(ARG_TEAM_MANAGER, model.getManager().getName());
            args.putString(ARG_TEAM_MANAGER_NATIONLITY, model.getManager().getNationality());
            args.putString(ARG_TEAM_MANAGER_COUNTRYCODE, model.getManager().getCountry_code());
        }

        args.putString(ARG_COL1, model.getColor1());
        args.putString(ARG_COL2, model.getColor2());
        fragment.setArguments(args);
        fragment.mPlayers = (ArrayList<PlayerStatistics>) model.getSquad();
        fragment.mKeyPlayers = keyPlayers;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            manager = getArguments().getString(ARG_TEAM_MANAGER);
            managerNationality = getArguments().getString(ARG_TEAM_MANAGER_NATIONLITY);
            col1 = getArguments().getString(ARG_COL1);
            col2 = getArguments().getString(ARG_COL2);

            list = new ArrayList<>();

          list.add(new squad("Teknik Direktör"));
          list.add(new squad(manager, managerNationality));

            list.add(new squad(getResources().getString(R.string.keeper)));
            for (PlayerStatistics tp_info : mPlayers) {
                if (tp_info.getRole().equals("1")) {
                    list.add(new squad(tp_info));
                }
            }

            list.add(new squad(getResources().getString(R.string.defense)));

            for (PlayerStatistics tp_info : mPlayers) {
                if (tp_info.getRole().equals("2")) {
                    list.add(new squad(tp_info));
                }
            }


            list.add(new squad(getResources().getString(R.string.midfield)));
            for (PlayerStatistics tp_info : mPlayers) {
                if (tp_info.getRole().equals("3")) {
                    list.add(new squad(tp_info));
                }
            }



            list.add(new squad(getResources().getString(R.string.forward)));

            for (PlayerStatistics tp_info : mPlayers) {
                if (tp_info.getRole().equals("4")) {
                    list.add(new squad(tp_info));
                }
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_squad,container,false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_squad);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(mPlayers!=null) mRecyclerView.setAdapter(new SquadAdapter(list));

        if(mKeyPlayers != null && mKeyPlayers.size() > 0) {
            mKeyPlayersRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_key_players);
            mKeyPlayersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            mKeyPlayersRecyclerView.setAdapter((new KeyPlayersAdapter(mKeyPlayers)));
        } else {
            LinearLayout keyPlayersTitle = (LinearLayout) view.findViewById(R.id.key_players_title);
            keyPlayersTitle.setVisibility(View.GONE);
        }

        return view;
    }

    public class TitleVH extends CardHolderBase {

        @BindView(R.id.title) TextView titleTv;

        public TitleVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    class squad {

        boolean isTitle;
        boolean isManager;
        String title;
        String name;
        String nationality;
        Integer goals;
        Integer assists;
        Integer cards;
        PlayerStatistics teamPlayerInfo;

        public squad(String title) {
            isTitle = true;
            this.title = title;
        }

        public squad(String name, String nationality) {
            isTitle = false;
            isManager = true;
            this.name = name;
            this.nationality = nationality;
        }

        public squad(PlayerStatistics teamPlayerInfo) {
            isTitle = false;
            isManager = false;
            goals = teamPlayerInfo.getGoals();
            assists = teamPlayerInfo.getAssists();
            cards = teamPlayerInfo.getCards();
            this.teamPlayerInfo = teamPlayerInfo;
        }

    }

    public class SquadAdapter extends RecyclerView.Adapter<CardHolderBase> {

        public static final int TYPE_TITLE = 1,TYPE_ITEM = 2;
        private ArrayList<squad> mPlayers;
        private OnPlayerClickListener mListener = null;

        SquadAdapter(ArrayList<squad> players) {
            mPlayers = players;
        }

        @Override
        public CardHolderBase onCreateViewHolder(ViewGroup parent, int viewType) {

            int layout = R.layout.stats_item;

            switch (viewType) {
                case TYPE_TITLE: layout = R.layout.item_title;
                    View titleView = LayoutInflater.from(parent.getContext())
                            .inflate(layout, parent, false);
                    return new TitleVH(titleView);
                case TYPE_ITEM : layout = R.layout.team_info_squad_item;
                    View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(layout, parent, false);
                    return new SquadHolder(itemView);
            }

            return null;

        }

        @Override
        public void onBindViewHolder(CardHolderBase holder1, int position) {

            if (holder1 instanceof SquadHolder) {

                SquadHolder holder = (SquadHolder) holder1;
                if (mPlayers.get(position).isManager) {
                    holder.bindItem(mPlayers.get(position).name, mPlayers.get(position).nationality);
                } else {
                    PlayerStatistics player = mPlayers.get(position).teamPlayerInfo;

                    holder.bindItem(player);

                    this.setOnClickListener(new OnPlayerClickListener() {
                        @Override
                        public void onPlayerClick(int position) {
                             Utilities.openPlayerDetails(getActivity(), mPlayers.get(position).teamPlayerInfo.get_id(), mPlayers.get(position).teamPlayerInfo.getName());
                        }
                    });
                }



            } else if (holder1 instanceof TitleVH) {

                TitleVH holder = (TitleVH) holder1;
                holder.titleTv.setText(mPlayers.get(position).title);

            }
        }

        @Override
        public int getItemViewType(int position) {

            if (mPlayers.get(position).isTitle) {
                return TYPE_TITLE;
            } else {
                return TYPE_ITEM;
            }
        }

        @Override
        public int getItemCount() {
            return mPlayers.size();
        }

        public void setOnClickListener(OnPlayerClickListener listener){
            mListener = listener;
        }

        public class SquadHolder extends CardHolderBase implements View.OnClickListener {

            private PlayerStatistics mPlayer;

            @BindView(R.id.text_view_squad_number)
            TextView mTextViewNumber;

            @BindView(R.id.image_squad)
            ImageView mImageSquad;

            @BindView(R.id.image_squad1)
            ImageView mImageSquad1;

            @BindView(R.id.image_squad2)
            ImageView mImageSquad2;

            @BindView(R.id.text_view_squad_name)
            TextView mTextViewName;

            @BindView(R.id.text_view_squad_goals)
            TextView mTextViewGoals;

            @BindView(R.id.tv_country)
            TextView mCountry;

            @BindView(R.id.iv_country_flag)
            ImageView mCountry_flag;

            @BindView(R.id.goal_section)
            View goal_section;

            @BindView(R.id.layout) View layout;

//        private ImageView mImageViewPlayer;
//        private ImageView mCountryImage;


            SquadHolder(View itemView) {
                super(itemView);
//            mImageViewPlayer = (ImageView)itemView.findViewById(R.id.image_view_squad_image);
//            mCountryImage = (ImageView)itemView.findViewById(R.id.iv_country_flag);
                ButterKnife.bind(this,itemView);
                layout.setOnClickListener(this);
            }

            void bindItem(PlayerStatistics player){
                try {

                    mPlayer = player;
                    mTextViewNumber.setText((mPlayer.getSquadNumber() == null|| mPlayer.getSquadNumber().equals("null")) ? "-":mPlayer.getSquadNumber());
                    //            mImageSquad.setImageResource(R.drawable.kit_striped);

                    Drawable drawable = getResources().getDrawable(R.drawable.kit_striped);
                    Drawable drawable1 = getResources().getDrawable(R.drawable.kit_striped1);

                    if (!col1.isEmpty()) {
                        drawable = DrawableCompat.wrap(drawable);
                        DrawableCompat.setTint(drawable, Color.parseColor(col1));
                    }

                    if (!col2.isEmpty()) {
                        drawable1 = DrawableCompat.wrap(drawable1);
                        DrawableCompat.setTint(drawable1, Color.parseColor(col2));
                    }

                    mImageSquad.setImageResource(android.R.color.transparent);

                    mImageSquad.setImageDrawable(drawable);
                    mImageSquad1.setImageDrawable(drawable1);
                    mImageSquad2.setImageResource(R.drawable.kit_striped2);
                    mTextViewGoals.setText(Integer.toString(mPlayer.getGoals()));

                    //            mImageSquad.setImageResource(android.R.color.transparent);
                    mCountry_flag.setImageResource(android.R.color.transparent);
                    mTextViewName.setText(mPlayer.getName());

                    if(mPlayer.getStatistics() != null) {
                        mTextViewGoals.setText(mPlayer.getStatistics().getGoals_scored() > 0 ? mPlayer.getStatistics().getGoals_scored() + "" : " -");
                    }

                    mCountry.setText(mPlayer.getNationality());

                    layout.setOnClickListener(this);

                    Context ctx = getContext();
                    int id = ctx.getResources().getIdentifier(player.getCountry_code(), "drawable", ctx.getPackageName());
                    if (id != 0) {
                        Bitmap bitmap = Utilities.getBitmapCountry(id, ctx);
                        if (bitmap != null) {
                            mCountry_flag.setImageBitmap(bitmap);
                        }
                    }
                } catch (Exception ex) {
                    Log.d("process error", ex.getMessage());
                }

//            if(mPlayer.getCountryCode() != null) {
//                String country_code = mPlayer.getCountryCode();
//                int imageResource = getResources().getIdentifier(country_code, "drawable", getActivity().getPackageName());
//                if(imageResource!=0) mCountry_flag.setBackgroundResource(imageResource);
//            }

//            try {
//                Picasso.with(getActivity()).load(mPlayer.getImageUrl()).transform(new RoundedCornersTransform(5)).fit().into(mImageViewPlayer);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
            }

            void bindItem(String name, String natio){ //manager

                goal_section.setVisibility(View.GONE);
                mTextViewNumber.setText("");
                mImageSquad.setImageResource(R.drawable.manager);
                mImageSquad1.setImageResource(android.R.color.transparent);
                mImageSquad2.setImageResource(android.R.color.transparent);
                mCountry_flag.setImageResource(android.R.color.transparent);
                mTextViewName.setText(name);
                mCountry.setText(natio);


            }

            @Override
            public void onClick(View v) {
                if(mListener ==null) return;
                mListener.onPlayerClick(getAdapterPosition());
            }
        }

    }

    public interface OnPlayerClickListener {
        void onPlayerClick(int position);
    }


    public class KeyPlayersAdapter extends RecyclerView.Adapter<KeyPlayersAdapter.KeyPlayerViewHolder> {
        private ArrayList<PlayerStatistics> mKeyPlayers;
        private OnPlayerClickListener mListener = null;

        public KeyPlayersAdapter(ArrayList<PlayerStatistics> players){
            mKeyPlayers = players;
            if(mKeyPlayers == null) {
                mKeyPlayers = new ArrayList<PlayerStatistics>();
            }
        }

        @Override
        public KeyPlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new KeyPlayerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.key_players_item, parent, false));
        }

        @Override
        public void onBindViewHolder(KeyPlayerViewHolder holder, int position) {
            try{

                PlayerStatistics p = mKeyPlayers.get(position);
                if(p == null) {
                    return;
                }

                Drawable drawable = getResources().getDrawable(R.drawable.kit_striped);
                Drawable drawable1 = getResources().getDrawable(R.drawable.kit_striped1);

                if (!col1.isEmpty()) {
                    drawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(drawable, Color.parseColor(col1));
                }

                if (!col2.isEmpty()) {
                    drawable1 = DrawableCompat.wrap(drawable1);
                    DrawableCompat.setTint(drawable1, Color.parseColor(col2));
                }
                holder.mSquadNumber.setText(p.getSquadNumber().equals("null") ? "-": p.getSquadNumber());
                holder.mImageSquad.setImageResource(android.R.color.transparent);
                holder.mImageSquad.setImageDrawable(drawable);
                holder.mImageSquad1.setImageDrawable(drawable1);
                holder.mImageSquad2.setImageResource(R.drawable.kit_striped2);
                holder.mName.setText(p.getName());
                holder.mGoals.setText(Integer.toString(p.getGoals()));
                holder.mAssists.setText(Integer.toString(p.getAssists()));

                this.setOnClickListener(new OnPlayerClickListener() {
                    @Override
                    public void onPlayerClick(int position) {
                        Utilities.openPlayerDetails(getActivity(), mKeyPlayers.get(position).getId(),
                                mKeyPlayers.get(position).getName());
                    }
                });

            } catch (Exception ex) {
                Log.d("error", ex.getMessage());
            }

//            mImageSquad.setImageResource(android.R.color.transparent);

        }


        public void setOnClickListener(OnPlayerClickListener listener){
            mListener = listener;
        }

        @Override
        public int getItemCount() {

            return mPlayers.size();
        }

        public class KeyPlayerViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
            @BindView(R.id.image_squad) ImageView mImageSquad;
            @BindView(R.id.image_squad1) ImageView mImageSquad1;
            @BindView(R.id.image_squad2) ImageView mImageSquad2;
            @BindView(R.id.text_view_squad_number) TextView mSquadNumber;
            @BindView(R.id.name) TextView mName;
            @BindView(R.id.goals) TextView mGoals;
            @BindView(R.id.assists) TextView mAssists;
            @BindView(R.id.rootLayout)
            RelativeLayout mRoot;

            public KeyPlayerViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mRoot.getLayoutParams());
                params.width = Utilities.getScreenWidth(getContext())/4;
                mRoot.setLayoutParams(params);
                mRoot.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                if(mListener ==null) return;
                mListener.onPlayerClick(getAdapterPosition());
            }
        }
    }
}
