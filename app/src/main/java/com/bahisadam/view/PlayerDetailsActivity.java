package com.bahisadam.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.adapter.PlayerDetailsAdapter;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.PlayerResponseModel;
import com.bahisadam.model.PlayerRolesModel;
import com.bahisadam.utility.Utilities;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerDetailsActivity extends AppCompatActivity {

    TabHost tabHost;
    private String Id;
    public static String playerUrl, json;
    public static PlayerResponseModel playerModel;
    public ArrayList<PlayerRolesModel> alPlayerRoles;
    private ListView lvPlayerRoles;
    private PlayerDetailsAdapter adapter;
    private View parentView;
    private RestClient restClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        alPlayerRoles = new ArrayList<>();
        lvPlayerRoles = (ListView) findViewById(R.id.lvPlayerRoles);
        parentView = findViewById(R.id.parentLayout);

        restClient = Utilities.buildRetrofit();

        Id = getIntent().getExtras().getString("ID");

        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Kisisel Bilgiler");
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

        loadData();

    }

    private void loadData() {

        Call<PlayerResponseModel> call = restClient.playerDetails(Id);
        call.enqueue(new Callback<PlayerResponseModel>() {
            @Override
            public void onResponse(Call<PlayerResponseModel> call, Response<PlayerResponseModel> response) {

                PlayerResponseModel responseModel = response.body();

            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *
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

/*PlayerDetailsAdapter playerAdapter = new PlayerDetailsAdapter(PlayerDetailsActivity.this, R.layout.player_details_row, playerModel);

        final ListView lvPlayerRoles = (ListView) findViewById(R.id.lvPlayerRoles);
        lvPlayerRoles.setAdapter(playerAdapter);*/


        /*ReadUrlJson readUrlJson = new ReadUrlJson();
        readUrlJson.execute();*/

        /*final ProgressDialog dialog;

        dialog = new ProgressDialog(PlayerDetailsActivity.this);
        dialog.setTitle(getString(R.string.string_getting_json_title));
        dialog.setMessage(getString(R.string.string_getting_json_message));
        dialog.show();

        ApiService api = RetroClient.getApiService();

        Call<PlayerRolesList> call = api.getMyJSON();

        call.enqueue(new Callback<PlayerRolesList>() {
            @Override
            public void onResponse(Call<PlayerRolesList> call, Response<PlayerRolesList> response) {
                //Dismiss Dialog
                dialog.dismiss();

                if(response.isSuccessful()) {
                    *//**
 * Got Successfully
 *//*
                    Toast.makeText(PlayerDetailsActivity.this, "Successful", Toast.LENGTH_LONG).show();
                    alPlayerRoles = response.body().getPlayerRoles();

                    *//**
 * Binding that List to Adapter
 *//*
                    adapter = new PlayerDetailsAdapter(PlayerDetailsActivity.this, alPlayerRoles);
                    lvPlayerRoles.setAdapter(adapter);

                } else {
                    Toast.makeText(PlayerDetailsActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            *//**
 * Invoked when a network exception occurred talking to the server or when an unexpected
 * exception occurred creating the request or processing the response.
 *
 * @param call
 * @param t
 *//*
            @Override
            public void onFailure(Call<PlayerRolesList> call, Throwable t) {
                Toast.makeText(PlayerDetailsActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                Log.e("JSON Error", t.toString());
                dialog.dismiss();
            }
        });
*/

/*
class ReadUrlJson extends AsyncTask{

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            json = readUrl(playerUrl);
            json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        playerModel = gson.fromJson(json, PlayerResponseModel.class);

        for(PlayerRolesModel roleItem : playerModel.data.prModel){
            alPlayerRoles.add(roleItem);
        }

        return playerModel;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    private String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }*/
