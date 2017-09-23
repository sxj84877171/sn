package com.sunvote.cmd.basestation;

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
 * 设置投票模式，就控制了基站的投票模式，包括投票类型和参数、投票的启动和停止。
 字节	标识符	描述
 1	BASECMD	0x60 基站类指令
 2	BASEID	指定的基站编号
 3	CMDTYPE	基站命令类型
 3 读取当前投票模式，后继的参数无意义
 4 设置当前投票模式
 4	DATAPOS	表决序号（结果记录序号）
 5	MODE	投票模式
 6-29	MODES	投票参数，具体和MODE有关，不同模式下参数意义不同

 从第4字节起，参数的含义和表决器投票模式信标中的参数含义是一模一样的，决定了表决器的投票状态，具体参数的含义和格式，参见《表决系统通讯协议-政务商务-表决器部分 V4.1》中的2.2节中的多种投票模式参数。

 基站应答：
 字节	标识符	描述
 1	BASECMD	0xE0 基站类指令应答
 2	BASEID	应答的基站的编号
 3	CMDTYPE	应答类型
 3 当前投票模式
 4	DATAPOS	当前表决序号
 5	MODE	当前投票模式
 6-29	MODES	当前投票参数，具体和MODE有关，不同模式下参数意义不同

 *
 *
 */
public class SettingVoteRequest extends BaseCmd {

    public static final byte CMD_TYPE = 0x03 ;
    /**
     * @return
     */

    public static final byte CMD = 0x60 ;

    /**
     * BASECMD
     */
    private byte basecmd = CMD;
    /**
     * 应答的基站的编号
     */
    private byte baseid ;
    /**
     * 应答类型 1 当前信标内容
     */
    private byte cmdtype = CMD_TYPE;

    /**
     *表决序号（结果记录序号）
     */
    private byte datapos;

    private byte mode;

    /**
     * 投票参数，具体和MODE有关，不同模式下参数意义不同
     */
    private byte[] modes = new byte[24];//


    @Override
    public byte[] toBytes() {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(getBasecmd());
            stream.write(getBaseid());
            stream.write(getCmdtype());
            stream.write(getDatapos());
            stream.write(getMode());
            stream.write(getModes());
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
        setCmdtype(source[start+2]);
        setDatapos(source[start+3]);
        setMode(source[start+4]);
        if(source.length > start + 5){
            setModes(Arrays.copyOfRange(source,start+5,source.length));
        }
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

    public byte getDatapos() {
        return datapos;
    }

    public void setDatapos(byte datapos) {
        this.datapos = datapos;
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
}
