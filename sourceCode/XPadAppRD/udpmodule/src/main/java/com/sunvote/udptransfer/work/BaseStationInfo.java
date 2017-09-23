package com.sunvote.udptransfer.work;

import java.io.Serializable;

/**
 * Created by Elvis on 2017/8/16.
 * Email:Eluis@psunsky.com
 * Description:
 */

public class BaseStationInfo implements Serializable{

    /**
     * KEYID	当前键盘编号，高位在前（便于改编号时候app获得信息）
     */
    private byte[] keyId = new byte[]{0x00,0x01};

    public void setKeyId(byte[] keyId) {
        this.keyId = keyId;
    }

    /**
     * 投票参数，具体和MODE有关，不同模式下参数意义不同
     */
    private   byte[] modes = new byte[19];

    public byte[] getModes() {
        return modes;
    }

    public void setModes(byte[] modes) {
        this.modes = modes;
    }

    private   byte dataPos ;

    public void setDataPos(byte dataPos) {
        this.dataPos = dataPos;
    }

    public byte getDataPos() {
        return dataPos;
    }

    private   byte[] key= new byte[4];

    private   byte login;

    private   byte reportLanguage;

    private   byte offtime;

    private   byte attrib;

    private   byte moreinfo;

    private   byte infoid;

    /**
     * 锁频模式
     0和 1自动锁频 2固定锁频
     */
    private   byte fixchan;

    public void setFixchan(byte fixchan) {
        this.fixchan = fixchan;
    }

    public byte getFixchan() {
        return fixchan;
    }

    /**
     * LOCKBASE	固定配对模式还是自由配对模式
     */
    private   byte lockBase;

    public byte getLockBase() {
        return lockBase;
    }

    public void setLockBase(byte lockBase) {
        this.lockBase = lockBase;
    }

