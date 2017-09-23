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
 * 基站内部有一个自动获取表决器结果的流程，除投票进行的时候表决器提交的结果，平常也可以接收表决器的状态报告、申请指令等。这些数据都要提交到电脑上。
 由于存在多个基站同时使用的情况，基站不主动发送数据，而是等待电脑发读取基站结果指令查询，才回送数据。
 基站回送数据的方法是这样的：最多一次回送10个数据包，每个数据包有编号，1到10，如果某个数据包编号为0xFF，就表示不足10个数据包，可以提前结束基站接收流程。
 电脑在收到编号为0xFF的数据包后，或者到达10个数据包的接收时间后，给基站发个确认指令，通知基站刚才发出的10个数据包中正确接收到了哪些。
 如果有多个基站的话，电脑查询下一个基站的结果。
 电脑读取基站结果、接收基站结果数据包和确认数据包这3个步骤，是周而复始的，保证可随时接收到表决器的数据。
 基站的结果数据包，实际是把键盘的关键数据重新打包，再一次传给电脑，一般情况下，一个数据包可传输几个表决器结果，比单个单个传递效率要高。由于结果有不同类型，因此结果数据包的类型和格式也有多种。
 3.2 读取基站结果
 电脑读取指定基站的结果：
 字节	标识符	描述
 1	BASECMD	0x60 基站类指令
 2	BASEID	指定的基站编号
 3	CMDTYPE	基站命令类型
 5 读取基站结果
 4-29		无意义


 整个结果数据包结构如下：
 字节	标识符	描述
 1	BASECMD	0xE0 基站类指令应答
 2	BASEID	应答的基站的编号
 3	CMDTYPE	5 基站结果上传
 4	MSGNO	数据包序号
 5	MSGTYPE	基站数据包类型
 1 单值
 6-7	KEYID1	第1个结果的表决器编号，高位在前
 表决器编号为0xFFFF表示结果空，不足8个结果
 8	VALUE1	第1个结果值
 9-11	KEYID2
 VALUE2	第2个结果的表决器编号和值
 。。。
 27-29	KEYID8
 VALUE8	第8个结果的表决器编号和值


 */
public class GetVoteDataRequest extends BaseCmd {

    public final static byte CMD_TYPE = 0x05;

    public static final byte CMD = 0x60;

    /**
     * 基站类指令
     */
    private byte basecmd;

    /**
     * 指定的基站编号
     */
    private byte baseid;

    /**
     * 基站命令类型
     */
    private byte cmdtype;

    private byte[] data = new byte[26];


    @Override
    public byte[] toBytes() {
        try{
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(getBasecmd());
            stream.write(getBaseid());
            stream.write(getCmdtype());
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
        if(source.length > start + 3){
            setData(Arrays.copyOfRange(source,start+3,source.length));
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
