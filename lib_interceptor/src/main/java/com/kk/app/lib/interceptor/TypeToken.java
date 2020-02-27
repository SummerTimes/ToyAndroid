package com.kk.app.lib.interceptor;

import java.lang.reflect.Type;

/**
 * @author billy.qi
 * @datetime: 2017/09/14
 * @desc:、用于gson获取泛型的类型
 */
public class TypeToken {

    Type getType() {
        return TypeUtil.getSuperclassTypeParameter(getClass(), 0);
    }
}
