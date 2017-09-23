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
 * 控制基站进入程序升级模式，一般是进入BootLoader状态。
 * 电脑向基站发送：
 * 字节	标识符	描述
 * 1	BASECMD	0x61 基站管理类指令
 * 2	BASEID	指定的基站编号，为0时候不指定
 * 3	CMDTYPE	基站管理命令类型
 * 7 基站升级
 * 4-29		参数无意义
 */
public class BaseStationUpgradeCmd extends BaseCmd {

    private byte basecmd;
    private byte baseid;
    private byte cmdtype;
    private byte[] data = new byte[26];

    @Override
    public byte[] toBytes() {
        try {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(getBasecmd());
            stream.write(getBaseid());
            stream.write(getCmdtype());
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
        setData(Arrays.copyOfRange(source, start+3, source.length));

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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
