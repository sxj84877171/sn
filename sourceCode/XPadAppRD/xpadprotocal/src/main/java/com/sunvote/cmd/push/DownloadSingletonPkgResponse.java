package com.sunvote.cmd.push;

import com.sunvote.cmd.ICmd;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * Created by Elvis on 2017/8/23.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 *
 * 字节	标识符	描述
 1	KEYCMD	0xB0 表决器遥控指令应答
 2-3	KEYID	表决器编号，2字节，高位在前
 4	KCMD	1 表决器配置信息
 5-6	KEYID	表决器编号
 7	OFFTIME	表决器自动关机时间
 8	LOCKBASE	固定配对模式还是自由配对模式
 9-24		参数无意义

 */
public class DownloadSingletonPkgResponse extends PushBaseCmd {

    public static final byte CMD = (byte) 0xB0;

    /**
     * KEYCMD	0xB0 表决器遥控指令应答
     */
    private byte keyCmd = CMD ;

    /**
     * KEYID	表决器编号，2字节，高位在前
     */
    private byte[] keyId = new byte[2];

    /**
     * KCMD	1 表决器配置信息
     */
    private byte kcmd ;

    /**
     * KEYID	表决器编号
     */
    private byte[] keyId2 = new byte[2];

    private byte offtime ;

    /**
     * LOCKBASE	固定配对模式还是自由配对模式
     */
    private byte lockBase;

    /**
     * 9-24		参数无意义
     */
    private byte[] datas = new byte[16];


    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(getKeyCmd());
            outputStream.write(getKeyId());
            outputStream.write(getKcmd());
            outputStream.write(getKeyId2());
            outputStream.write(getOfftime());
            outputStream.write(getLockBase());
            outputStream.write(getDatas());
            return outputStream.toByteArray();
        }catch (Exception ex){
            return new byte[0];
        }
    }

    @Override
    public ICmd parseCmd(byte[] source, int start) {
        setKeyCmd(source[start]);
        setKeyId(new byte[]{source[start+1],source[start+2]});
        setKcmd(source[start+3]);
        setKeyId2(new byte[]{source[start+4],source[start+5]});
        setOfftime(source[6]);
        setLockBase(source[start+7]);
        setDatas(Arrays.copyOfRange(source, start+8, start+24));
        return this;
    }

    public void setOfftime(byte offtime) {
        this.offtime = offtime;
    }

    public byte getOfftime() {
        return offtime;
    }

    public byte getKeyCmd() {
        return keyCmd;
    }

    public void setKeyCmd(byte keyCmd) {
        this.keyCmd = keyCmd;
    }

    public byte[] getKeyId() {
        return keyId;
    }

    public void setKeyId(byte[] keyId) {
        this.keyId = keyId;
    }

    public byte getKcmd() {
        return kcmd;
    }

    public void setKcmd(byte kcmd) {
        this.kcmd = kcmd;
    }

    public byte[] getKeyId2() {
        return keyId2;
    }

    public void setKeyId2(byte[] keyId2) {
        this.keyId2 = keyId2;
    }

    public byte getLockBase() {
        return lockBase;
    }

    public void setLockBase(byte lockBase) {
        this.lockBase = lockBase;
    }

    public byte[] getDatas() {
        return datas;
    }

    public void setDatas(byte[] datas) {
        this.datas = datas;
    }
}
