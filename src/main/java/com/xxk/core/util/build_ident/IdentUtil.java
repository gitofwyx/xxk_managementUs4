package com.xxk.core.util.build_ident;

import com.xxk.core.util.DateUtil;
import com.xxk.core.util.NumberUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class IdentUtil {

    public void makeIdent() {

    }

    public static Map<String, Integer> getIdent(String ident) {
        Map<String, Integer> result = new HashMap<>();
        if (!"".equals(ident) && ident != null) {
            String[] dev_ident = ident.split("&");
            int dev_nameNo = Integer.parseInt(dev_ident[0]);
            int dev_typeNo = Integer.parseInt(dev_ident[1]);
            result.put("dev_nameNo", dev_nameNo);
            result.put("dev_typeNo", dev_typeNo);
        } else {
            return null;
        }
        return result;
    }

    //用于生成的编号：出库/入库编号（设备编号解释：年月日种类编号型号编号-出/入库数量）
    public static String getIdentNo(String ident, int maxNo, int amount, String Date) { //设备编号，最大值， 数量, 时间
        String Orderno = null;
        int baseNum = 100;
        //maxOrderno = "20180315001-01"; // 从数据库查询出的最大编号
        String uid_pfix = "" + DateUtil.getStrYMd(Date); // 组合流水号前一部分，NO+时间字符串，如：NO20160126
        if ("".equals(ident) || ident == null) {
            return null;
        }
        if (amount >= 100) {
            int sizeOfNo_O = NumberUtil.sizeNumOfInt(maxNo);
            baseNum = NumberUtil.getNumBySize(sizeOfNo_O) * 10;
        }
        String ident_end = ident.substring(10);
        int beginNum = maxNo; // 最大值
        int tmpNum = baseNum + beginNum;
        if (amount > 0) {
            Orderno = uid_pfix + ident_end + IdentUtil.subStr("" + tmpNum, 1) + "-" + amount;// 把10002首位的1去掉，再拼成NO201601260002字符串
        } else {
            return null;
        }
        System.out.println("Orderno=" + Orderno);
        return Orderno;
    }

    //用于生成的编号：设备编号（设备编号从左到右解释：年月日种类编号型号编号）
    public static String getIdent(int No_O, int No_T, String Date) {
        String Orderno = null;
        int baseNum = 100;
        String uid_pfix = "" + DateUtil.getStrYMd(Date);
        if (No_O >= 100 || No_T >= 100) {
            int sizeOfNo_O = NumberUtil.sizeNumOfInt(No_O);
            int sizeOfNo_T = NumberUtil.sizeNumOfInt(No_T);
            if (sizeOfNo_O > sizeOfNo_T) {
                baseNum = NumberUtil.getNumBySize(sizeOfNo_O) * 10;
            } else {
                baseNum = NumberUtil.getNumBySize(sizeOfNo_T) * 10;
            }
        }
        if (No_O >= 0 && No_T == 0) {
            int Num_first = baseNum + No_O + 1;
            int Num_sec = baseNum + 1;
            Orderno = uid_pfix + IdentUtil.subStr("" + Num_first, 1) + IdentUtil.subStr("" + Num_sec, 1);
        } else if (No_O > 0 && No_T > 0) {
            int Num_first = baseNum + No_O;
            int Num_sec = baseNum + No_T + 1;
            Orderno = uid_pfix + IdentUtil.subStr("" + Num_first, 1) + IdentUtil.subStr("" + Num_sec, 1);
        } else {
            return null;
        }
        ; // 结果10002
        return Orderno;
    }

    //用于生成的编号(按月统计排序)
    public static String buildIdent(String No_O, int No_T, String Date) {
        String Orderno = null;
        int baseNum = 100;
        String uid_pfix = "" + DateUtil.getStrYMd(Date);
        if (No_T >= 100) {
            int sizeOfNo_T = NumberUtil.sizeNumOfInt(No_T);
            baseNum = NumberUtil.getNumBySize(sizeOfNo_T) * 10;
        }
        if (No_T >= 0) {
            int Num_first = baseNum + No_T + 1;
            Orderno = uid_pfix +No_O+ IdentUtil.subStr("" + Num_first, 1);
        } else {
            return null;
        }
        ; // 结果10002
        return Orderno;
    }

    public static String subStr(String str, int start) {
        if (str == null || str.equals("") || str.length() == 0)
            return "";
        if (start < str.length()) {
            return str.substring(start);
        } else {
            return "";
        }

    }
}