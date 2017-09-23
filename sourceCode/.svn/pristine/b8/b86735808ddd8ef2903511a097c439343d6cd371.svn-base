package com.sunvote.sunvotesdk.basestation;

import com.sunvote.util.LogUtil;

/**
 * Created by Elvis on 2017/9/21.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class DefaultKeyEventCallBack implements IKeyEventCallBack {
    /**
     * 启动投票后，键盘提交信息后会产生的事件类型
     * 批次提交测验或作业结果格式为“测验或作业名称：题目数，题号（从1开始）：结果，题号：结果……” 例如：“test： 10， 1： abcd， 2： bc,……”
     * 每题提交的测验结果格式为：“题号：结果” 例如：“1： ab”
     * 定制签到结果格式为： “按键值，电压，信号强度， 键盘SN” 例如：“21,2.86,51,160830000015”
     *
     * @param keyId 键盘ID
     * @param info  键盘提交的信息
     * @param time  键盘提交的时间
     */
    @Override
    public void keyEventKeyResultInfo(String keyId, String info, float time) {
        LogUtil.i("DefaultKeyEventCallBack","keyEventKeyResultInfo(keyId:" + keyId + ",info:" + info + ",time:" + time+")");
    }

    /**
     * 设定需要状态报告时会产生的事件。
     * 状态报告格式为：“输入状态， 充电状态， 电池电压，信号强度, 当前题号, 已答题数,修改次数，累计时间”
     * 其中，当前题号、已答题数、修改次数、累计时间只有在在线测验模式下有效， 其他模式可以忽略。 |
     * 输入状态： 0 表决开始后没输入   1 有输入，但未提交  2 有输入，已经确认 提交
     * 充电状态：
     * 0：没有充电
     * 1：充电中
     * 电压:单位为伏特  信号强度: -dBm 当前题号:测验时候的 当前答题题号，在测 验模式时有效。已答题数，已经输入答案的题目累计，测验时候有效，但如果投票器不支持则为0。
     * 当前题目的修改次数，测验时候有效，但如果投票器不支持则为0。
     * 当前题目的累计花费时间，单位秒，高位在前，但如果投票器不支持则为0。
     *
     * @param keyId 键盘ID
     * @param info  键盘提交的信息
     * @param time  键盘提交的时间
     */
    @Override
    public void keyEventKeyResultStats(String keyId, String info, float time) {
        LogUtil.i("DefaultKeyEventCallBack","keyEventKeyResultStats(keyId:" + keyId + ",info:" + info + ",time:" + time+")");
    }

    /***
     *键盘登录信息
     *  @param keyId 键盘ID
     * @param info 键盘提交的信息
     *    键盘提交的内容

     * @param time 键盘提交的时间
     */
    @Override
    public void keyEventKeyResultLoginInfo(String keyId, String info, float time) {
        LogUtil.i("DefaultKeyEventCallBack","keyEventKeyResultLoginInfo(keyId:" + keyId + ",info:" + info + ",time:" + time+")");
    }

    /**
     * 遥控器信息     说明：遥控器信息在基站连接成功的任何时候均可以提交， 不收投票状态限制。
     *
     * @param keyId 键盘ID
     * @param info  键盘提交的信息
     *              单个按键值
     * @param time  键盘提交的时间
     */
    @Override
    public void keyEventKeyResultRemoteControlAnswer(String keyId, String info, float time) {
        LogUtil.i("DefaultKeyEventCallBack","keyEventKeyResultRemoteControlAnswer(keyId:" + keyId + ",info:" + info + ",time:" + time+")");
    }

    /**
     * 配对成功信息
     *
     * @param keyId 键盘ID
     * @param info  键盘提交的信息
     *              “键盘型号编码， 硬件版本号， 软件版本号， 硬件序列号”
     * @param time  键盘提交的时间
     */
    @Override
    public void keyEventKeyResultMatch(String keyId, String info, float time) {
        LogUtil.i("DefaultKeyEventCallBack","keyEventKeyResultMatch(keyId:" + keyId + ",info:" + info + ",time:" + time+")");
    }
}
