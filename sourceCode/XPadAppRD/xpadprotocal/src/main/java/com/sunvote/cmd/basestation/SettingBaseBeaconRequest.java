package com.sunvote.cmd.basestation;

import com.sunvote.cmd.BaseCmd;
import com.sunvote.cmd.ICmd;

import java.io.ByteArrayOutputStream;

/**
 * Created by Elvis on 2017/8/30.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 * 电脑发给基站：
 * 字节	标识符	描述
 * 1	BASECMD	0x60 基站类指令
 * 2	BASEID	指定的基站编号
 * 3	CMDTYPE	基站命令类型
 * 1 读取信标，后继的参数无意义
 * 2 设置信标
 * 4,5	AUTHCODE	授权号，2字节，高位在前，0-0xFFFF
 * =0时候不使用授权模式，表决器可以参与表决
 * >0时候，表决器保存的授权号相同才能参与表决
 * 6	LOGIN	登录申请模式(后台签到模式)
 * 具体含义同表决器协议
 * 7	REPORT	表决器报告状态模式
 * 具体含义同表决器协议
 * 8	OFFTIME	自动关机时间
 * 0xFF不关机 0使用硬件缺省  其他：按分钟为单位关机
 * 9	ATTRIB	表决器特性
 * 具体含义同表决器协议
 * 10-11	NOWT	时标值（用于计时）
 * 12	MOREINFO	从此字节起，设置参数无效，基站发出的信标内容NOWT后由基站自己组织，不由基础信标设置指令设置。
 * 00 后面参数无意义（老版本）
 * 03 设置的议案信息（V5.0）
 * 13-14	CONFID	2字节，会议资料UID编号，1-65535，高位字节在前
 * （对应系统使用后创见的会议文件的唯一编号）
 * 15	INFOID	议题编号或索引（1-255），对应到具体用户可以浏览的议案文件内容
 * 16-29		空，不可设置
 */
public class SettingBaseBeaconRequest extends BaseCmd {

    /**
     * @return
     */

    public static final byte CMD = 0x60;

    public static final byte CMD_TYPE_READ = 0x01;
    public static final byte CMD_TYPE_WRITE = 0x02;

    /**
     * BASECMD	0xE0 基站类指令应答
     */
    private byte basecmd = CMD;
    /**
     * 应答的基站的编号
     */
    private byte baseid;
    /**
     * 应答类型 1 当前信标内容
     */
    private byte cmdtype;
    /**
     * AUTHCODE	授权号
     */
    private byte[] authcode = new byte[2];
    private byte login;
    private byte report;
    private byte offtime;
    private byte attrib;
    private byte[] nowt = new byte[2];
    private byte moreinfo;
    private byte[] confid = new byte[2];
    private byte infoid;
    private byte[] data = new byte[14];

    @Override
    public byte[] toBytes() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(getBasecmd());
            outputStream.write(getBaseid());
            outputStream.write(getCmdtype());
            outputStream.write(getAuthcode());
            outputStream.write(getLogin());
            outputStream.write(getReport());
            outputStream.write(getOfftime());
            outputStream.write(getAttrib());
            outputStream.write(getNowt());
            outputStream.write(getMoreinfo());
            outputStream.write(getConfid());
            outputStream.write(getInfoid());
            outputStream.write(getData());
            return outputStream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public ICmd parseCmd(byte[] source, int start) {
        return null;
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

    public byte getCmdtype() {
        return cmdtype;
    }

    public void setCmdtype(byte cmdtype) {
        this.cmdtype = cmdtype;
    }

    public byte[] getAuthcode() {
        return authcode;
    }

    public void setAuthcode(byte[] authcode) {
        this.authcode = authcode;
    }

    public byte getLogin() {
        return login;
    }

    public void setLogin(byte login) {
        this.login = login;
    }

    public byte getReport() {
        return report;
    }

    public void setReport(byte report) {
        this.report = report;
    }

    public byte getOfftime() {
        return offtime;
    }

    public void setOfftime(byte offtime) {
        this.offtime = offtime;
    }

    public byte getAttrib() {
        return attrib;
    }

    public void setAttrib(byte attrib) {
        this.attrib = attrib;
    }

    public byte[] getNowt() {
        return nowt;
    }

    public void setNowt(byte[] nowt) {
        this.nowt = nowt;
    }

    public byte getMoreinfo() {
        return moreinfo;
    }

    public void setMoreinfo(byte moreinfo) {
        this.moreinfo = moreinfo;
    }

    public byte[] getConfid() {
        return confid;
    }

    public void setConfid(byte[] confid) {
        this.confid = confid;
    }

    public byte getInfoid() {
        return infoid;
    }

    public void setInfoid(byte infoid) {
        this.infoid = infoid;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
