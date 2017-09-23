package com.sunvote.cmd.state;

import com.sunvote.cmd.ICmd;

import java.io.ByteArrayOutputStream;

/**
 * Created by Elvis on 2017/8/21.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class GetPkgStateResponse extends StateBaseCmd {

    public static final byte CMD = 0x15;

    private byte cmd;

    private byte[] key1 = new byte[2];

    private byte[] key2 = new byte[2];

    private byte[] key3 = new byte[2];

    private byte[] key4 = new byte[2];

    private byte[] key5 = new byte[2];

    private byte[] key6 = new byte[2];

    private byte[] key7 = new byte[2];

    private byte[] key8 = new byte[2];

    private byte[] key9 = new byte[2];

    private byte[] key10 = new byte[2];

    private byte[] keyOther = new byte[3];


    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }

    public byte[] getKey1() {
        return key1;
    }

    public void setKey1(byte[] key1) {
        this.key1 = key1;
    }

    public byte[] getKey2() {
        return key2;
    }

    public void setKey2(byte[] key2) {
        this.key2 = key2;
    }

    public byte[] getKey3() {
        return key3;
    }

    public void setKey3(byte[] key3) {
        this.key3 = key3;
    }

    public byte[] getKey4() {
        return key4;
    }

    public void setKey4(byte[] key4) {
        this.key4 = key4;
    }

    public byte[] getKey5() {
        return key5;
    }

    public void setKey5(byte[] key5) {
        this.key5 = key5;
    }

    public byte[] getKeyOther() {
        return keyOther;
    }

    public void setKeyOther(byte[] keyOther) {
        this.keyOther = keyOther;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        try {
            byteOutputStream.write(cmd);
            byteOutputStream.write(key1);
            byteOutputStream.write(key2);
            byteOutputStream.write(key3);
            byteOutputStream.write(key4);
            byteOutputStream.write(key5);
            byteOutputStream.write(key6);
            byteOutputStream.write(key7);
            byteOutputStream.write(key8);
            byteOutputStream.write(key9);
            byteOutputStream.write(key10);
            byteOutputStream.write(keyOther);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return byteOutputStream.toByteArray();
        }
    }

    @Override
    public ICmd parseCmd(byte[] source, int start) {
        cmd = source[start];
        key1[0] = source[start + 1];
        key1[1] = source[start + 2];
        key2[0] = source[start + 3];
        key2[1] = source[start + 4];
        key3[0] = source[start + 5];
        key3[1] = source[start + 6];
        key4[0] = source[start + 7];
        key4[1] = source[start + 8];
        key5[0] = source[start + 9];
        key5[1] = source[start + 10];
        key6[0] = source[start + 11];
        key6[1] = source[start + 12];
        key7[0] = source[start + 13];
        key7[1] = source[start + 14];
        key8[0] = source[start + 15];
        key8[1] = source[start + 16];
        key9[0] = source[start + 17];
        key9[1] = source[start + 18];
        key10[0] = source[start + 19];
        key10[1] = source[start + 20];
        keyOther[0] = source[start + 21];
        keyOther[1] = source[start + 22];
        keyOther[2] = source[start + 23];
        return this;
    }

    public boolean containsKeyId(byte[] keyId){
        if(compareBytes(keyId,key1)){
            return true;
        }
        if(compareBytes(keyId,key2)){
            return true;
        }
        if(compareBytes(keyId,key3)){
            return true;
        }
        if(compareBytes(keyId,key4)){
            return true;
        }
        if(compareBytes(keyId,key5)){
            return true;
        }
        if(compareBytes(keyId,key6)){
            return true;
        }
        if(compareBytes(keyId,key7)){
            return true;
        }
        if(compareBytes(keyId,key8)){
            return true;
        }
        if(compareBytes(keyId,key9)){
            return true;
        }
        if(compareBytes(keyId,key10)){
            return true;
        }
        return false;
    }

    private static boolean compareBytes(byte[] byte1, byte[] byte2) {
        if (byte1 != null && byte2 != null) {
            if (byte1.length != byte2.length) {
                return false;
            }

            for (int i = 0; i < byte1.length; i++) {
                if (byte1[i] != byte2[i]) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }

    public static GetPkgStateResponse parseRequest(byte[] bytes, int length) {
        GetPkgStateResponse response = new GetPkgStateResponse();
        response.parseCmd(bytes);
        return response;
    }
}
