package com.bahisadam.utility.slider.animations.slidertypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.bahisadam.R;


public class DefaultSliderView extends BaseSliderView{

    public DefaultSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_default,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        bindEventAndShow(v, target);
        return v;
    }
}

