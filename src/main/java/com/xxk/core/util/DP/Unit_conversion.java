package com.xxk.core.util.DP;


import com.xxk.core.util.StringUtil;

/**
 * @author Administrator
 */
public class Unit_conversion {
    /**
     * @param stock_unit
     * @return
     */
    public static double unit_Conversion(double stock_unit, int stock_proportion) {
        double unit = Math.floor(stock_unit);
        double stock_total = unit * stock_proportion;
        double remain=stock_unit%unit;
            if(remain>stock_proportion){
                remain=remain/stock_proportion;
                unit = Math.floor(remain);
            }
        return 0;
    }

}
