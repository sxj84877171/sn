package com.sunvote.txpad.bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class ResponseDataBean<T> implements Serializable {

    private T data ;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /**
     * "code": "200",
     "msg": "登陆成功!",
     */
    private String code ;

    private String msg ;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
