package com.bahisadam.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bahisadam.NewsCurrentAdapter;
import com.bahisadam.R;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.NewsOriantedModel;
import com.bahisadam.model.news.newsmodel.NewsData;
import com.bahisadam.model.news.newsmodel.NewsModel;
import com.bahisadam.services.NewsBusService;
import com.bahisadam.utility.Utilities;
import com.bahisadam.utility.slider.animations.SliderLayout;
import com.bahisadam.utility.slider.animations.slidertypes.BaseSliderView;
import com.bahisadam.utility.slider.animations.slidertypes.TextSliderView;
import com.squareup.picasso.Picasso;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

/**
 * @author GorkemKarayel on 9.05.2017.
 */

public class NewsFragment extends BaseFragment{

    @BindView(R.id.news_slider_item_image)
    ImageView mSliderItemImage;

    @BindView(R.id.news_slider_item_title)
    TextView mSliderItemTitle;

    @BindView(R.id.news_slider_recycler)RecyclerView mNewsRecycler;
    private Response<NewsModel> mNewsModel;
    private ArrayList<NewsData> mNewsData;
    private SparseArray<NewsOriantedModel> mSliderNewsData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this,mView);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle instant) {
        super.onActivityCreated(instant);
        EventBus.getDefault().register(this);
        mSliderNewsData = new SparseArray<>();
    }

    @Override
    public void api(RestClient restClient) {
        RestClient restClient1 = restClient;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NewsBusService event) {
        if (event.exception == null) {
            mNewsModel = event.mNewsModel;
            if (mNewsModel != null) initial();
        }
    }

    private void initial() {
        mNewsData = new ArrayList<>();
        setAdapter();
        for (int i=0 ; i< mNewsModel.body().data.size() ; i++){
            if (i<1) {
                mSliderNewsData.put(i,new NewsOriantedModel(mNewsModel.body().data.get(i)._id,
                        Constant.BASE_IMAGE_ASSET + mNewsModel.body().data.get(i).asset_id,mNewsModel.body().data.get(i).title));
            }else{
                mNewsData.add(mNewsModel.body().data.get(i));
            }
        }

        final NewsData first = mNewsModel.body().data.get(0);
        ViewGroup.LayoutParams params = mSliderItemImage.getLayoutParams();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Double width =  (double)size.x;
        params.height = ((Double)(width/620 * 350)).intValue();
        mSliderItemImage.setLayoutParams(params);
        mSliderItemTitle.setText(first.title);
        Picasso.with(getActivity()).load(Constant.BASE_IMAGE_ASSET + first.asset_id).into(mSliderItemImage);
        mSliderItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.openNewsDetailActivity(getActivity(), first._id);
            }
        });
    }

    private void setAdapter() {
        NewsCurrentAdapter mNewsCurrentAdapter =
                new NewsCurrentAdapter(getActivity(),mNewsData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mNewsRecycler.setLayoutManager(mLayoutManager);
        mNewsRecycler.setAdapter(mNewsCurrentAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();;
        EventBus.getDefault().unregister(this);
    }

}
