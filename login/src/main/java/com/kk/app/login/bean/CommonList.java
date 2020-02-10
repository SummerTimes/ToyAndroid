package com.kk.app.login.bean;

import java.util.List;

/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc:
 */
public class CommonList {

    public List<CommonBean> data;
    public int errorCode;
    public String errorMsg;

    @Override
    public String toString() {
        return "CommonList{" +
                "data=" + data +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
