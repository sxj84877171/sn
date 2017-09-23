package com.sunvote.cmd.app;

import com.sunvote.cmd.BaseCmd;
import com.sunvote.cmd.ICmd;

/**
 * Created by Elvis on 2017/8/21.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 *
 * 针对有些协议过来，没有遵照协议文档做兼容
 */
public class OtherCmd extends BaseCmd {

    private  byte[] content = new byte[0];
    @Override
    public byte[] toBytes() {
        return content;
    }

    @Override
    public ICmd parseCmd(byte[] source, int start) {
        if(source.length > start) {
            content = new byte[source.length - start];
            for (int i = 0; i < content.length; i++) {
                content[i] = source[i + start];
            }
        }
        return this;
    }

    public ICmd parseCmd(byte[] source, int start,int length) {
        if(source.length - start >= length && length > 0) {
            content = new byte[length];
            for (int i = 0; i < content.length; i++) {
                content[i] = source[i + start];
            }
        }
        return this;
    }
}
