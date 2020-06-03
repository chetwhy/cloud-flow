package cn.yccoding.common.util;

import java.math.BigDecimal;

/**
 * @author YC
 * @since 2020/6/3 18:23
 */
public class CurrencyUtils {

    /**
     * 元转分
     *
     * @param yuan
     * @return
     */
    public static Integer Yuan2Fen(Double yuan) {
        return new BigDecimal(yuan).movePointRight(2).intValue();
    }

    /**
     * 分转元
     *
     * @param fen
     * @return
     */
    public static Double Fen2Yuan(Integer fen) {
        return new BigDecimal(fen).movePointLeft(2).doubleValue();
    }

}
