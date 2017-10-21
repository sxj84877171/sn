package com.sunvote.sunvotesdk;

import com.sunvote.sunvotesdk.basestation.BaseStationInfo;
import com.sunvote.sunvotesdk.basestation.IKeyEventCallBack;
import com.sunvote.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.sunars.sdk.SunARS;

/**
 * Created by Elvis on 2017/9/7.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class BaseStationManager {

    public static final String TAG = "SunVoteSDK";
    public final static String SUCCESS = "1";
    public final static String CONNECT_FAIL = "2";
    public final static String LISTENER_KEY = "SUNARS2013";
    private static BaseStationManager instance;
    private BaseStationInfo baseStationInfo = new BaseStationInfo();
    private List<IKeyEventCallBack> keyEventCallBackList = new ArrayList<>();
    private WorkThread workThread = new WorkThread(TAG);

    public static BaseStationManager getInstance() {

        if (instance == null) {
            instance = new BaseStationManager();
        }
        return instance;
    }

    /**
     * 初始化基站一些信息
     */
    public void init() {
        // 密码认证，后面为认证密码
        WorkThread.MessageBean messageBean = new WorkThread.MessageBean();
        messageBean.executeMethod = new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                int license = 0;
                if(!baseStationInfo.islicenseSuccess()) {
                    license = SunARS.license(1, LISTENER_KEY);
                }else{
                    license = 1;
                }
                if (license > 0) {
                    LogUtil.i(TAG, "获取授权成功!");
                    baseStationInfo.setIslicenseSuccess(true);
                    if(!baseStationInfo.isConnected()) {
                        SunARS.connect(baseStationInfo.getConnectType(), baseStationInfo.getConnectInfo());
                        LogUtil.i(TAG, "发起连接基站!");
                        workThread.postDelayed(tcpConnect,2000);
                    }
                }
            }
        };

        workThread.sendMessage(messageBean);

