package com.sunvote.cmd.basestation.manager;

import com.sunvote.cmd.BaseCmd;
import com.sunvote.cmd.ICmd;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * Created by Elvis on 2017/8/30.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 *
 * 基站管理类指令都是对基站进行设置或操作。主要分基础管理类和双机备份类指令。
 基础管理类就是对基站进行基本的配置设置、读取硬件版本信息、进入软件升级模式等。
 双机备份类指令就是搭建双机备份系统时候要使用的指令。
 5.2 基础管理类
 5.2.1 读写基站配置

 电脑向基站发送：
 字节	标识符	描述
 1	BASECMD	0x61 基站管理类指令
 2	BASEID	指定的基站编号，为0时候不指定
 3	CMDTYPE	基站管理命令类型
 1 读取基站配置，后继的参数无意义
 2 设置基站配置
 4	BASEID	基站编号
 5	CHAN	基站频点
 6-7	KEYMIN	基站所带表决器范围开始值，2字节，高位在前
 8-9	KEYMAX	基站所带表决器范围结束值，2字节，高位在前
 10	RFPWR	基站射频功率等级
 0 按缺省值 1 满功率  2中功率  3小功率
 11-29		参数无意义

 基站应答：
 字节	标识符	描述
 1	BASECMD	0xE1 基站管理类指令应答
 2	BASEID	应答的基站的编号
 3	CMDTYPE	应答类型
 1 基站当前配置
 4	BASEID	基站编号
 5	CHAN	基站频点
 6-7	KEYMIN	基站所带表决器范围开始值，2字节，高位在前
 8-9	KEYMAX	基站所带表决器范围结束值，2字节，高位在前
 10	RFPWR	基站射频功率等级
 11-29		参数无意义

 读写基站配置信息的命令，包括请求与返回
 使用该命令，一定要指定命令数据
 *
 */
public class ReadOrWriteBaseStationConfigCmd extends BaseCmd {

    public static final byte CMD = 0x60 ;
    /**
     *  1	BASECMD	0x61 基站管理类指令
     2	BASEID	指定的基站编号，为0时候不指定
     3	CMDTYPE	基站管理命令类型

     4	BASEID	基站编号
     5	CHAN	基站频点
     6-7	KEYMIN	基站所带表决器范围开始值，2字节，高位在前
     8-9	KEYMAX	基站所带表决器范围结束值，2字节，高位在前
     10	RFPWR	基站射频功率等级
     0 按缺省值 1 满功率  2中功率  3小功率
     11-29		参数无意义
     */
    private byte basecmd = CMD ;
    /**
     * 指定的基站编号，为0时候不指定
     * 如果是设置基站编号，则为旧基站编号。后面的baseid 为薪基站编号
     * 如果读取，按照该基站编号进行去读
     */
    private byte baseid;
    /**
     *
     1 读取基站配置，后继的参数无意义
     2 设置基站配置
     */
    private byte cmdType ;
    /**
     * 薪基站编号，或者返回自己的基站编号
     */
    private  byte baseid2;
    /**
     * 基站频点
     */
    private byte chan;
    /**
     * 基站所带表决器范围开始值，2字节，高位在前
     */
    private byte[] keymin = new byte[2];
    /**
     * 基站所带表决器范围结束值，2字节，高位在前
     */
    private byte[] keymax = new byte[2];
    /**
     * 基站射频功率等级
     0 按缺省值 1 满功率  2中功率  3小功率
     */
    private byte rfpwr ;

    private byte[] data = new byte[19];

    @Override
    public byte[] toBytes() {
        try{
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(getBasecmd());
            stream.write(getBaseid());
            stream.write(getCmdType());
            stream.write(getBaseid2());
            stream.write(getChan());
            stream.write(getKeymin());
            stream.write(getKeymax());
            stream.write(getRfpwr());
            stream.write(getData());
            return stream.toByteArray();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public ICmd parseCmd(byte[] source, int start) {
        setBasecmd(source[start]);
        setBaseid(source[start+1]);
        setCmdType(source[start+2]);
        setBaseid2(source[start+3]);
        setChan(source[start+4]);
        setKeymin(Arrays.copyOfRange(source,start+5,start+7));
        setKeymax(Arrays.copyOfRange(source,start+7,start+9));
        setRfpwr(source[start+9]);
        setData(Arrays.copyOfRange(source,start+10,source.length));
        return this;
    }

    public byte getBasecmd() {
        return basecmd;
    }

    public void setBasecmd(byte basecmd) {
        this.basecmd = basecmd;
    }

    public byte getBaseid() {
        return baseid;
    }

    public void setBaseid(byte baseid) {
        this.baseid = baseid;
    }

    public byte getCmdType() {
        return cmdType;
    }

    public void setCmdType(byte cmdType) {
        this.cmdType = cmdType;
    }

    public byte getBaseid2() {
        return baseid2;
    }

    public void setBaseid2(byte baseid2) {
        this.baseid2 = baseid2;
    }

    public byte getChan() {
        return chan;
    }

    public void setChan(byte chan) {
        this.chan = chan;
    }

    public byte[] getKeymin() {
        return keymin;
    }

    public void setKeymin(byte[] keymin) {
        this.keymin = keymin;
    }

    public byte[] getKeymax() {
        return keymax;
    }

    public void setKeymax(byte[] keymax) {
        this.keymax = keymax;
    }

    public byte getRfpwr() {
        return rfpwr;
    }

    public void setRfpwr(byte rfpwr) {
        this.rfpwr = rfpwr;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
