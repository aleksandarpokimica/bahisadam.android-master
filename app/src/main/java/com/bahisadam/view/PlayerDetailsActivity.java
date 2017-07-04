package com.bahisadam.view;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.adapter.PlayerDetailsAdapter;
import com.bahisadam.adapter.PlayerSeasonsAdapter;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.PlayerResponseModel;
import com.bahisadam.model.PlayerRolesModel;
import com.bahisadam.model.PlayerStatsModel;
import com.bahisadam.model.PlayerStatsTeamModel;
import com.bahisadam.utility.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerDetailsActivity extends AppCompatActivity {


    private String Id;
    public ArrayList<PlayerRolesModel> alPlayerRoles;
    public ArrayList<PlayerStatsTeamModel> alPlayerStats;
    private RestClient restClient;
    PlayerDetailsAdapter playerAdapter;

    //views
    @BindView(R.id.tabHost)
    TabHost tabHost;
    @BindView(R.id.playerInfoContainer)
    LinearLayout playerInfoContainer;
    @BindView(R.id.tvPlayerName)
    TextView tvPlayerName;
    @BindView(R.id.tvPlayerDetails)
    TextView tvPlayerDetails;
    @BindView(R.id.tvPlayerNationalTeam)
    TextView tvPlayerNationalTeam;
    @BindView(R.id.tvFoot)
    TextView tvFoot;
    @BindView(R.id.tvPlayerAge)
    TextView tvPlayerAge;
    @BindView(R.id.tvPlayerPosition)
    TextView tvPlayerPosition;
    @BindView(R.id.tvPlayerHeight)
    TextView tvPlayerHeight;
    @BindView(R.id.tvPlayerWeight)
    TextView tvPlayerWeight;
    @BindView(R.id.player_details_playerLogo)
    CircleImageView player_details_playerLogo;
    @BindView(R.id.team_details_teamLogo)
    CircleImageView team_details_teamLogo;
    @BindView(R.id.lvPlayerRoles)
    ListView lvPlayerRoles;
    @BindView(R.id.llPlayerCareerStatisticsTotals)
    LinearLayout llPlayerCareerStatisticsTotals;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tvMatches)
    TextView tvMatches;
    @BindView(R.id.ivGoals)
    ImageView ivGoals;
    @BindView(R.id.ivAssists)
    ImageView ivAssists;
    @BindView(R.id.ivYellowCards)
    ImageView ivYellowCards;
    @BindView(R.id.ivRedCards)
    ImageView ivRedCards;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.tvTotalMatches)
    TextView tvTotalMatches;
    @BindView(R.id.tvTotalGoals)
    TextView tvTotalGoals;
    @BindView(R.id.tvTotalAssists)
    TextView tvTotalAssists;
    @BindView(R.id.tvTotalYellowCards)
    TextView tvTotalYellowCards;
    @BindView(R.id.tvTotalRedCards)
    TextView tvTotalRedCards;
    @BindView(R.id.listViewPlayerSeasons)
    ListView listViewPlayerSeasons;
    @BindView(R.id.listViewPlayerNational)
    ListView listViewPlayerNational;

    //but they merge just the same, but okay, you want me to use gitbash to commit files?ok
    //use strings.xml instead of hard coded strings
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        ButterKnife.bind(this);

        restClient = Utilities.buildRetrofit();

        Id = getIntent().getExtras().getString("ID");

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Kisisel Bilgiler");//put strings into stringsxml the app is running on my device, i dont use an emulator
        spec.setContent(R.id.tab1);
        spec.setIndicator("Kisisel Bilgiler");
        tabHost.addTab(spec);

        //Tab 2
        spec = tabHost.newTabSpec("Kariyer");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Kariyer");
        tabHost.addTab(spec);

        //Tab 3
        spec = tabHost.newTabSpec("Haberler");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Haberler");
        tabHost.addTab(spec);

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
        }

        loadData();

    }

    private void loadData() {

        Call<PlayerResponseModel> call = restClient.playerDetails(Id);
        call.enqueue(new Callback<PlayerResponseModel>() {
            @Override
            public void onResponse(Call<PlayerResponseModel> call, Response<PlayerResponseModel> response) {
                PlayerResponseModel responseModel = response.body();
                tvPlayerName.setText("#" + responseModel.getData().prModel.get(0).jerseyNumber + " " + responseModel.getData().name);
                Picasso.with(getApplicationContext()).load(responseModel.getData().asset_url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(player_details_playerLogo);
                team_details_teamLogo.setImageResource(R.drawable.au);
                tvPlayerNationalTeam.setText(responseModel.getData().nationality);
                tvFoot.setText(responseModel.getData().preferredFoot);
                tvPlayerAge.setText(responseModel.getData().age);
                tvPlayerPosition.setText(responseModel.getData().type);
                tvPlayerHeight.setText(responseModel.getData().height + " cm");
                tvPlayerWeight.setText(responseModel.getData().weight + " kg");

                alPlayerRoles = new ArrayList<>();
                alPlayerRoles.addAll(responseModel.getData().prModel);
                playerAdapter = new PlayerDetailsAdapter(PlayerDetailsActivity.this, alPlayerRoles);
                lvPlayerRoles.setAdapter(playerAdapter);
                //tab2
                Picasso.with(PlayerDetailsActivity.this).load(R.drawable.goal).fit().centerCrop().into(ivGoals);
                Picasso.with(PlayerDetailsActivity.this).load(R.drawable.assist).fit().centerCrop().into(ivAssists);
                Picasso.with(PlayerDetailsActivity.this).load(R.drawable.yellow_card).fit().centerCrop().into(ivYellowCards);
                Picasso.with(PlayerDetailsActivity.this).load(R.drawable.red_card).fit().centerCrop().into(ivRedCards);

                tvTotalMatches.setText(responseModel.getData().psModel.totalModel.matchesPlayed);
                tvTotalGoals.setText(responseModel.getData().psModel.totalModel.goalsScored);
                tvTotalAssists.setText(responseModel.getData().psModel.totalModel.assists);
                tvTotalYellowCards.setText(responseModel.getData().psModel.totalModel.yellowCards);
                int totalRedCards = Integer.parseInt(responseModel.getData().psModel.totalModel.redCards) +
                        Integer.parseInt(responseModel.getData().psModel.totalModel.yellowRedCards);
                tvTotalRedCards.setText(totalRedCards + "");

                alPlayerStats = new ArrayList<PlayerStatsTeamModel>();
                alPlayerStats.addAll(responseModel.getData().psModel.pstModel);
                PlayerSeasonsAdapter psAdapter = new PlayerSeasonsAdapter(PlayerDetailsActivity.this, alPlayerStats);
                listViewPlayerSeasons.setAdapter(psAdapter);

            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *The app is stuck, run it again
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<PlayerResponseModel> call, Throwable t) {
                Log.e("reali", t.toString());
            }
        });
    }
}