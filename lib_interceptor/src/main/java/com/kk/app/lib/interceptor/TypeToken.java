package com.kk.app.lib.interceptor;

import java.lang.reflect.Type;

/**
 * 用于gson获取泛型的类型
 * @author billy.qi
 * @since 17/9/14 12:50
 */
public class TypeToken {

    Type getType() {
        return TypeUtil.getSuperclassTypeParameter(getClass(), 0);
    }
}
