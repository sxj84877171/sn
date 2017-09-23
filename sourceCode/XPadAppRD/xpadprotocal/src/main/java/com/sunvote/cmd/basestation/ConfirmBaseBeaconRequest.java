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
 * 电脑在收到MSGNO=0xFF的命令或超出10次应答所需要的时间后，使用这个命令告诉基站哪些数据包正确收到。
 电脑确认接收到基站哪些结果：
 字节	标识符	描述
 1	BASECMD	0x60 基站类指令
 2	BASEID	指定的基站编号
 3	CMDTYPE	基站命令类型
 6 确认基站结果
 4	MSGNO1	第1个成功接收数据包的MSGNO
 。。。		第2个到第9个成功接收数据包的MSGNO，如果没有，对应字节填0xFF
 13	MSGNO10	第10个成功接收数据包的MSGNO，如果没有，对应字节填0xFF
 14-29		无意义

 基站应答：
 字节	标识符	描述
 1	BASECMD	0xE0 基站类指令应答
 2	BASEID	应答的基站的编号
 3	CMDTYPE	应答类型
 6 确认数据包应答
 4	OK	1 表示收到指令
 5-29		无实际意义

 如果电脑没收到基站应答，电脑再确认一次。

 *
 */
public class ConfirmBaseBeaconRequest extends BaseCmd {

    public static final byte CMD = 0x60 ;

    /**
     * 1	BASECMD	0x60 基站类指令
     2	BASEID	指定的基站编号
     3	CMDTYPE	基站命令类型
     6 确认基站结果
     4	MSGNO1	第1个成功接收数据包的MSGNO
     。。。		第2个到第9个成功接收数据包的MSGNO，如果没有，对应字节填0xFF
     13	MSGNO10	第10个成功接收数据包的MSGNO，如果没有，对应字节填0xFF
     14-29		无意义
     */
    private byte basecmd = CMD ;
    private byte baseid ;
    private byte cmdtype ;
    private byte[] msgno = new byte[10];
    private byte[] data = new byte[16];


    @Override
    public byte[] toBytes() {
        try{
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(getBasecmd());
            stream.write(getBaseid());
            stream.write(getCmdtype());
            stream.write(getMsgno());
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
        setCmdtype(source[start+2]);
        setMsgno(Arrays.copyOfRange(source,start+3,start+13));
        if(source.length > start + 13){
            setData(Arrays.copyOfRange(source,start+13,source.length));
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

    public byte[] getMsgno() {
        return msgno;
    }

    public void setMsgno(byte[] msgno) {
        this.msgno = msgno;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
