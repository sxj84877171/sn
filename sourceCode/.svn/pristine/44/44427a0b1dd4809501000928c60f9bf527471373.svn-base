package com.sunvote.cmd.state;

import com.sunvote.cmd.ICmd;
import com.sunvote.cmd.push.BaseStatusChangeRequest;
import com.sunvote.cmd.push.PushBaseCmd;

/**
 * Created by Elvis on 2017/8/16.
 * Email:Eluis@psunsky.com
 * Description:
 *  取包信标概述
 字节	标识符	描述
 1	GETCMD	应答模式（组呼、竞争式）
 0x11 指定式，对指定编号表决器进行读取（未支持）
 0x12 组呼式，对指定组号的表决器进行读取（未支持）
 0x13 竞争式，有数据的表决器竞争式提交数据，使用键盘编号提交，键盘用0x91类指令应答，基站用0x15类指令确认
 0x14竞争式，使用硬件序列号提交提交数据，键盘用0x92类指令应答，基站用0x16类指令确认

 2-3	GETH
 GETL



 NowT	应答模式参数（2字节），GETH是高位
 指定式时候，GET是表决器编号高位，GETL是编号低位
 组呼式，GETH、L是组号，10个编号一组，组从1开始
 竞争式时候，暂时GETH是20，GETL是10

 时标值，2字节，高位在前
 从投票启动开始的时间，用于表决器同步计时，20ms为单位，最大约21分钟，最大0xFFFF不自动变为0
 4	DATAPOS	表决序号（结果记录序号）
 1-0xFF，值发生变化的时候，代表是新的一轮表决开始
 注意：也用作表决、评议、单选多选时候结果保存的位置，可用指令读出保存的结果
 5	MODE	投票模式
 Bit7=1表示继续表决，表决器重新提交数据，用于系统恢复，表决器可继续输入或使用原先结果；=0 正常表决
 低7位是表决模式：
 1-9是政务应用 10-19商务应用和教育 20-29多项和批次 30-39二维表评测 40-50管理类

 0、空闲，表决停止（含答案提示）
 1、签到按键签到或特殊申请签到
 2、表决 2键3键保密修改否
 3、评议多种，含等级题保密修改否
 4、评分简单、带规则（商务带计时）
 5、判断题
 9、模拟测试（通讯效果）

 10、单选多选（带计时）
 11、排序（带计时）
 12、填空
 13、抢答（带计时）和游戏按键模式
 14、测验 V4.5
 15、提交作业（或其他批次结果）

 20 连续批次（单项、多项）表决、评议、评分、选举
 21 随机批次
 22 带另选他人选举

 30 带名称式二维表评测
 31 编号式二维表评测

 40 对码模式
 41 防丢模式
 6-24	MODES	投票参数，具体和MODE有关，不同模式下参数意义不同

 */

public class GetPkgStateRequest extends StateBaseCmd {

    public final static int CMD_LENGTH = 24 ;

    public static final byte CONTEND_1 = 0x13 ;
    public static final byte CONTEND_2 = 0x14 ;

    private byte getcmd = CONTEND_1 ;

    private byte[] nowT = new byte[2];

    private byte dataPos ;

    private byte mode;

    private byte[] modes = new byte[19];

    public byte getGetcmd() {
        return getcmd;
    }

    public void setGetcmd(byte getcmd) {
        this.getcmd = getcmd;
    }

    public byte[] getNowT() {
        return nowT;
    }

    public void setNowT(byte[] nowT) {
        this.nowT = nowT;
    }

    public byte getDataPos() {
        return dataPos;
    }

    public void setDataPos(byte dataPos) {
        this.dataPos = dataPos;
    }

    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }

    public byte[] getModes() {
        return modes;
    }

    public void setModes(byte[] modes) {
        this.modes = modes;
    }

    @Override
    public byte[] toBytes() {
        byte[] result = new byte[CMD_LENGTH];
        result[0] = getcmd;
        for (int i = 0; i < 2; i++) {
            result[1 + i] = nowT[i];
        }
        result[3] = dataPos ;
        result[4] = mode ;
        for (int i = 0; i < 19 && i <modes.length ; i++) {
            result[5 + i] = modes[i];
        }
        return result;
    }

    @Override
    public ICmd parseCmd(byte[] source, int start) {
        if (source != null && source.length >= CMD_LENGTH+start) {
            getcmd = source[start + 0];
            for (int i = 0; i < 2; i++) {
                nowT[i] = source[start + 1 + i];
            }
            dataPos = source[3];
            mode = source[4];
            for (int i = 0; i < 19; i++) {
                modes[i] = source[start + 5 + i];
            }
        }
        return this;
    }

    /**
     *
     * @param bytes
     * @param length
     * @return
     */
    public static GetPkgStateRequest parseRequest(byte[] bytes, int length) {
        GetPkgStateRequest getPkgStateRequest = new GetPkgStateRequest();
        getPkgStateRequest.parseCmd(bytes);
        return getPkgStateRequest;
    }
}
