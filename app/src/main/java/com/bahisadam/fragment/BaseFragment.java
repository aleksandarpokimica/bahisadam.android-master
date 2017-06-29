package com.bahisadam.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.bahisadam.MyApplication;
import com.bahisadam.interfaces.RestClient;
import org.greenrobot.eventbus.EventBus;

/**
 * @author GorkemKarayel on 10.05.2017.
 */

public abstract class BaseFragment extends Fragment{

    public abstract void api (RestClient restClient);

    @Override
    public void onCreate(Bundle instant) {
        super.onCreate(instant);
    }

    @Override
    public void onActivityCreated(Bundle instant) {
        super.onActivityCreated(instant);
        api(((MyApplication)getActivity().getApplication()).getApi());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
