package com.sunvote.txpad.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.sunvotesdk.OnConnectChanagerListener;
import com.sunvote.sunvotesdk.basestation.BaseStationInfo;
import com.sunvote.sunvotesdk.basestation.IKeyEventCallBack;
import com.sunvote.util.LogUtil;

import cn.sunars.sdk.SunARS;

public class BackgroundService extends Service {
    private Handler handler;
    public BackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public class LocalBinder extends Binder {
    }

    @Override
    public void onCreate() {
        handler = new Handler();
        handler.postDelayed(runnable,2000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(BaseStationManager.getInstance().getBaseStationInfo().isConnected()){
                BaseStationManager.getInstance().readBaseStation_MatchCode();
            }
            handler.postDelayed(this,2000);
        }
    };

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
        BaseStationManager.getInstance().readBaseConfig();
    }

    /**
     * @param baseId 基站ID
     * @param iMode  模式
     * @link{readBaseConfig()}
     */
    public void readParam(int baseId, int iMode) {
        BaseStationManager.getInstance().readParam(baseId,iMode);
    }

    /**
     * 断开基站，在软件退出或更换基站前需要断开连接
     */
    public void disconnect() {
        BaseStationManager.getInstance().disconnect();
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
        BaseStationManager.getInstance().voteStartChoice(choose);
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
        BaseStationManager.getInstance().voteStartChoice(num);
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
        BaseStationManager.getInstance().voteStartSequence(num);
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
        BaseStationManager.getInstance().voteStartSequence(setting);
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
        BaseStationManager.getInstance().voteStartTrueFalse(setting);
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
        BaseStationManager.getInstance().voteStartVote(setting);
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
        BaseStationManager.getInstance().voteStartFillBlanks(setting);
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
        BaseStationManager.getInstance().voteStartNumber(setting);
    }

    /**
     * 继续之前的投票当用户需要继续之前已经停止的投票
     * 以便用户可以继续提交结果时
     */
    public void voteContinue() {
        BaseStationManager.getInstance().voteContinue();
    }

    /**
     * 重新提交并继续投票当出现特殊情况导致最后一次投票的数据丢失时，可以用此参数启动投票，键盘会自动重新提交最后一次提交的结果，不需要用户干预。
     */
    public void voteSubmitAndContinue() {
        BaseStationManager.getInstance().voteSubmitAndContinue();
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
        BaseStationManager.getInstance().voteExamination(setting);
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
        BaseStationManager.getInstance().voteHomework(setting);
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
       BaseStationManager.getInstance().getMultiResultByID(keyId,resultType,resultId);
    }


    /**
     * 用于向所有基站发送停止投票指令
     */
    public void voteStop() {
        BaseStationManager.getInstance().voteStop();
    }

    /**
     * 注册键盘回调事件
     * @param callBack
     */
    public void registerKeyEventCallBack(IKeyEventCallBack callBack){
        BaseStationManager.getInstance().registerKeyEventCallBack(callBack);
    }

    /**
     * 取消键盘回调事件
     * @param callBack
     */
    public void unRegisterKeyEventCallBack(IKeyEventCallBack callBack){
        BaseStationManager.getInstance().unRegisterKeyEventCallBack(callBack);
    }

    /**
     * 获取基站信息
     * @return
     */
    public BaseStationInfo getBaseStationInfo() {
        return BaseStationManager.getInstance().getBaseStationInfo();
    }


    /**
     * 设置连接状态变化事件
     */
    public OnConnectChanagerListener onConnectChanagerListener;

    public void setOnConnectChanagerListener(OnConnectChanagerListener onConnectChanagerListener) {
       BaseStationManager.getInstance().setOnConnectChanagerListener(onConnectChanagerListener);
    }

    public void writeKeyPad_WorkingMode(int mode){
       BaseStationManager.getInstance().writeKeyPad_WorkingMode(mode);
    }

    public void readKeyPad_WorkingMode(){
        BaseStationManager.getInstance().readKeyPad_WorkingMode();
    }

    public void voteStartSign(String settings){
        BaseStationManager.getInstance().voteStartSign(settings);
    }

    public void voteStartBackgroundSignIn(){
        BaseStationManager.getInstance().voteStartBackgroundSignIn();
    }

    public void voteStopBackgroundSignIn(){
        BaseStationManager.getInstance().voteStopBackgroundSignIn();
    }

    public void voteStartKeyPadTest(){
        BaseStationManager.getInstance().voteStartKeyPadTest();
    }

    public void sendKeyboradSignFail(String keyID){
        BaseStationManager.getInstance().sendKeyboradSignFail(keyID);
    }

    public void writeChanel(String chanel){
        BaseStationManager.getInstance().writeChanel(chanel);
    }

    public void readChanel(){
        BaseStationManager.getInstance().readChanel();
    }

    public boolean isExamRunngin(){
        return BaseStationManager.getInstance().isExamRunngin();
    }
}
