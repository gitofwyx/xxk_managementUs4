package com.xxk.core.util.device_ident;

import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


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

    public static String getIdentNo(String ident, int maxNo, int amount) { //编号，最大值， 数量
        String Orderno = null;
        //maxOrderno = "NO20180315001-01"; // 从数据库查询出的最大编号
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); // 时间字符串产生方式
        String uid_pfix = "" + format.format(new Date()); // 组合流水号前一部分，NO+时间字符串，如：NO20160126
        if("".equals(ident)||ident==null){
            return null;
        }
        String ident_end = ident.substring(10, 14);
        int beginNum = maxNo; // 最大值加数量
        int tmpNum = 100 + beginNum;//生成倍数
        if (amount > 0) {
            Orderno = uid_pfix + ident_end + IdentUtil.subStr("" + tmpNum, 1) + "-" + amount;// 把10002首位的1去掉，再拼成NO201601260002字符串
        } else {
            return null;
        }
        System.out.println("Orderno=" + Orderno);
        return Orderno;
    }

    public static String getIdent(int No_O, int No_T, String Date) {
        String Orderno = null;
        //maxOrderno = "NO20180315001-01"; // 从数据库查询出的最大编号
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); // 时间字符串产生方式
        String uid_pfix = "NO" + format.format(new Date()); // 组合流水号前一部分，NO+时间字符串，如：NO20160126

        if (No_O >= 0 && No_T == 0) {
            int Num_first = 100 + No_O + 1;
            int Num_sec = 100 + 1;
            Orderno = uid_pfix + IdentUtil.subStr("" + Num_first, 1) + IdentUtil.subStr("" + Num_sec, 1);
        } else if (No_O > 0 && No_T > 0) {
            int Num_first = 100 + No_O;
            int Num_sec = 100 + No_T + 1;
            Orderno = uid_pfix + IdentUtil.subStr("" + Num_first, 1) + IdentUtil.subStr("" + Num_sec, 1);
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