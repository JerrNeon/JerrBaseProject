package com.cw.jerrbase.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (数字操作类)
 * @create by: chenwei
 * @date 2016/10/10 18:34
 */
public class NumberUtils {

    /**
     * 数据格式化.
     *
     * @param pattern the pattern
     * @param value   the i
     * @return the string
     */
    public static String codeFormat(String pattern, Object value) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    /**
     * 格式化金额.
     *
     * @param value the value
     * @return the string
     */
    public static String formatCurrency2String(Long value) {
        if (value == null || "0".equals(String.valueOf(value))) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value / 100.00);
    }

    /**
     * 格式化金额.
     *
     * @param priceFormat the price format
     * @return the long
     */
    public static Long formatCurrency2Long(String priceFormat) {
        BigDecimal bg = new BigDecimal(priceFormat);
        Long price = bg.multiply(new BigDecimal(100)).longValue();
        return price;
    }

    /**
     * 格式化金额
     *
     * @param StrBd
     * @return
     */
    public static BigDecimal formatString2Bigdecimal(String StrBd) {
        BigDecimal bd = new BigDecimal(StrBd);
        //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    /**
     * 格式化金额.(保留两位小数)
     *
     * @param value the value
     * @return the string
     */
    public static String formatDouble2String(double value) {
        if (QMUtil.isEmpty(value)) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    /**
     * 生成固定长度的随机字符和数字
     *
     * @param len
     * @return
     */
    public static String generateRandomCharAndNumber(Integer len) {
        StringBuffer sb = new StringBuffer();
        for (Integer i = 0; i < len; i++) {
            int intRand = (int) (Math.random() * 52);
            int numValue = (int) (Math.random() * 10);
            char base = (intRand < 26) ? 'A' : 'a';
            char c = (char) (base + intRand % 26);
            if (numValue % 2 == 0) {
                sb.append(c);
            } else {
                sb.append(numValue);
            }
        }
        return sb.toString();
    }

    /**
     * @param count 位数，如果是1就产生1位的数字，如果是2就产生2位数字，依次类推
     * @return
     * @Title: getRandomNumber
     * @Description: 获取随机数
     */
    public static String getRandomNumber(int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            int rand = (int) (Math.random() * 10);
            result += rand;
        }
        return result;
    }
}
