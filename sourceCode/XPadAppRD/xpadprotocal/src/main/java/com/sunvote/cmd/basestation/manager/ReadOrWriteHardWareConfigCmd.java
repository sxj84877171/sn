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
 * <p>
 * <p>
 * 5.2.2 读写硬件信息
 * 读写配对码只对无线基站有效。
 * 电脑向基站发送：
 * 字节	标识符	描述
 * 1	BASECMD	0x61 基站管理类指令
 * 2	BASEID	指定的基站编号，为0时候不指定
 * 3	CMDTYPE	基站管理命令类型
 * 3 读取基站硬件信息，后继的参数无意义
 * 4 设置基站硬件信息（内部使用，不对外公开）
 * 4	MODEL	硬件类型代码，写操作时有效
 * 5	HVER	硬件版本号，写操作时有效
 * 6	SVER	软件版本号，写操作时有效
 * 7-12	SERIAL	硬件序列号，6字节(实际用了5字节)，高位在前，写操作时有效
 * 13	NEWVER	0xA0 表示后面3字节是新的版本号
 * 14-16	SW_3B	3字节固件版本号，例如1、0、0表示V1.0.0
 * 11、12、13表示V11.12.13
 * 17-29		参数无意义
 * <p>
 * 基站应答：
 * 字节	标识符	描述
 * 1	BASECMD	0xE1 基站管理类指令应答
 * 2	BASEID	应答的基站的编号
 * 3	CMDTYPE	应答类型
 * 3 基站当前硬件信息
 * 4	MODEL	硬件类型代码，写操作时有效
 * 5	HVER	硬件版本号，写操作时有效
 * 6	SVER	软件版本号，写操作时有效
 * 7-12	SERIAL	硬件序列号，6字节，高位在前，写操作时有效
 * 13	NEWVER	0xA0 表示后面3字节是新的版本号
 * 14-16	SW_3B	3字节固件版本号，例如1、0、0表示V1.0.0
 * 11、12、13表示V11.12.13
 * 17-29		参数无意义
 */
public class ReadOrWriteHardWareConfigCmd extends BaseCmd {

    private byte basecmd;
    private byte baseid;
    private byte cmdtype;
    private byte model;
    private byte hver;
    private byte sver;
    private byte[] serial = new byte[6];
    private byte newver;
    private byte sw_3b;
    private byte[] data = new byte[13];

    @Override
    public byte[] toBytes() {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(getBasecmd());
            stream.write(getBaseid());
            stream.write(getCmdtype());
            stream.write(getModel());
            stream.write(getHver());
            stream.write(getSver());
            stream.write(getSerial());
            stream.write(getNewver());
            stream.write(getSw_3b());
            stream.write(getData());
            return stream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public ICmd parseCmd(byte[] source, int start) {

        setBasecmd(source[start]);
        setBaseid(source[start + 1]);
        setCmdtype(source[start + 2]);
        setModel(source[start + 3]);
        setHver(source[start + 4]);
        setSver(source[start + 5]);
        setSerial(Arrays.copyOfRange(source, start + 6, start + 12));
        setNewver(source[start + 12]);
        setSw_3b(source[start + 13]);
        setData(Arrays.copyOfRange(source, start + 14, source.length));

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

    public byte getCmdtype() {
        return cmdtype;
    }

    public void setCmdtype(byte cmdtype) {
        this.cmdtype = cmdtype;
    }

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

    public byte getSw_3b() {
        return sw_3b;
    }

    public void setSw_3b(byte sw_3b) {
        this.sw_3b = sw_3b;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