//        try {
//            BaseStationManager.class.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void reConnect(){
        WorkThread.MessageBean messageBean = new WorkThread.MessageBean();
        messageBean.executeMethod = new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                int license  = SunARS.license(1, LISTENER_KEY);
                if (license > 0) {
                    LogUtil.i(TAG, "获取授权成功!");
                    baseStationInfo.setIslicenseSuccess(true);
                    SunARS.connect(baseStationInfo.getConnectType(), baseStationInfo.getConnectInfo());
                    LogUtil.i(TAG, "发起连接基站!");
                }
            }
        };

        workThread.sendMessage(messageBean);
    }

    private Runnable tcpConnect = new Runnable() {
        @Override
        public void run() {
            baseStationInfo.setConnectType(BaseStationInfo.CONNECT_TYPE_TCP);//tcp 连接
            baseStationInfo.setConnectInfo(BaseStationInfo.CONNECT_INFO_TCP);
            SunARS.connect(baseStationInfo.getConnectType(), baseStationInfo.getConnectInfo());
        }
    };


    private BaseStationManager() {
        SunARS.setListener(listener);
    }

    SunARS.SunARSListener listener = new SunARS.SunARSListener() {

        @Override
        public void onConnectEventCallBack(int iBaseID, int iMode, String sInfo) {
            LogUtil.i(TAG,"onConnectEventCallBack(mode=" + iMode + ",baseId=" + iBaseID + ",info=" + sInfo + ")");
            LogUtil.i(TAG, "基站连接返回数据!");
            if(SUCCESS.equals(sInfo) && iMode == 4){// 如果串口连接成功，则撤销tcp连接
                workThread.removeCallbacks(tcpConnect);
            }
            if (SUCCESS.equals(sInfo) && iMode == baseStationInfo.getConnectType()) {
                LogUtil.i(TAG, "基站连接成功,baseID:" + iBaseID);
                baseStationInfo.setBaseId(iBaseID);
                if(onConnectChanagerListener != null){
                    onConnectChanagerListener.onConnectChanger(baseStationInfo.isConnected(),true);
                }
                baseStationInfo.setConnected(true);
//                SunARS.writeHDParam(0,SunARS.BaseStation_MatchCode,"66666666");

                readBaseConfig();
                return;
            }else{
                if(onConnectChanagerListener != null){
                    onConnectChanagerListener.onConnectChanger(baseStationInfo.isConnected(),false);
                }
            }
            LogUtil.i(TAG, "基站连接失败,baseID:" + iBaseID + ",iMode:" + iMode + ",sInfo:" + sInfo);
        }

        /**
         * 基站信息返回
         * @param iBaseID 基站ID
         * @param iMode 模式
         * @param sInfo 返回信息
         */
        @Override
        public void onHDParamCallBack(int iBaseID, int iMode, String sInfo) {
            LogUtil.i(TAG,"onHDParamCallBack(mode=" + iMode + ",baseId=" + iBaseID + ",info=" + sInfo + ")");
            saveBaseStationInfo(iMode, sInfo);
        }

        /**
         * 此函数用于处理启动或停止投票事件，会在基站应答启动或停止投票指令后被调用，用户可以编写代码以便对基站应答的信息进行处理,函数名字可以自定义，但是函数的签名必需一致。
         * 参数说明：
         * @param iBaseID 基站编号
         * @param iMode 投票模式，列表同VoteStart之iMode
         * @param sInfo “1“为成功，“2” 为失败
         */
        @Override
        public void onVoteEventCallBack(int iBaseID, int iMode, String sInfo) {
            LogUtil.i(TAG,"onVoteEventCallBack(mode=" + iMode + ",baseId=" + iBaseID + ",info=" + sInfo + ")");
            if (baseStationInfo.getBaseId() == iBaseID) {
                if (SUCCESS.equals(sInfo)) {
                    baseStationInfo.setExaminationRunning(true);
                }
            }
        }

        /**
         * 键盘事件回调函数
         * 此函数用于处理键盘事件， SDK 在收到键盘事件时调用此函数，用户可以编写代码以便对键盘信息进行处理,函数名字可以自定义，但是函数的签名必需一致
         * @param KeyID 发起事件的键盘编号或键盘SN
         * @param iMode 反馈结果类型详见下表
         * @param Time 开始投票到按键的时间，单位为秒， 0表示没有计时信息
         * @param sInfo 事件详细信息的字符串，与iMode一一对应，详见下表。
         *  iMode 名称  |  sInfo说明
         */
        @Override
        public void onKeyEventCallBack(String KeyID, int iMode, float Time, String sInfo) {
            LogUtil.i(TAG,"onKeyEventCallBack(KeyID=" + KeyID+",mode=" + iMode + ",time=" + Time + ",info=" + sInfo + ")");
            switch (iMode) {
                case SunARS.KeyResult_info:
                    onKeyResultInfo(KeyID,sInfo,Time);
                    break;
                case SunARS.KeyResult_status:
                    onKeyResultStatus(KeyID,sInfo,Time);
                    break;
                case SunARS.KeyResult_loginInfo:
                    onKeyResultLoginInfo(KeyID,sInfo,Time);
                    break;
                case SunARS.KeyResult_remoteControlAnswer:
                    onKeyResultRemoteControlAnswer(KeyID,sInfo,Time);
                    break;
                case SunARS.KeyResult_match:
                    onKeyResultMatch(KeyID,sInfo,Time);
                    break;
            }
        }

        @Override
        public void onStaEventCallBack(String sInfo) {
            LogUtil.i(TAG,"onStaEventCallBack:" + sInfo);
        }

        @Override
        public void onLogEventCallBack(String sInfo) {
            LogUtil.i(TAG,"onLogEventCallBack:" + sInfo);
        }

        @Override
        public void onDataTxEventCallBack(byte[] sendData, int dataLen) {
            LogUtil.i(TAG,"onLogEventCallBack:" ,sendData,dataLen);
        }


    };

    private void saveBaseStationInfo(int iMode, String sInfo) {
        switch (iMode) {
            case SunARS.BaseStation_ID: {
                int id = 0;
                try {
                    id = Integer.parseInt(sInfo);
                    baseStationInfo.setBaseId(id);
                } catch (Exception ex) {
                    LogUtil.e(TAG, ex);
                }
                break;
            }
            case SunARS.BaseStation_Title: {
                baseStationInfo.setBaseStationName(sInfo);
                break;
            }

            case SunARS.BaseStation_SN: {
                baseStationInfo.setBasestationSerial(sInfo);
                break;
            }

            case SunARS.BaseStation_Version: {
                baseStationInfo.setBasestationVersion(sInfo);
                break;
            }

            case SunARS.BaseStation_MatchCode:{
                baseStationInfo.setBaseStationMatchCode(sInfo);
                break;
            }

            case SunARS.BaseStation_IP:{
                baseStationInfo.setBaseStationIP(sInfo);
                break;
            }

            case SunARS.BaseStation_MAC:{
                baseStationInfo.setBaseStationMAC(sInfo);
                break;
            }

            case SunARS.BaseStation_SubnetMask:{
                baseStationInfo.setBaseStationSubnetMask(sInfo);
                break;
            }

            case SunARS.BaseStation_Gateway:{
                baseStationInfo.setBaseStationGateway(sInfo);
                break;
            }

            case SunARS.WiFi_SSID:{
                baseStationInfo.setWifiSSID(sInfo);
                break;
            }

            case SunARS.WiFi_Password:{
                baseStationInfo.setWifiSSID(sInfo);
                break;
            }

            case SunARS.BaseStation_Channel:{
                baseStationInfo.setBaseStationChannel(sInfo);
                break;
            }

            case SunARS.BaseStation_RFPower:{
                baseStationInfo.setBaseStationRFPower(sInfo);
                break;
            }

            case SunARS.BaseStation_CommunicationChannels:{
                baseStationInfo.setBaseStationCommunicationChannels(sInfo);
                break;
            }

            case SunARS.KeyPad_WorkingMode:{
                baseStationInfo.setKeyPadWorkingMode(sInfo);
                break;
            }
            case SunARS.KeyPad_IdentificationMode:{
                baseStationInfo.setKeyPadIdentificationMode(sInfo);
                break;
            }

            case SunARS.KeyPad_Config:{
                baseStationInfo.setKeyPadConfig(sInfo);
                break;
            }

            case SunARS.SoftwareDongle_VerifyPWD:{
                baseStationInfo.setSoftwareDongleVerifyPWD(sInfo);
                break;
            }

            case SunARS.SoftwareDongle_A_PWD:{
                baseStationInfo.setSoftwareDongleAPWD(sInfo);
                break;
            }

            case SunARS.SoftwareDongle_A_Zone:{
                baseStationInfo.setSoftwareDongleAZone(sInfo);
                break;
            }

            case SunARS.SoftwareDongle_B_Zone:{
                baseStationInfo.setSoftwareDongleBZone(sInfo);
                break;
            }

            case SunARS.KeyPad_ID:{
                baseStationInfo.setKeyPadID(sInfo);
                break;
            }

            case SunARS.KeyPad_UserID:{
                baseStationInfo.setKeyPadUserID(sInfo);
                break;
            }

            case SunARS.KeyPad_SN:{
                baseStationInfo.setKeyPadSN(sInfo);
                break;
            }

            case SunARS.KeyPad_Version:{
                baseStationInfo.setKeyPadVersion(sInfo);
                break;
            }

            case SunARS.KeyPad_PowerOff:{

                break;
            }

            case SunARS.Background_SignIn:{
                if("1,2".equals(sInfo)){
                    baseStationInfo.setBackgroundSign(true);
                }else{
                    baseStationInfo.setBackgroundSign(false);
                }
                break;
            }
            case SunARS.Keypad_AuthorizeByID:{
                baseStationInfo.setKeypadAuthorizeByID(sInfo);
                break;
            }

            case SunARS.BaseStation_ChannelInterference:{
                baseStationInfo.setBaseStationChannelInterference(sInfo);
                break;
            }

            case SunARS.BaseStation_AutoChangeChannel:{
                baseStationInfo.setBaseStationAutoChangeChannel(sInfo);
                break;
            }

            case SunARS.BaseStation_Model:{
                baseStationInfo.setBaseStationModel(sInfo);
                break;
            }

            case SunARS.KeyPad_Model:{
                baseStationInfo.setKeyPadModel(sInfo);
                break;
            }

        }
    }

    /**
     *配对成功信息
     * @param keyID 键盘ID
     * @param info 键盘提交的信息
     *   “键盘型号编码， 硬件版本号， 软件版本号， 硬件序列号”
     * @param time 键盘提交的时间
     */
    private void onKeyResultMatch(String keyID, String info, float time) {
        for(IKeyEventCallBack callback:keyEventCallBackList){
            callback.keyEventKeyResultMatch(keyID,info,time);
        }
    }

    /**
     * 遥控器信息     说明：遥控器信息在基站连接成功的任何时候均可以提交， 不收投票状态限制。
     * @param keyID 键盘ID
     * @param info 键盘提交的信息
     *   单个按键值
     * @param time 键盘提交的时间
     */
    private void onKeyResultRemoteControlAnswer(String keyID, String info, float time) {
        for(IKeyEventCallBack callback:keyEventCallBackList){
            callback.keyEventKeyResultRemoteControlAnswer(keyID,info,time);
        }
    }

    /**
     *键盘登录信息
     * @param keyID 键盘ID
     * @param info 键盘提交的信息
     *    键盘提交的内容
     * @param time 键盘提交的时间
     */
    private void onKeyResultLoginInfo(String keyID, String info, float time) {
        for(IKeyEventCallBack callback:keyEventCallBackList){
            callback.keyEventKeyResultLoginInfo(keyID,info,time);
        }
    }

    /**
     *设定需要状态报告时会产生的事件。
     *  状态报告格式为：“输入状态， 充电状态， 电池电压，信号强度, 当前题号, 已答题数,修改次数，累计时间”
     *  其中，当前题号、已答题数、修改次数、累计时间只有在在线测验模式下有效， 其他模式可以忽略。 |
     *  输入状态： 0 表决开始后没输入   1 有输入，但未提交  2 有输入，已经确认 提交
     *  充电状态：
     *  0：没有充电
     *  1：充电中
     * 电压:单位为伏特  信号强度: -dBm 当前题号:测验时候的 当前答题题号，在测 验模式时有效。已答题数，已经输入答案的题目累计，测验时候有效，但如果投票器不支持则为0。
     * 当前题目的修改次数，测验时候有效，但如果投票器不支持则为0。
     * 当前题目的累计花费时间，单位秒，高位在前，但如果投票器不支持则为0。
     * @param keyID 键盘ID
     * @param info 键盘提交的信息
     * @param time 键盘提交的时间
     */
    private void onKeyResultStatus(String keyID, String info, float time) {
        for(IKeyEventCallBack callback:keyEventCallBackList){
            callback.keyEventKeyResultStats(keyID,info,time);
        }
    }

    /**
     * 动投票后，键盘提交信息后会产生的事件类型
     * @param keyID 键盘ID
     * @param info 键盘提交的信息
     * @param time 键盘提交的时间
     */
    private void onKeyResultInfo(String keyID, String info, float time) {
        for(IKeyEventCallBack callback:keyEventCallBackList){
            callback.keyEventKeyResultInfo(keyID,info,time);
        }
    }

    /**
     * 读取基站基本信息
     * 基站编号
     * 基站名称
     * 硬件序列号
     * 硬件版本
     * 配对码
     * 键盘识别模式
     * 基站主信道
     * 基站功率
     * 键盘设置
     */
    public void readBaseConfig() {
        readParam(baseStationInfo.getBaseId(), SunARS.BaseStation_ID);
        readParam(baseStationInfo.getBaseId(), SunARS.BaseStation_Title);
        readParam(baseStationInfo.getBaseId(), SunARS.BaseStation_SN);
        readParam(baseStationInfo.getBaseId(), SunARS.BaseStation_Version);
        readParam(baseStationInfo.getBaseId(), SunARS.Background_SignIn);
        readParam(baseStationInfo.getBaseId(), SunARS.BaseStation_MatchCode);
        readParam(baseStationInfo.getBaseId(), SunARS.KeyPad_IdentificationMode);
        readParam(baseStationInfo.getBaseId(), SunARS.BaseStation_Channel);
        readParam(baseStationInfo.getBaseId(), SunARS.BaseStation_RFPower);
        readParam(baseStationInfo.getBaseId(), SunARS.KeyPad_Config);
        readParam(baseStationInfo.getBaseId(), SunARS.SoftwareDongle_A_PWD);
        readParam(baseStationInfo.getBaseId(), SunARS.SoftwareDongle_A_Zone);
        readParam(baseStationInfo.getBaseId(), SunARS.SoftwareDongle_B_Zone);
        readParam(baseStationInfo.getBaseId(), SunARS.KeyPad_UserID);
        readParam(baseStationInfo.getBaseId(),SunARS.BaseStation_Model);
        readParam(baseStationInfo.getBaseId(),SunARS.KeyPad_WorkingMode);
    }

    public void readBaseStation_MatchCode(){
        readParam(baseStationInfo.getBaseId(), SunARS.BaseStation_MatchCode);
    }

    /**
     * @param baseId 基站ID
     * @param iMode  模式
     * @link{readBaseConfig()}
     */
    public void readParam(int baseId, int iMode) {
        LogUtil.i(TAG, "读取基站信息，baseid:" + baseId + ",iMode:" + iMode);
        if(baseStationInfo.isConnected()){
            SunARS.readHDParam(baseId, iMode);
        }
    }

    /**
     * 断开基站，在软件退出或更换基站前需要断开连接
     */
    public void disconnect() {
        if (baseStationInfo.isConnected()) {
            SunARS.voteStop();
            baseStationInfo.setMode(0);
            SunARS.disconnect(baseStationInfo.getBaseId());
            baseStationInfo.setConnected(false);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        disconnect();
    }

    /**
     * “Mode1， Mode2， Mode3， Mode4，Mode5， Mode6”
     * Mode1：选项显示方式，只对带液晶的表决器有效
     * “1” 显示字母ABCD……
     * “2” 显示数字1234……
     * Mode2：
     * “0” 提交后不能修改
     * “1” 提交后可以修改
     * Mode3：保密模式，具体和产品型号有关
     * “0” 不保密
     * “1” 显示保密
     * Mode4：迫选模式
     * “0” 可以缺选
     * “1’ 迫选，必须选出Mode6规定的项目数
     * Mode5：最大可供选择项目数，最大值为 10
     * Mode6：可选出数目，最小值是1，最大值不超出Mode5
     *
     * @param choose
     */
    public void voteStartChoice(String choose) {
        if (baseStationInfo.isConnected()) {
            baseStationInfo.setMode(SunARS.VoteType_Choice);
            SunARS.voteStart(SunARS.VoteType_Choice, choose);
        }
    }

    /**
     * “Mode1， Mode2， Mode3， Mode4，Mode5， Mode6”
     * Mode1：选项显示方式，只对带液晶的表决器有效
     * “1” 显示字母ABCD……
     * “2” 显示数字1234……
     * Mode2：
     * “0” 提交后不能修改
     * “1” 提交后可以修改
     * Mode3：保密模式，具体和产品型号有关
     * “0” 不保密
     * “1” 显示保密
     * Mode4：迫选模式
     * “0” 可以缺选
     * “1’ 迫选，必须选出Mode6规定的项目数
     * Mode5：最大可供选择项目数，最大值为 10
     * Mode6：可选出数目，最小值是1，最大值不超出Mode5
     *
     * @param num
     */
    public void voteStartChoice(int num) {
        if(baseStationInfo.isConnected()){
            voteStartChoice(BaseStationInfo.VOTETYPE_CHOICE_DEFAULT_SETTING_BEGIN + num + "," + num);
        }
    }

    /**
     * “Mode1， Mode2， Mode3， Mode4，Mode5， Mode6”
     * Mode1：选项显示方式，只对带液晶的表决器有效
     * “1” 显示字母ABCD……
     * “2” 显示数字1234……
     * Mode2：
     * “0” 提交后不能修改
     * “1” 提交后可以修改
     * Mode3：保密模式，具体和产品型号有关
     * “0” 不保密
     * “1” 显示保密
     * Mode4：迫选模式
     * “0” 可以缺选
     * “1’ 迫选，必须选出Mode6规定的项目数
     * Mode5：最大可供选择项目数，最大值为 10
     * Mode6：可选出数目，最小值是1，最大值不超出Mode5
     *
     * @param num
     */
    public void voteStartSequence(int num) {
        voteStartSequence(BaseStationInfo.VOTETYPE_CHOICE_DEFAULT_SETTING_BEGIN + num + "," + num);
    }

    /**
     * “Mode1， Mode2， Mode3， Mode4，Mode5， Mode6”
     * Mode1：选项显示方式，只对带液晶的表决器有效
     * “1” 显示字母ABCD……
     * “2” 显示数字1234……
     * Mode2：
     * “0” 提交后不能修改
     * “1” 提交后可以修改
     * Mode3：保密模式，具体和产品型号有关
     * “0” 不保密
     * “1” 显示保密
     * Mode4：迫选模式
     * “0” 可以缺选
     * “1’ 迫选，必须选出Mode6规定的项目数
     * Mode5：最大可供选择项目数，最大值为 10
     * Mode6：可选出数目，最小值是1，最大值不超出Mode5
     */
    public void voteStartSequence(String setting) {
        if (baseStationInfo.isConnected()) {
            baseStationInfo.setMode(SunARS.VoteType_Sequence);
            SunARS.voteStart(SunARS.VoteType_Sequence, setting);
        }
    }

    /**
     * “Mode1， Mode2， Mode3”
     * Mode1：键盘液晶显示模式
     * “1” 真/假
     * “2” 是/否
     * Mode2：修改模式
     * “0” 提交后不能修改
     * “1” 提交后可以修改
     * Mode3：保密模式，具体和产品型号有关
     * “0” 不保密
     * “1” 显示保密
     * 部分键盘支持，请参考键盘参数
     *
     * @param setting
     */
    public void voteStartTrueFalse(String setting) {
        if (baseStationInfo.isConnected()) {
            baseStationInfo.setMode(SunARS.VoteType_TrueFalse);
            SunARS.voteStart(SunARS.VoteType_TrueFalse, setting);
        }
    }

    /**
     * “Mode1， Mode2， Mode3， Mode4，title”
     * Mode1：表决模式
     * “1” 3 键 表 决 模 式 ，
     * “1/2/3”(S50 “√/×/？ ”)分别代表赞成/反对/弃权
     * “2” 2 键表决模式， “1/2”(S50“√/×”)分别表示赞成/反对
     * “3” 带标题快速表决，支持 7汉字标题和 10 种标准（政务系列键盘专用）
     * Mode2: 修改模式
     * “0” 提交后不能修改
     * “1” 提交后可以修改
     * Mode3: 保密模式，即提交后是否继续显示按键内容，具体和产品型号有关
     * “0” 不保密
     * “1” 显示保密
     * Mode4:表决选项
     * 在 Mode1=3 的时候，是使用哪种标准
     * “1” -赞成/反对
     * “2” -赞成/反对/弃权
     * “3” -同意/反对/弃权
     * “4” -满意/基本满意/不满意
     * “5” -满意/基本满意/不满意/不了解
     * “6” -满意/基本满意/一般/不满意
     * “7” -非常满意/满意/不了解/不满意/非常不满意
     * “8” -非常同意/同意/不确定/不同意/非常不同意
     * “9” -优秀/称职/不称职
     * “10”-优秀/称职/基本称职/不称职
     * “11” -优/良/中/差
     * Title:表决标题在 Mode1=3 的时候，是表决项目的标题， 14 字节字符，可支持7 个汉字，其他模式下，参数值没有意义
     *
     * @param setting
     */
    public void voteStartVote(String setting) {
        if (baseStationInfo.isConnected()) {
            baseStationInfo.setMode(SunARS.VoteType_Vote);
            SunARS.voteStart(SunARS.VoteType_Vote, setting);
        }
    }

    /**
     * 部分键盘支持
     * “Mode1， Mode2， Mode3，
     * Mode4”
     * Mode1: 填空类型
     * “1” 任意字母（含数字、标点
     * 符号）
     * “2” 数字选项（限定输入数字
     * 和标点符号） --暂不实现
     * Mode2: 修改模式
     * “0” 提交后不能修改
     * “1” 提交后可以修改
     * Mode3: 保密模式，即提交后是
     * 否继续显示按键内容，具体和
     * 产品型号有关
     * “0” 不保密
     * “1” 显示保密
     *
     * @param setting
     */
    public void voteStartFillBlanks(String setting) {
        if (baseStationInfo.isConnected()) {
            baseStationInfo.setMode(SunARS.VoteType_FillBlanks);
            SunARS.voteStart(SunARS.VoteType_FillBlanks, setting);
        }
    }

    /**
     * “Mode1， Mode2， Mode3”
     * Mode1：
     * “1” 不限定规则，最多输入 16
     * 位，含小数点
     * “2”“3”待扩展
     * Mode2：修改模式
     * “0” 提交后不能修改
     * “1” 提交后可以修改
     * Mode3：保密模式，具体和产品型
     * 号有关
     * “0” 不保密
     * “1” 显示保密
     *
     * @param setting
     */
    public void voteStartNumber(String setting) {
        if (baseStationInfo.isConnected()) {
            baseStationInfo.setMode(SunARS.VoteType_Number);
            SunARS.voteStart(SunARS.VoteType_Number, setting);
        }
    }

    /**
     * 继续之前的投票当用户需要继续之前已经停止的投票
     * 以便用户可以继续提交结果时
     */
    public void voteContinue() {
        if (baseStationInfo.isConnected()) {
            baseStationInfo.setMode(SunARS.VoteType_Continue);
            SunARS.voteStart(SunARS.VoteType_Continue, "");
        }
    }

    /**
     * 重新提交并继续投票当出现特殊情况导致最后一次投票的数据丢失时，可以用此参数启动投票，键盘会自动重新提交最后一次提交的结果，不需要用户干预。
     */
    public void voteSubmitAndContinue() {
        if (baseStationInfo.isConnected()) {
            baseStationInfo.setMode(SunARS.VoteType_SubmitAndContinue);
            SunARS.voteStart(SunARS.VoteType_SubmitAndContinue, "");
        }
    }

    /**
     * 开始测验或停止答题等待收卷 测验只支持选择题
     * “MODE1， REPORT， TESTNUM，INFO”
     * MODE1 测验模式
     * 0 停止答题，未提交答案的强迫提交（批次传结果的强迫一次提交，每道题传结果的当前题目也提交）
     * 注意： 停止答题之后的REPORT、 TESTNUM、 TESTNAME要保持和开始测验时一致。
     * 1 最后一批次传结果(仅S50和 S50Li支持)
     * 2 每道题传结果
     * 3 暂不支持
     * 4 带题型设置，每题传结果
     * REPORT 是否汇报答题进度
     * 1 汇报，切换题目后，用状态报告指令报告现在的题号
     * 0 不汇报
     * TESTNUM 题目数目
     * MODE1为1-3时， 1-100题；
     * MODE1为4时， 1-30题
     * INFO 附加信息
     * MODE1为1-3时， INFO为测验名称， 不超过8字符
     * MODE1为4时， INFO为“题型1：
     * 备选项个数， 题型2：备选项个数， …”
     * 题型： 0为单选， 1为多选
     */
    public void voteExamination(String setting) {
        if (baseStationInfo.isConnected()) {
            baseStationInfo.setMode(SunARS.VoteType_Examination);
            SunARS.voteStart(SunARS.VoteType_Examination, setting);
        }
    }

    /**
     * 收家庭作业     作业只支持选择题
     * “MODE1， HOMEWK”
     * MODE1 提交模式对于教育应用，是否指定作业名称接收
     * 1 不指定，学生随便可选1个作业提交
     * 2 指定名称，无对应名称作业时，学生可选择另一个提交(暂不支持)
     * 3 指定作业编号提交  HOMEWK 如果是指定编号，首字节是作业编号 1-9； 作业名称， 8字符，用于确定是收哪个作业。
     *
     * @param setting
     */
    public void voteHomework(String setting) {
        if (baseStationInfo.isConnected()) {
            baseStationInfo.setMode(SunARS.VoteType_Homework);
            SunARS.voteStart(SunARS.VoteType_Homework, setting);
        }
    }

    /**
     * 此函数用 ID 指定单个或多个键盘， 收取测验结果或家庭作业，需要键盘支持
     *
     * @param keyId      一个或多个键盘 ID，“1， 3-5” 表示 1、 3、 4、 5
     * @param resultType 1 测验 2 作业
     * @param resultId   收测验结果时为 0；收作业结果时为作业编号， 1-9
     *                   只说明函数执行是否出现异常，因为有回调函数，建议不判断
     */
    public void getMultiResultByID(String keyId, int resultType, int resultId) {
        SunARS.getMultiResultByID(keyId, resultType, resultId);
    }


    /**
     * 用于向所有基站发送停止投票指令
     */
    public void voteStop() {
        SunARS.voteStop();
        baseStationInfo.setMode(0);
    }

    /**
     * 注册键盘回调事件
     * @param callBack
     */
    public void registerKeyEventCallBack(IKeyEventCallBack callBack){
        if(!keyEventCallBackList.contains(callBack)){
            keyEventCallBackList.add(callBack);
        }
    }

    /**
     * 取消键盘回调事件
     * @param callBack
     */
    public void unRegisterKeyEventCallBack(IKeyEventCallBack callBack){
        keyEventCallBackList.remove(callBack);
    }

    /**
     * 获取基站信息
     * @return
     */
    public BaseStationInfo getBaseStationInfo() {
        return baseStationInfo;
    }


    /**
     * 设置连接状态变化事件
     */
    public OnConnectChanagerListener onConnectChanagerListener;

    public void setOnConnectChanagerListener(OnConnectChanagerListener onConnectChanagerListener) {
        this.onConnectChanagerListener = onConnectChanagerListener;
    }

    public void writeKeyPad_WorkingMode(int mode){
        SunARS.writeHDParam(baseStationInfo.getBaseId(),SunARS.KeyPad_WorkingMode,"" + mode);

    }

    public void readKeyPad_WorkingMode(){
        readParam(baseStationInfo.getBaseId(),SunARS.KeyPad_WorkingMode);
    }

    public void voteStartSign(String settings){
        if (baseStationInfo.isConnected()) {
            baseStationInfo.setMode(SunARS.VoteType_Signin);
            SunARS.voteStart(SunARS.VoteType_Signin, settings);
        }
    }

    public void voteStartBackgroundSignIn(){
        if(baseStationInfo.isConnected()){
            SunARS.writeHDParam(baseStationInfo.getBaseId(),SunARS.Background_SignIn,"1,2");
        }
    }

    public void voteStopBackgroundSignIn(){
        if(baseStationInfo.isConnected()){
            SunARS.writeHDParam(baseStationInfo.getBaseId(),SunARS.Background_SignIn,"0,2");
        }
    }

    public void voteStartKeyPadTest(){
        if(baseStationInfo.isConnected()){
            baseStationInfo.setMode(SunARS.VoteType_KeyPadTest);
            SunARS.voteStart(SunARS.VoteType_KeyPadTest, "1,0,0");
        }
    }

    public void sendKeyboradSignFail(String keyID){
        if(baseStationInfo.isConnected()){
            SunARS.writeHDParam(0,SunARS.Keypad_AuthorizeByID, keyID + ",2");
        }
    }

    public void writeChanel(String chanel){
        if(baseStationInfo.isConnected()){
            SunARS.writeHDParam(0,SunARS.BaseStation_Channel,chanel);
        }
    }

    public void readChanel(){
        if(baseStationInfo.isConnected()){
            SunARS.readHDParam(0,SunARS.BaseStation_Channel);
        }
    }

    public boolean isExamRunngin(){
        if(baseStationInfo.isConnected()){
            return baseStationInfo.getMode() == SunARS.VoteType_Examination;
        }
        return false;
    }
}
