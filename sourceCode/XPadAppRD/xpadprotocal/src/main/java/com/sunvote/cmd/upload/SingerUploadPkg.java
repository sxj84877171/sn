package com.sunvote.cmd.upload;

import com.sunvote.cmd.BaseCmd;
import com.sunvote.cmd.ICmd;

import java.io.ByteArrayOutputStream;

/**
 * Created by Elvis on 2017/8/19.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 * 上传单包类主要是上传简单的投票结果、状态、申请等。一般情况下，表决器如果有数据要上传，在收到基站的基础信标或取包信标后，根据防冲突算法，在一定的时序里发送数据包。基站接收到完整数据包后，给出数据确认应答，让表决器知道数据已经上传，不再多次发送。可参考《表决系统通讯协议-应用文档-基础原理》。
 * 键盘使用序列号标识模式，请看3.6节。
 * 键盘使用键盘ID模式，上传单包的数据格式是：
 * 字节	标识符	描述
 * 1	ANSCMD	0x91
 * 2-3	KEYID	表决器编号，2字节，高位在前，1开始
 * 4	ANSTYPE	数据包类型
 * 0状态
 * 1 结果是单值（签到、表决、评议）
 * 2 单选多选
 * 3 评分
 * 4 排序
 * 5 填空
 * 6 按键游戏结果
 * <p>
 * 15 多包申请
 * 16 登录信息
 * <p>
 * 21 批次单值结果
 * 22 批次单选多选结果
 * 23 批次评分值结果
 * 24 批次排序结果
 * 25 批次填空结果
 * 26 另选他人结果
 * <p>
 * 33服务申请
 * 34主席控制
 * 35发言控制
 * <p>
 * 40 对码
 * 41 防丢
 * <p>
 * 5-24	ANSDATA	根据结果类型ANSTYPE的不同，有不同的数据长度和含义
 */
public class SingerUploadPkg extends BaseCmd {

    public static final byte CMD = (byte) 0x91;

    private byte anscmd = CMD;

    private byte[] keyid = new byte[2];

    public byte getAnscmd() {
        return anscmd;
    }

    public void setAnscmd(byte anscmd) {
        this.anscmd = anscmd;
    }

    public byte[] getKeyid() {
        return keyid;
    }

    public void setKeyid(byte[] keyid) {
        this.keyid = keyid;
    }

    private byte anstype;

    private byte[] ansdata = new byte[20];

    public byte getAnstype() {
        return anstype;
    }

    public void setAnsdata(byte[] ansdata) {
        this.ansdata = ansdata;
    }

    public byte[] getAnsdata() {
        return ansdata;
    }

    public void setAnstype(byte anstype) {
        this.anstype = anstype;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(getAnscmd());
            outputStream.write(getKeyid());
            outputStream.write(getAnstype());
            outputStream.write(getAnsdata());
            return outputStream.toByteArray();
        }catch (Exception ex){
            return new byte[0];
        }
    }

    @Override
    public ICmd parseCmd(byte[] source, int start) {
        if (source != null && source.length > start + 24) {
            setAnscmd(source[start]);
            setKeyid(new byte[]{source[start + 1], source[start + 2]});
            setAnstype(source[start + 3]);
            for (int i = start + 4; i < start + 24; i++) {
                ansdata[i - 4 - start] = source[i];
            }
        }
        return this;
    }
}
