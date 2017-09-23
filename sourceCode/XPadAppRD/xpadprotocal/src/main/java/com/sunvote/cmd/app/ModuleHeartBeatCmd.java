package com.sunvote.cmd.app;

import com.sunvote.cmd.BaseCmd;
import com.sunvote.cmd.ICmd;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * Created by Elvis on 2017/8/24.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 * <p>
 * 上传单包类主要是上传简单的投票结果、状态、申请等。一般情况下，表决器如果有数据要上传，在收到基站的基础信标或取包信标后，根据防冲突算法，在一定的时序里发送数据包。基站接收到完整数据包后，给出数据确认应答，让表决器知道数据已经上传，不再多次发送。可参考《表决系统通讯协议-应用文档-基础原理》。
 * 键盘使用序列号标识模式，请看3.6节。
 * 键盘使用键盘ID模式，上传单包的数据格式是：
 * 字节	标识符	描述
 * 1	ANSCMD	0x91
 * 2-3	KEYID	表决器编号，2字节，高位在前，1开始
 * <p>
 * <p>
 * 状态类指令用于汇报表决器的状态信息，用于系统在线监控。
 * 商务政务体系，仅567字节有效。教育体系，在测验模式，才有已答题数等信息，平常也为空。
 * 字节	标识符	描述
 * 4	ANSTYPE	=0 报告状态
 * 5	KEYIN	输入状态
 * 0 表决开始后没输入
 * 1 有输入，但未提交
 * 2 有输入，已经确认提交
 * 6	VOLT	电池电压，Bit7为1表示在充电，低7位x0.04V就是电池当前电压
 * 7	RSSI	射频接收信号强度
 * 8	QPOS	测验时候的当前答题题号，在测验模式时有效（仅教育体系有效）
 * 9	DONE	已答题数，已经输入答案的题目累计，测验时候有效，但如果投票器不支持则用0表示不使用此参数
 * 10	CHANGE	当前题目的修改次数，测验时候有效，但如果投票器不支持则用0表示不使用此参数
 * 11-12	SECONDS	当前题目的累计花费时间，单位秒，高位在前，但如果投票器不支持则用0表示不使用此参数
 * 13-24		空
 */
public class ModuleHeartBeatCmd extends BaseCmd {

    public static final byte CMD = (byte) 0x91;

    private byte anscmd = CMD;

    private byte[] keyid = new byte[2];

    private byte anstype;

    private byte keyin;

    private byte volt;

    private byte rssi;

    private byte qpos;

    private byte done;

    private byte change;

    private byte[] seconds = new byte[2];

    private byte[] datas = new byte[12];

    @Override
    public byte[] toBytes() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(getAnscmd());
            outputStream.write(getKeyid());
            outputStream.write(getAnstype());
            outputStream.write(getKeyin());
            outputStream.write(getVolt());
            outputStream.write(getRssi());
            outputStream.write(getQpos());
            outputStream.write(getDone());
            outputStream.write(getChange());
            outputStream.write(getSeconds());
            outputStream.write(getDatas());
            return outputStream.toByteArray();
        } catch (Exception ex) {
        }
        return new byte[0];
    }

    @Override
    public ICmd parseCmd(byte[] source, int start) {
        setAnscmd(source[start]);
        setKeyid(Arrays.copyOfRange(source, start + 1, start + 3));
        setAnstype(source[start + 3]);
        setKeyin(source[start + 4]);
        setVolt(source[start + 5]);
        setRssi(source[start + 6]);
        setQpos(source[start + 7]);
        setDone(source[start + 8]);
        setChange(source[start + 9]);
        setSeconds(Arrays.copyOfRange(source, start + 10, start + 12));
        setDatas(Arrays.copyOfRange(source, start + 12, start + 24));
        return this;
    }

    public byte getAnscmd() {
        return anscmd;
    }

    public void setAnscmd(byte anscmd) {
        this.anscmd = anscmd;
    }

    public byte[] getKeyid() {
        return keyid;
    }

    public void setKeyid(byte[] keyid) {
        this.keyid = keyid;
    }

    public byte getAnstype() {
        return anstype;
    }

    public void setAnstype(byte anstype) {
        this.anstype = anstype;
    }

    public byte getKeyin() {
        return keyin;
    }

    public void setKeyin(byte keyin) {
        this.keyin = keyin;
    }

    public byte getVolt() {
        return volt;
    }

    public void setVolt(byte volt) {
        this.volt = volt;
    }

    public byte getRssi() {
        return rssi;
    }

    public void setRssi(byte rssi) {
        this.rssi = rssi;
    }

    public byte getQpos() {
        return qpos;
    }

    public void setQpos(byte qpos) {
        this.qpos = qpos;
    }

    public byte getDone() {
        return done;
    }

    public void setDone(byte done) {
        this.done = done;
    }

    public byte getChange() {
        return change;
    }

    public void setChange(byte change) {
        this.change = change;
    }

    public byte[] getSeconds() {
        return seconds;
    }

    public void setSeconds(byte[] seconds) {
        if(seconds == null){
            seconds = new byte[2];
        }
        this.seconds[0] = seconds[0];
        this.seconds[1] = seconds[1];
    }

    public byte[] getDatas() {
        return datas;
    }

    public void setDatas(byte[] datas) {
        this.datas = datas;
    }
}
