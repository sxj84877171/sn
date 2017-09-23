package com.sunvote.udptransfer.stream;

import com.sunvote.udptransfer.core.LocalUDPDataSender;
import com.sunvote.udptransfer.work.SDKProcessWork;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Elvis on 2017/8/8.
 * Email:Eluis@psunsky.com
 * Description:实现对应用层调用的输入输出流。
 * 对接应用层的输入输出流
 */

public class SunVoteOutputStream extends OutputStream {
    @Override
    public void write(int i) throws IOException {
        LocalUDPDataSender.getInstance().send(new byte[]{(byte) i}, 1);
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    @Override
    public void flush() throws IOException {
        super.flush();
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        write(buffer,0,buffer.length);
    }

    /**
     * 后续需要完成善，这里存在一个问题，如果调用者的数据是一个一个的发送，这样会出问题的。
     * 目前检查过调用者的代码，暂时是不会发生这样问题的。
     * 后续需要加强数据的校验，才能准备的分析每个包而不会出错。
     * @param buffer 写的数据大小
     * @param offset 偏移量
     * @param count 个数
     * @throws IOException
     */
    @Override
    public void write(byte[] buffer, int offset, int count) throws IOException {
        byte[] temp = new byte[count];
        System.arraycopy(buffer, offset, temp, 0, count);
        SDKProcessWork.getInstance().execute(temp,count);
    }
}
