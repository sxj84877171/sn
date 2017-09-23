package com.sunvote.protocal;

import com.sunvote.cmd.BaseCmd;
import com.sunvote.cmd.ICmd;
import com.sunvote.utils.ConvertUtils;
import com.sunvote.utils.Crc16;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Elvis on 2017/8/8.
 * Email:Eluis@psunsky.com
 * Description:
 * 1.1数据包的格式
 * F5   AA   AA   LEN   有效数据 CRC1  CRC2
 */

public class Protocol<C extends ICmd> implements IProtocal {

    protected final static byte[] HEADER = new byte[]{(byte) 0xF5, (byte) 0xAA, (byte) 0xAA};

    /**
     * F5   AA   AA
     */
    protected byte[] header = HEADER;

    /**
     * 消息体长度
     */
    protected byte length;

    /**
     * 消息体内容
     * !! 不能超过126个字节
     */
    protected byte[] messageBody;

    /**
     * crc校验码
     */
    protected byte[] crcs = new byte[]{0x00, 0x00};

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public void setCrcs(byte[] crcs) {
        this.crcs = crcs;
    }

    public void setCmd(C cmd) {
        this.cmd = cmd;
    }

    public C getCmd() {
        if (cmd == null) {
            if (messageBody != null) {
                cmd = (C) BaseCmd.parse(messageBody, messageBody.length);
            }
        }
        return cmd;
    }

    private boolean enableMatchCode = false;

    public void setEnableMatchCode(boolean enableMatchCode) {
        this.enableMatchCode = enableMatchCode;
    }

    private byte[] matchCode = new byte[]{0x00, 0x00, 0x00, 0x00};

    private byte[] getMessageBody() {
        messageBody = cmd.toBytes();
        return messageBody;
    }

    private byte getLength() {
        length = 0;
        length += getMessageBody().length;
        length += 2;
//        if (enableMatchCode) {
//            length += 4;
//        }
        return length;
    }

    private C cmd;

    /**
     * 由协议转化成字节码，可以直接用来发送
     *
     * @return
     */
    public byte[] toBytes() {
        length = getLength();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            // 头信息
            // 包头
            byteArrayOutputStream.write(header);
            //长度
            byteArrayOutputStream.write(length);

            if (enableMatchCode) {
                byteArrayOutputStream.write(matchCode);
            }
            //内容
            byteArrayOutputStream.write(getMessageBody());

            byte[] res = byteArrayOutputStream.toByteArray();
            //CRC校验码
            roundCrc(res);
            byteArrayOutputStream.write(crcs);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 自动补充协议里面的CRC值
     *
     * @param data
     */
    public void roundCrc(byte[] data) {
        int crcValue = Crc16.getUnsignedShort(Crc16.crc16(data, data.length - 4));
        crcs[0] = (byte) (crcValue >> 8);
        crcs[1] = (byte) (crcValue);
    }

    /**
     * 检查外来数据包的头是否正确
     *
     * @param datas
     * @return
     */
    public static boolean checkHeader(byte[] datas) {
        if (datas != null && datas.length >= 3) {
            return datas[0] == HEADER[0] && datas[1] == HEADER[1] && datas[2] == HEADER[2];
        }
        return false;
    }

    /**
     * @param datas 数据
     * @return
     */
    public static boolean checkCRC(byte[] datas) {
        if (datas.length > 6) {
            return Crc16.crc16Check(datas);
        }
        return false;
    }

    /**
     * 解析协议里面的数据长度
     *
     * @param datas
     * @return
     */
    public static int parseLength(byte[] datas) {
        if (datas != null && datas.length >= 4) {
            return datas[3] & 0xFF;
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("header:").append(ConvertUtils.bytes2HexString(header)).append("\n");
//        stringBuilder.append("length:").append(ConvertUtils.bytes2HexString(new byte[]{getLength()})).append("\n");
        stringBuilder.append("message:").append(ConvertUtils.bytes2HexString(getMessageBody())).append("\n");
        return stringBuilder.toString();
    }

}
