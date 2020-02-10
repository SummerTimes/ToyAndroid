package com.kk.app.util;

import java.text.DecimalFormat;

/**
 * description:
 * author: mlp00
 * date: 2017/9/5 10:59
 */

public class DigitalUtil {
    /**
     * 保留2位小数
     *
     * @param f 输入
     * @return 输出
     */
    public static String twoDecimal(float f) {
        String rtn = "0.00";
        if (f > 0) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            rtn = decimalFormat.format(f);
        }

        return rtn;
    }
}