    public  byte[] getKey() {
        if(key == null){
            key = new byte[4];
        }
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
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

    public byte getMoreinfo() {
        return moreinfo;
    }

    public void setMoreinfo(byte moreinfo) {
        this.moreinfo = moreinfo;
    }

    public byte getInfoid() {
        return infoid;
    }

    public void setInfoid(byte infoid) {
        this.infoid = infoid;
    }

    public void setOnline(byte online) {
        this.online = online;
    }

    /**
     * 2字节，会议资料UID编号，1-65535，高位字节在前
     * （对应系统使用后创见的会议文件的唯一编号）
     */
    private   byte[] confid = new byte[2];

    public byte[] getConfid() {
        if(confid == null){
            confid = new byte[2];
        }
        return confid;
    }

    public void setConfid(byte[] confid) {
        this.confid = confid;
    }


    /**
     * 议题编号或索引（1-255），对应到具体用户待机时候可以浏览的议案文件内容
     * 值=0，表示不浏览内容 ，值=255，表示自由浏览所有内容
     * 其他值，指定议案编号浏览
     */
    private   byte voteid;

    public byte getVoteid() {
        return voteid;
    }

    public void setVoteid(byte voteid) {
        this.voteid = voteid;
    }

    private byte volt;


    public void setVolt(byte volt) {
        this.volt = volt;
    }

    public byte getVolt() {
        return volt;
    }

    /**
     * 基站编号
     */
    private   byte baddh = 0x00 ;
    /**
     *基站名称，最多12字节
     */
    private byte[] basename = new byte[]{
            0x00, 0x00, 0x00, 0x00,
            0x02, 0x00, 0x01, 0x07,
            0x00, 0x08, 0x02, 0x03
    };

    public void setBaddh(byte baddh) {
        this.baddh = baddh;
    }

    public byte getBaddh() {
        return baddh;
    }

    public byte[] getBasename() {
        if(basename == null){
            basename = new byte[]{
                    0x00, 0x00, 0x00, 0x00,
                    0x02, 0x00, 0x01, 0x07,
                    0x00, 0x08, 0x02, 0x03
            };
        }
        return basename;
    }

    public void setBasename(byte[] basename) {
        this.basename = basename;
    }

    public   byte baseID = 0x44;

    /**
     * 基站编号
     * @return
     */
    public byte getBaseID() {
        return baseID;
    }

    public void setBaseID(byte baseID) {
        this.baseID = baseID;
    }

    private   byte online = 0x02 ;

    public byte getOnline() {
        return online;
    }

    public void setOnline() {
        this.online = 0x01;
    }

    public void setOffline(){
        online = 0x02 ;
    }

    public byte[] getKeyId() {
        return keyId;
    }

    /**
     * KEYSN	6字节键盘SN
     */
    private   byte[] keySn = new byte[]{0x01,0x02,0x03,0x04,0x05,0x06};

    public void setKeySn(byte[] keySn) {
        this.keySn = keySn;
    }

    public byte[] getKeySn() {
        if(keySn == null){
            keySn = new byte[]{0x01,0x02,0x03,0x04,0x05,0x06};
        }
        return keySn;
    }

    /**
     * 4字节键盘配对码
     */
    private   byte[] matchCode = new byte[4];

    public byte[] getMatchCode() {
        if(matchCode == null){
            matchCode = new byte[4];
        }
        return matchCode;
    }

    public void setMatchCode(byte[] matchCode) {
        this.matchCode = matchCode;
    }


    /**
     * 投票模式
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
     */
    private   byte sunvoteMode ;

    public void setSunvoteMode(byte sunvoteMode) {
        this.sunvoteMode = sunvoteMode;
    }

    public byte getSunvoteMode() {
        return sunvoteMode;
    }

    /**
     * 模块当前主从模式
     * 1 基站模式（主）
     * 2 键盘模式（从）
     */
    private   byte mode = 0x02 ;

    /**
     * MODEL	硬件型号代码，固件程序写死
     */
    private   byte model ;

    public byte getModel() {
        return model;
    }

    public void setModel(byte model) {
        this.model = model;
    }

    private   byte[] nowT = new byte[2];

    public byte[] getNowT() {
        return nowT;
    }

    public void setNowT(byte[] nowT) {
        this.nowT = nowT;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }

    public byte getMode() {
        return mode;
    }

    /**
     * IDMODE	基站的识别模式
     * 定义同信标变化通知里面的，APP可以不处理此信息
     */
    private   byte idmode;

    public void setIdmode(byte idmode) {
        this.idmode = idmode;
    }

    public byte getIdmode() {
        return idmode;
    }

    /**
     * 授权号，2字节，高位在前，0-0xFFFF
     * =0时候不使用授权模式，表决器可以参与表决
     * >0时候，表决器保存的授权号相同才能参与表决
     */
    private   byte[] authcode = new byte[2];

    public byte[] getAuthcode() {
        return authcode;
    }

    public void setAuthcode(byte[] authcode) {
        this.authcode = authcode;
    }

    /**
     * 当前频点号
     */
    private   byte chan;

    public byte getChan() {
        return chan;
    }

    public void setChan(byte chan) {
        this.chan = chan;
    }

    /**
     * 接收到基站的信号强度RSSI值，负数，越小表示信号越大
     */
    private   byte rssi = 0x78;

    public byte getRssi() {
        return rssi;
    }

    private   byte[] seconds = new byte[2];

    public byte[] getSeconds() {
        return seconds;
    }

    public void setSeconds(byte[] seconds) {
        this.seconds = seconds;
    }

    private   byte change;

    public void setChange(byte change) {
        this.change = change;
    }

    public byte getChange() {
        return change;
    }

    private   byte done;

    public void setDone(byte done) {
        this.done = done;
    }

    public byte getDone() {
        return done;
    }

    private   byte qpos;

    public void setQpos(byte qpos) {
        this.qpos = qpos;
    }

    public byte getQpos() {
        return qpos;
    }

    public void setRssi(byte rssi) {
        this.rssi = rssi;
    }

    /**
     * TX	1表示刚才1秒内有提交数据过
     * 0 表示没有
     */
    private   byte tx;

    public byte getTx() {
        return tx;
    }

    public void setTx(byte tx) {
        this.tx = tx;
    }

    /**
     * RX	1 表示刚才1秒内收到过基站的投票指令（特指投票中）
     * 0 表示没有
     */
    private   byte rx;

    public byte getRx() {
        return rx;
    }

    public void setRx(byte rx) {
        this.rx = rx;
    }

    /**
     * 硬件型号，数字
     */
    private   byte hmodel;

    public byte getHmodel() {
        return hmodel;
    }

    public void setHmodel(byte hmodel) {
        this.hmodel = hmodel;
    }

    /**
     * 固件版本，3位数字，例如 1.0.0
     */
    private   byte[] sver = new byte[]{0x01,0x00,0x00};

    public byte[] getSver() {
        return sver;
    }

    public void setSver(byte[] sver) {
        this.sver = sver;
    }


    private    boolean canWrite = false;

    public boolean isCanWrite() {
        return canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }

    private   boolean baseNofify = false;

    public boolean isBaseNofify() {
        return baseNofify;
    }

    public void setBaseNofify(boolean baseNofify) {
        this.baseNofify = baseNofify;
    }
}
