package com.bahisadam.utility.slider.animations;

import android.view.View;

public interface BaseAnimationInterface {


    public void onPrepareCurrentItemLeaveScreen(View current);


    public void onPrepareNextItemShowInScreen(View next);


    public void onCurrentItemDisappear(View view);


    public void onNextItemAppear(View view);
}
