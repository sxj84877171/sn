package com.sunvote.udptransfer.work;

import com.sunvote.protocal.Protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elvis on 2017/8/17.
 * Email:Eluis@psunsky.com
 * Description:
 */

public class RepeatMessageManager {

    public static final int SDK = 0x01;
    public static final int BASE_STATION = 0x02 ;

    class RepeatBean{
        int type ;
        Protocol protocal;
    }


    private RepeatMessageManager(){}

    private static RepeatMessageManager instance = new RepeatMessageManager();

    public static RepeatMessageManager getInstance(){return instance;}

    private Map<String,RepeatBean> protocalMap = new HashMap<>();

    /**
     * 保存消息包，后续根据是否有消息包处理
     * @param key
     * @param protocal
     * @return
     */
    public RepeatBean put(String key , Protocol protocal,int type){
        RepeatBean repeatBean = new RepeatBean();
        repeatBean.protocal = protocal;
        repeatBean.type = type;
        synchronized (protocalMap) {
             protocalMap.put(key,repeatBean);
        }
        return repeatBean;
    }

    public RepeatBean remove(String key){
        RepeatBean repeatBean = null ;
        synchronized (protocalMap){
            repeatBean = protocalMap.remove(key);
        }
        return repeatBean;
    }

    public boolean hasKey(String key){
        return protocalMap.containsKey(key);
    }

    public Protocol getProtocal(String key){
        synchronized (protocalMap) {
            RepeatBean repeatBean = protocalMap.get(key);
            if (repeatBean != null) {
                return repeatBean.protocal;
            }
        }
        return null;
    }

    public void start(){
        BaseStationProcessWork.getInstance().postDelayed(repeatTask);
    }

    public void stop(){
        BaseStationProcessWork.getInstance().removeCallbacks(repeatTask);
    }

    private Runnable repeatTask = new Runnable() {
        @Override
        public void run() {
            //清除上次提交的任務操作
            BaseStationProcessWork.getInstance().removeCallbacks(this);
            synchronized (protocalMap) {
                Object[] keys = protocalMap.keySet().toArray();
                for(Object key : keys){
                    RepeatBean repeatBean = protocalMap.get(key.toString());
                    if(repeatBean.type == SDK){
                        SDKProcessWork.getInstance().postData(repeatBean.protocal.toBytes());
                    }else{
                        BaseStationProcessWork.getInstance().postData(repeatBean.protocal.toBytes());
                    }
                }
            }
            BaseStationProcessWork.getInstance().postDelayed(this);
        }
    };
}
