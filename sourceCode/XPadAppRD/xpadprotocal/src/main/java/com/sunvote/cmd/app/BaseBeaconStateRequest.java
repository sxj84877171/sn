package com.sunvote.cmd.app;

import com.sunvote.cmd.BaseCmd;
import com.sunvote.cmd.ICmd;

import java.io.ByteArrayOutputStream;

/**
 * Created by Elvis on 2017/8/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 * <p>
 * 基础信标是基站定时发送到一个信标信号。
 * 基础信标用于实现表决器锁定基站、是否要特殊登录、实现授权后才能表决、投票计时等功能，同时也控制了表决系统的一些特性，例如是否允许自动关机、背光模式等等。
 * 键盘接收到基础信标后，如果有数据提交，可以按防冲突算法在10个时间片内向基站提交数据，并接收0x15类的应答确认，如果提交不成功，延时在下一个周期再提交。具体参考3.1节和《表决系统通讯协议-应用文档-基础原理》。
 * <p>
 * 字节	标识符	描述
 * 1	CMD	0x10 标识
 * 2	BADDH	基站编号
 * 3-4	KEYMIN	基站所带表决器范围开始值，2字节，高位在前(由于有配对码区分，表决器暂不处理)
 * V4.71 第1字节是0xA5的时候，第2字节（KEYMAX值）是频点（用于避免键盘近距离使用锁频的次频点上） EA2000T-V0.0.3、EA1200
 * 5-6	KEYMAX	基站所带表决器范围结束值，2字节，高位在前，
 * 暂时1基站最多带400个表决器，即KEYMAX比KEYMIN最多多399(由于有配对码区分，表决器暂不处理)
 * 7-8	AUTHCODE	授权号，2字节，高位在前，0-0xFFFF
 * =0时候不使用授权模式，表决器可以参与表决
 * >0时候，表决器保存的授权号相同才能参与表决
 * 9	LOGIN	登录申请模式（后台签到模式），是否需要IC卡、登录码（用户编号、登录密码等）、用户姓名信息、学号信息，或直接授权
 * 低4位是登录模式：
 * =0 无申请要求，要等待授权指令授权
 * =1 按键签到（应答同后面的签到码，用BCD码1FFFFF提交结果）
 * =2 要输入数字签到码登录（用3.4.3节签到码格式应答）
 * =3 IC卡登录，要插入IC卡V4.5 输入学号登陆
 * =4 输入（英文）姓名登录
 * =5 自动学号登陆（投票器设置好的学号信息）
 * =6 自动姓名登陆（投票器设置好的学号信息）
 * （S50支持模式3、4、5、6）
 * =7自动硬件序列号登陆，自动提交
 * =8自动硬件序列号登陆，有提示，人工点击提交
 * =7 投票前先核对末4位学号，正确才能投票
 * =8投票前先核对末6位学号，正确才能投票
 * （JetVote上海理工大学试点，多位学号先写到键盘上，每次投票前先必须输入末4位或6位学号才能投票，用于防止1人操作多个键盘，时间来不及，每次投票都要输入，是避免签到后早退）
 * 第6位为1时候，允许表决器执行签退操作（暂不实现）
 * 签退申请也是通过签到信息包提交
 * <p>
 * 第7位=1的时候，表示是动态编号模式，表决器需要先用硬件序列号登陆，重新分配键盘编号（例如教育应用大学模式）
 * <p>
 * 10	REPORT
 * +
 * LANGUAGE	表决器报告状态模式
 * Bit0待机中报告 Bit1投票中报告 (Bit2开关机报告暂不实现)
 * 时间间隔暂时为10秒间隔
 * 但如果是投票中报告，如果开始表决后5秒内没按键，就提前报告状态
 * 高4位值，控制键盘使用的语言(V4.75)：
 * 0是用户自己选择，>1则指定语言，具体什么语言由键盘程序决定，一般1是中文，2是英文
 * 11	OFFTIME	自动关机时间
 * 0xFF不关机 0使用硬件缺省其他：按分钟为单位关机
 * 12	ATTRIB	表决器特性：背光模式+蜂鸣器模式
 * Bit0,1是背光模式 0关背光 1按键点亮，延时关 2结果提交成功后延时灭，否则一直保持亮，直到表决停止 3一直亮
 * Bit2是蜂鸣器模式 1开 0关
 * Bit3 自动提交模式 1延时自动提交 0要按提交键或OK键提交（对政务版本的表决、评议模式有效，或商务的单选多选有效）
 * Bit4 振动反馈控制 1开0关
 * Bit5 允许服务申请 1开 0关
 * Bit5 股权票数显示 1开 0 关（V4.77）
 * Bit6允许键盘发短信
 * Bit7允许键盘申请中继 1开 0关
 * Bit7 屏蔽键盘编号显示 0正常显示 1不显示编号（E10待机特殊版本，定制）
 * 13-14	NOWT	时标值，2字节，高位在前
 * 从投票启动开始的时间，用于表决器同步计时，20ms为单位，最大约21分钟，最大0xFFFF不自动变为0
 * 15	MOREINFO	附加信息类型，从此字节起，后继信息由基站自己添加组织，例如教育版本基站，后继信息是基站名称，商务基站，后继信息是基站名称，也可能是空，或多频点信息。
 * 00后面参数无意义（EA1000 V0.0.1）
 * 01 基站名称，9字节（教育系列，允许键盘按名称选择基站）
 * 02 EA4000T多信道模式（EA4000T基站是基站名称、多信道模式分时发送，EA1000仅发送了基站名称）
 * 03 EA4000T降速延距模式
 * 03 启用xPad扩展参数（V5.0），参数格式见本条备注（SDK修改基础信标的时候，如果这个字节是03，这条信息就能定时发送出去）
 * <p>
 * 16-24		根据MOREINFO决定其意义
 * <p>
 * 备注：表决器收到基础信标指令时，除更新REPORT、ATTRIB等特性外，主要判断授权码是否和自己记录的相同，如果不同而且不是0，就表示自己没授权，不能参与表决。然后判断LOGIN看是否进入注册签到。
 * <p>
 * 多信道模式参数：
 * 字节	标识符	描述
 * 15	MOREINFO	02 EA4000T多信道模式
 * 16	ATTRIB2	补充系统特性2
 * Bit7，1启用中继转发，0不使用中继转发
 * Bit6，是否允许键盘申请中继 1开 0关
 * 17	CHANS	低4位，多少通道同时收发，0为不启用，4为4通道，（2为2通道）
 * 18	Chan1	信道1的频点值，主频点，和基站频点设置值相同
 * 19	Chan2	信道2的频点值，副频点
 * 20	Chan3	信道3的频点值，副频点
 * 21	Chan4	信道4的频点值，副频点
 * 细节说明：
 * 1、基站启用多通道模式下，副频点也发射基础信标、投票信标、确认信标，和主频点一样，在副频点上相当于有一个基站；
 * 2、建议键盘锁频在主频点上，提交数据的时候，才转换到对应频点（根据键盘编号4等分选择对应频点），提交完毕，切换回主频点，（中间时间长度要兼容启用中继的时候），这种模式，可以快速跟踪基站模式的变化，例如基站转换到降速延距模式的时候；
 * 3、如果键盘中间丢频，锁频到了副频点上，也可以据此知道主频点，回到主频点上，但这种情况要排除是发射数据时候换到副频的状态；
 * 4、如果键盘采用一直锁频在副频点的模式，如果基站变换到低速延距模式，这时候除主频点外，原有副频点再也收不到信息，就会导致丢频，然后一段时间后才能锁频到主频点上
 * <p>
 * xPad扩展参数：（V5.0）
 * 字节	标识符	描述
 * 15	MOREINFO	03 xPad扩展参数信息
 * 16-17	CONFID	2字节，会议资料UID编号，1-65535，高位字节在前
 * （对应系统使用后创见的会议文件的唯一编号）
 * 18	INFOID	议题编号或索引（1-255），对应到具体用户待机时候可以浏览的议案文件内容
 * V5.1细节说明：
 * 值=0，表示不浏览内容 ，值=255，表示自由浏览所有内容
 * 其他值，指定议案编号浏览
 * 19-24		空
 */
