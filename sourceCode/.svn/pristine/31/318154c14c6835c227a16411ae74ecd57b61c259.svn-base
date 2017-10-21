package com.sunvote.txpad.ui.fragment.binfo;

import com.sunvote.txpad.base.BaseFragment;
import com.sunvote.txpad.base.BasePresent;

/**
 * Created by XXW on 2017/10/20.
 */

public class BaseStationInfoFragment extends BaseFragment implements IBaseStationInfoView{

    private BaseStationInfoPresent present;
    @Override
    public BasePresent getBasePresent() {
        return present;
    }

    @Override
    public void initMVP() {
        present = new BaseStationInfoPresent();
        present.setView(this);
        present.setModel(new BaseStationInfoModel());
    }

    @Override
    public int getLayoutId() {
        return super.getLayoutId();
    }
}
