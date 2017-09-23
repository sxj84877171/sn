package com.sunvote.cmd.push;

import com.sunvote.cmd.ICmd;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * Created by Elvis on 2017/8/23.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 * ReadAndWriteVoterAllocation
 *
 * 用于读取或修改表决器的基本参数，例如表决器编号。
 发送给表决器的指令结构如下：
 字节	标识符	描述
 1	KEYCMD	0x30 表决器下载单包类指令
 2-3	KEYID	表决器编号，或0x0000广播
 4	KCMD	1 读表决器配置
 2 写表决器配置
 5-6	NEWID	写表决器配置操作时候有效
 新表决器编号，2字节，高位在前
 7	OFFTIME	写表决器配置操作时候有效
 表决器自动关机时间，以分钟为单位，0xFF不自动关机
 某些型号不支持修改
 8	LOCKBASE	固定配对模式还是自由配对模式
 目前暂应用在教育系列
 1 固定配对 2自由配对（可搜寻基站）
 9	FIXCHAN	锁频模式
 0和 1自动锁频 2固定锁频
 10-24		参数无意义

 表决器回应表决器配置信息：
 字节	标识符	描述
 1	KEYCMD	0xB0 表决器遥控指令应答
 2-3	KEYID	表决器编号，2字节，高位在前
 4	KCMD	1 表决器配置信息
 5-6	KEYID	表决器编号
 7	OFFTIME	表决器自动关机时间
 8	LOCKBASE	固定配对模式还是自由配对模式
 9-24		参数无意义
 */
public class ReadAndWriteVoterAllocation extends DownloadSingletonPkg {

    private byte[] newid = new byte[2];

    private byte offtime;

    private byte lockbase;

    private byte fixchan;

    private byte[] datas = new byte[15];

    @Override
    public byte[] toBytes() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(getKeycmd());
            outputStream.write(getKeyid());
            outputStream.write(getKcmd());
            outputStream.write(getNewid());
            outputStream.write(getOfftime());
            outputStream.write(getLockbase());
            outputStream.write(getFixchan());
            outputStream.write(getDatas());
            return outputStream.toByteArray();
        }catch (Exception ex){
            return new byte[0];
        }
    }

    @Override
    public ICmd parseCmd(byte[] source, int start) {
        if(source != null && source.length >= start + CMD_LENGTH){
            setKeycmd(source[start+0]);
            setKeyid(Arrays.copyOfRange(source,start+1,start+3));
            setKcmd(source[start+3]);
            setNewid(Arrays.copyOfRange(source,start+4,start+6));
            setOfftime(source[start+6]);
            setLockbase(source[start+7]);
            setFixchan(source[8]);
            setDatas(Arrays.copyOfRange(source,start+9,start+24));
        }
        return this;
    }

    public byte[] getNewid() {
        return newid;
    }

    public void setNewid(byte[] newid) {
        this.newid = newid;
    }

    public byte getOfftime() {
        return offtime;
    }

    public void setOfftime(byte offtime) {
        this.offtime = offtime;
    }

    public byte getLockbase() {
        return lockbase;
    }

    public void setLockbase(byte lockbase) {
        this.lockbase = lockbase;
    }

    public byte getFixchan() {
        return fixchan;
    }

    public void setFixchan(byte fixchan) {
        this.fixchan = fixchan;
    }

    public void setDatas(byte[] datas) {
        this.datas = datas;
    }

    public byte[] getDatas() {
        return datas;
    }
}