public class BaseBeaconStateRequest extends BaseCmd {

    public static final byte CMD = 0x10 ;

    private byte cmd ;

    private byte baddh ;

    private byte[] key= new byte[4];

    private byte[] authcode = new byte[2];

    private byte login;

    private byte reportLanguage;

    private byte offtime;

    private byte attrib;

    private byte[] nowt = new byte[2];

    private byte moreinfo;

    private byte[] confid = new byte[2];

    private byte infoid;

    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }

    public byte getBaddh() {
        return baddh;
    }

    public void setBaddh(byte baddh) {
        this.baddh = baddh;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
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

    public byte getReportLanguage() {
        return reportLanguage;
    }

    public void setReportLanguage(byte reportLanguage) {
        this.reportLanguage = reportLanguage;
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

    @Override
    public byte[] toBytes() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(cmd);
            outputStream.write(baddh);
            if(key != null){
                outputStream.write(key);
            }else{
                outputStream.write(new byte[2]);
            }
            outputStream.write(authcode);
            outputStream.write(login);
            outputStream.write(reportLanguage);
            outputStream.write(offtime);
            outputStream.write(attrib);
            if(nowt == null){
                outputStream.write(new byte[2]);
            }else{
                outputStream.write(nowt);
            }
            outputStream.write(moreinfo);
            outputStream.write(confid);
            outputStream.write(infoid);
            return outputStream.toByteArray();
        }catch (Exception ex){
            return new byte[24];
        }
    }

    @Override
    public ICmd parseCmd(byte[] source, int start) {
        if(source != null && source.length > 15+start){
            setCmd(source[start]);
            setBaddh(source[start+1]);
            setKey(new byte[]{source[start+2],source[start+3]});
            setAuthcode(new byte[]{source[start+4],source[start+5]});
            setLogin(source[start+6]);
            setReportLanguage(source[start+7]);
            setOfftime(source[start+8]);
            setAttrib(source[start+9]);
            setNowt(new byte[]{source[start+10],source[start+11]});
            setMoreinfo(source[start+12]);
            setConfid(new byte[]{source[start +13],source[start+14]});
            setInfoid(source[start +15]);
        }
        return this;
    }

    public static BaseBeaconStateRequest parseRequest(byte[] bytes, int length) {
        BaseBeaconStateRequest baseBeaconStateRequest = new BaseBeaconStateRequest();
        baseBeaconStateRequest.parseCmd(bytes);
        return baseBeaconStateRequest;
    }
}
