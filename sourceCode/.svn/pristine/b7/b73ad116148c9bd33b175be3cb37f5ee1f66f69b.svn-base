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
 * 5.2.2 读写硬件信息
 用于读取硬件型号、版本、序列号等信息。
 发送给表决器的指令结构如下：
 字节	标识符	描述
 1	KEYCMD	0x30 表决器下载单包类指令
 2-3	KEYID	表决器编号，或0x0000广播
 4	KCMD	3 读表决器硬件信息
 4 写表决器硬件信息（内部使用，不对外公开）
 5	MODEL	硬件型号代码，固件程序写死
 6	HVER	硬件版本号，固件程序写死
 7	SVER	软件版本号，固件程序写死
 8-13	SERIAL	硬件序列号，6字节，高位在前，写操作时有效
 14	NEWVER	0xA0 表示后面3字节是新的版本号
 15-17	SW_3B	3字节固件版本号，例如1、0、0表示V1.0.0
 11、12、13表示V11.12.13
 18-24		参数无意义

 表决器回应表决器硬件信息：
 字节	标识符	描述
 1	KEYCMD	0xB0 表决器遥控指令应答
 2-3	KEYID	表决器编号，2字节，高位在前
 4	KCMD	3 表决器硬件信息
 5	MODEL	硬件型号代码
 6	HVER	硬件版本号
 7	SVER	软件版本号
 8-13	SERIAL	硬件序列号，6字节，高位在前
 14	NEWVER	0xA0 表示后面3字节是新的版本号
 15-17	SW_3B	3字节固件版本号
 18-24		参数无意义
 */
public class ReadAndWriteHardwareInformation extends DownloadSingletonPkg {


    private byte model ;

    private byte hver;

    private byte sver ;

    private byte[] serial = new byte[6];

    private byte newver ;

    private byte[] newverName = new byte[3];

    private byte[] datas = new byte[7];

    public byte getModel() {
        return model;
    }

    public void setModel(byte model) {
        this.model = model;
    }

    public byte getHver() {
        return hver;
    }

    public void setHver(byte hver) {
        this.hver = hver;
    }

    public byte getSver() {
        return sver;
    }

    public void setSver(byte sver) {
        this.sver = sver;
    }

    public byte[] getSerial() {
        return serial;
    }

    public void setSerial(byte[] serial) {
        this.serial = serial;
    }

    public byte getNewver() {
        return newver;
    }

    public void setNewver(byte newver) {
        this.newver = newver;
    }

    public byte[] getNewverName() {
        return newverName;
    }

    public void setNewverName(byte[] newverName) {
        this.newverName = newverName;
    }

    public byte[] getDatas() {
        return datas;
    }

    public void setDatas(byte[] datas) {
        this.datas = datas;
    }

    @Override
    public byte[] toBytes() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(getKeycmd());
            outputStream.write(getKeyid());
            outputStream.write(getKcmd());
            outputStream.write(getModel());
            outputStream.write(getHver());
            outputStream.write(getSver());
            outputStream.write(getSerial());
            outputStream.write(getNewver());
            outputStream.write(getNewverName());
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
            setKeyid(Arrays.copyOfRange(source,start+1,start+2));
            setKcmd(source[start+3]);
            /**
             * 1	KEYCMD	0x30 表决器下载单包类指令
             2-3	KEYID	表决器编号，或0x0000广播
             4	KCMD	3 读表决器硬件信息
             4 写表决器硬件信息（内部使用，不对外公开）
             5	MODEL	硬件型号代码，固件程序写死
             6	HVER	硬件版本号，固件程序写死
             7	SVER	软件版本号，固件程序写死
             8-13	SERIAL	硬件序列号，6字节，高位在前，写操作时有效
             14	NEWVER	0xA0 表示后面3字节是新的版本号
             15-17	SW_3B	3字节固件版本号，例如1、0、0表示V1.0.0
             11、12、13表示V11.12.13
             18-24		参数无意义
             */
            setModel(source[start+4]);
            setHver(source[start+5]);
            setSver(source[start+6]);
            setSerial(Arrays.copyOfRange(source,start+7,start+13));
            setNewver(source[start+13]);
            setNewverName(Arrays.copyOfRange(source,start + 14,start+17));
            setDatas(Arrays.copyOfRange(source,start+17,start+24));
        }
        return this;
    }
}
