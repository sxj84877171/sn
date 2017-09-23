package com.sunvote.cmd.basestation;

import com.sunvote.cmd.BaseCmd;
import com.sunvote.cmd.ICmd;

import java.io.ByteArrayOutputStream;

/**
 * Created by Elvis on 2017/8/30.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 *
 * 基站应答：（注意：一次最多应答10个数据包）
 字节	标识符	描述
 1	BASECMD	0xE0 基站类指令应答
 2	BASEID	应答的基站的编号
 3	CMDTYPE	应答类型
 5 基站结果上传
 4	MSGNO	数据包序号
 基站最多依次输出10个数据包，第1个数据包MSGNO=1，第2个为2，依次类推，如果不足10个数据包，MSGNO=0xFF表示没有数据了
 5	MSGTYPE	基站数据包类型
 1 单值
 2 单选多选带计时
 3 评分带计时
 4 批次结果
 5 排序带计时
 6 字符串带计时
 7 单值带计时

 10状态类信息
 11 登录信息
 12 申请类信息
 13 扩展信息类

 20 硬件序列号的结果提交（统一BCD格式结果）
 6-29	MSGDATA	基站数据包实际有效的结果数据，24字节，多个表决器的结果就打包放在这24字节中
 可以1次传递8个表决器的单值结果，4个表决器的单选多选带计时结果，4个表决器的评分结果。。。

 *
 */
public class GetVoteDataResponse extends BaseCmd {
    public static final byte CMD = (byte) 0xE0;
    public static final byte CMD_TYPE = 0x05;//基站结果上传

    /**
     * 基站类指令应答
     */
    private byte basecmd = CMD;

    /**
     * 应答的基站的编号
     */
    private byte baseid ;

    /**
     * 应答类型 5 基站结果上传
     */
    private byte cmdtype = CMD_TYPE;

    /**
     * 数据包序号
     */
    private byte msgno;

    private byte msgtype;//	基站数据包类型;

    /**
     * MSGDATA	基站数据包实际有效的结果数据，24字节，多个表决器的结果就打包放在这24字节中
     可以1次传递8个表决器的单值结果，4个表决器的单选多选带计时结果，4个表决器的评分结果。。。
     */
    private byte[] msgdata = new byte[24];;

    /**
     * MSGNO
     */

    @Override
    public byte[] toBytes() {
        try{
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(getBasecmd());
            stream.write(getBaseid());
            stream.write(getCmdtype());
            stream.write(getMsgno());
            stream.write(getMsgtype());
            stream.write(getMsgdata());
            return stream.toByteArray();
        }catch (Exception ex){
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

    public byte getMsgno() {
        return msgno;
    }

    public void setMsgno(byte msgno) {
        this.msgno = msgno;
    }

    public byte getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(byte msgtype) {
        this.msgtype = msgtype;
    }

    public byte[] getMsgdata() {
        return msgdata;
    }

    public void setMsgdata(byte[] msgdata) {
        this.msgdata = msgdata;
    }
}
