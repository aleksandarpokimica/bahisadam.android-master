package com.bahisadam;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.model.news.newsdetailmodel.NewsDetailRelatedNews;
import com.bahisadam.utility.Utilities;
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GorkemKarayel on 13.05.2017.
 */

public class NewsRelaitedAdapter  extends RecyclerView.Adapter<NewsRelaitedAdapter.MyViewHolder> {

    private ArrayList<NewsDetailRelatedNews> mNewsData;
    private Activity mActivity;
    private SonClick mSonClick;

    public NewsRelaitedAdapter(Activity mActivity,ArrayList<NewsDetailRelatedNews> mNewsData) {
        this.mNewsData = mNewsData;
        this.mActivity = mActivity;
    }

    @Override
    public NewsRelaitedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsRelaitedAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsRelaitedAdapter.MyViewHolder holder, final int position) {
        DateFormat df = new SimpleDateFormat("yyyy.EE.dd");
        // 2017-04-28T22:14:03.735Z
        String date = df.format(Calendar.getInstance().getTime());
        if (date.equalsIgnoreCase(mNewsData.get(position).inserted_date.split("T")[0])) {
            holder.mNewsTime.setText(mNewsData.get(position).inserted_date.split("T")[1].split(".")[0]);
        } else {
            holder.mNewsTime.setText(mNewsData.get(position).inserted_date.split("T")[0]);
        }
        holder.mNewsSummer.setText(stripHtml(mNewsData.get(position).summary));
        holder.mNewsTitle.setText(mNewsData.get(position).title);
        Picasso.with(mActivity).load(Constant.BASE_IMAGE_ASSET+mNewsData.get(position).asset_id).into(holder.mNewsImages);
        holder.mItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.openNewsDetailActivity(mActivity,mNewsData.get(position)._id);
                mSonClick.isOnClick(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.news_title)TextView mNewsTitle;
        @BindView(R.id.news_summery)TextView mNewsSummer;
        @BindView(R.id.news_images)ImageView mNewsImages;
        @BindView(R.id.news_item_container)CardView mItemContainer;
        @BindView(R.id.news_time)TextView mNewsTime;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,itemView);
        }
    }

    public String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return String.valueOf(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            return String.valueOf(Html.fromHtml(html));
        }
    }

    public void addOnClick(SonClick sonClick){
        this.mSonClick = sonClick;
    }

    public interface SonClick{
        void isOnClick(boolean isClick);
    }
}

