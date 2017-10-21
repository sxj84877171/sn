package com.sunvote.txpad.ui.manager;

import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.base.BasePresent;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class BaseStationManagerActivity extends BaseActivity implements IBaseStationManagerView{

    private BaseStationManagerPresent present;

    @Override
    public int getLayoutID() {
        return R.layout.activity_base_station_manager;
    }

    @Override
    public void initMVP() {
        present = new BaseStationManagerPresent();
        present.setView(this);
        present.setModel(new BaseStationManagerModel());
        present.initBaseStation();
    }

    @Override
    public BasePresent getBasePresent() {
        return present;
    }


    @Override
    public void showConnectSuccess() {
        showToast(R.string.basestation_manager_connect_success);
    }

    @Override
    public void showConnectFail() {
        showToast(R.string.basestation_manager_connect_fail);
    }
}
