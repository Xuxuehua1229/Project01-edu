package com.guli.common.constants;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {
    private boolean success;
    private Integer code;
    private String message;
    private Map<String,Object> data = new HashMap<>();

    //操作成功，调用这个方法，返回成功的数据
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("操作成功！");
        return r;
    }

    //操作失败，调用这个方法，返回失败的数据
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("操作失败！");
        return r;
    }

    //使用链式编程
    public R success(boolean success){
        this.setSuccess(success);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public R data(Map<String,Object> data){
        this.setData(data);
        return this;
    }
}
