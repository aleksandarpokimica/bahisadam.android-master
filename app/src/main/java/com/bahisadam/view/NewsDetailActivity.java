package com.bahisadam.view;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bahisadam.NewsRelaitedAdapter;
import com.bahisadam.R;
import com.bahisadam.adapter.TagAdapter;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.news.newsdetailmodel.NewsDetailModel;
import com.bahisadam.utility.Utilities;
import com.squareup.picasso.Picasso;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author GorkemKarayel on 12.05.2017.
 */

public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.news_detail_title)TextView mDetailTitle;
    @BindView(R.id.news_detail_onesummer)TextView mDetailOneSummer;
    @BindView(R.id.news_detail_time)TextView mDetailTime;
    @BindView(R.id.news_detail_images)ImageView mDetailImages;
    @BindView(R.id.news_detail_content)TextView mContent;
    @BindView(R.id.toolbar)Toolbar mToolbar;
    @BindView(R.id.news_detail_source)TextView mSource;
    @BindView(R.id.news_detail_eticets)RecyclerView mTagsRecycler;
    @BindView(R.id.news_detail_relatited_news)RecyclerView mRelaitedNews;
    @BindView(R.id.news_detail_player) TextView mPlayer;

    private Response<NewsDetailModel> mNewsDetailModel;
    private RestClient mRestClient;
    private String mNewsId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.news);
        mNewsId = this.getIntent().getStringExtra(BaseActivity.ID);
        getDataList();
    }

    private void getDataList() {
        mRestClient.getDetailNews(mNewsId).enqueue(new Callback<NewsDetailModel>() {
            @Override
            public void onResponse(Call<NewsDetailModel> call, Response<NewsDetailModel> response) {
                mNewsDetailModel = response;
                initial(mNewsDetailModel);
            }
            @Override
            public void onFailure(Call<NewsDetailModel> call, Throwable t) {
            }
        });
    }

    public String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return String.valueOf(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            return String.valueOf(Html.fromHtml(html));
        }
    }

    @Override
    public void api(RestClient restClient) {
        this.mRestClient = restClient;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initial(final Response<NewsDetailModel> mNewsDetailModel) {
        mDetailTime.setText(mNewsDetailModel.body().data.news.inserted_date.split("T")[0]);
        mDetailTitle.setText(mNewsDetailModel.body().data.news.title);
        mDetailOneSummer.setText(stripHtml(mNewsDetailModel.body().data.news.summary));
        ViewGroup.LayoutParams params = mDetailImages.getLayoutParams();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Double width =  (double)size.x;
        params.height = ((Double)(width/620 * 350)).intValue();
        mDetailImages.setLayoutParams(params);

        Picasso.with(this).load(Constant.BASE_IMAGE_ASSET+ mNewsDetailModel.body().data.news.asset_id).into(mDetailImages);

        mContent.setText(stripHtml(mNewsDetailModel.body().data.news.content));
        mSource.setText("Kaynak:"+ mNewsDetailModel.body().data.news.resource);
        if(mNewsDetailModel.body().data.news.player != null) {
            mPlayer.setVisibility(View.VISIBLE);
            mPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utilities.openPlayerDetails(NewsDetailActivity.this,
                            mNewsDetailModel.body().data.news.player.player_id,
                            mNewsDetailModel.body().data.news.player.player_name);
                }
            });
        } else {
            mPlayer.setVisibility(View.GONE);
        }

        getNewsTag();
        getNewsRelaitedNews();
    }

    private void getNewsTag() {
        TagAdapter mTagAdapter =
                new TagAdapter(this,mNewsDetailModel.body().data.news.tags);

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mTagsRecycler.setLayoutManager(mLayoutManager);
        mTagsRecycler.setItemAnimator(new DefaultItemAnimator());
        mTagsRecycler.setAdapter(mTagAdapter);
    }

    private void getNewsRelaitedNews() {
        NewsRelaitedAdapter mNewsCurrentAdapter =
                new NewsRelaitedAdapter(this,mNewsDetailModel.body().data.related_news);
        mNewsCurrentAdapter.addOnClick(new NewsRelaitedAdapter.SonClick() {
            @Override
            public void isOnClick(boolean isClick) {
                if (isClick) finish();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRelaitedNews.setLayoutManager(mLayoutManager);
        mRelaitedNews.setNestedScrollingEnabled(false);
        mRelaitedNews.setAdapter(mNewsCurrentAdapter);
    }
}
