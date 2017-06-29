package com.bahisadam.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bahisadam.R;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GorkemKarayel on 13.05.2017.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.MyViewHolder> {

    private Activity mActivity;
    private ArrayList<String> mTags;

    public TagAdapter(Activity mActivity , ArrayList<String> mTags) {
        this.mActivity = mActivity;
        this.mTags = mTags;
    }

    @Override
    public TagAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TagAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.news_detail_tags, parent, false));
    }

    @Override
    public void onBindViewHolder(TagAdapter.MyViewHolder holder, int position) {
        holder.mNewsTag.setText(mTags.get(position));
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.news_detail_tags)TextView mNewsTag;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,itemView);
        }
    }
}
