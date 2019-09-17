/*
 * 版权©2015-2018年，广州都灵源链信息科技有限公司，版权所有。
 * Copyright © 2015 - 2018, GuangZhou Torino Source Info Technology Co., LTD.,
 * All rights reserved.
 */
package com.torinosrc.commons.utils;

import com.torinosrc.commons.exceptions.TorinoSrcApplicationException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <b><code>TorinoSrcCommonUtils</code></b>
 * <p/>
 * class_comment
 * <p/>
 * <b>Creation Time:</b> 2017/2/8 16:24.
 *
 * @author xiedongmei
 * @version ${Revision} 2017/2/8
 * @since wxj-be project_version
 */
public class TorinoSrcCommonUtils {

    public static String SFSTR = "yyyyMMddHHmmss";

    public static String HFSTR = "yyyyMMddHH";

    public static String DFSTR = "yyyy-MM-dd";

    public static String MFSTR = "yyyy-MM-dd HH:mm";

    public static String COMMA = ",";

    public static String URL="http://www.torinosrc.com";

    public static String dateToString(Date date, String format) {

        SimpleDateFormat hf = new SimpleDateFormat(format);

        String dateTime = hf.format(date);

        return dateTime;

    }

    public static Date stringToDate(String str, String format) throws ParseException {

        SimpleDateFormat hf = new SimpleDateFormat(format);

        Date date = hf.parse(str);

        return date;

    }

    public static String[] splitString(String str, String split) {
        String[] sourceStrArray = str.split(split);
        return sourceStrArray;
    }

    public static List<String> splitStringToList(String str, String split) {
        String[] sourceStrArray = str.split(split);
        List<String> list=new ArrayList<>();
        for(int i=0;i<sourceStrArray.length;i++){
            list.add(sourceStrArray[i]);
        }
        return list;
    }

    /**
     * 生成 1 到 9 位随机数
     * @param place 随机数位数
     * @return 生成的随机数
     */
    public static int generateRandomNumber(int place) {
        if (place > 0 && place < 10) {
            return (int) ((Math.random() * 9 + 1) * Math.pow(10, place - 1));
        } else {
            throw new TorinoSrcApplicationException("传入参数错误，不存在位数为 "+place+" 的随机 int 数");
        }
    }

    /**
     * 根据属性名获取属性值
     * */
    public static Object getFieldValueByName(String fieldName, Object o) throws Exception {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        Method method = o.getClass().getMethod(getter, new Class[] {});
        Object value = method.invoke(o, new Object[] {});
        return value;
    }

    /**
     * 获取当前网络ip
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    new TorinoSrcApplicationException("Fail to analyze header's IP",e);
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    @SuppressWarnings("rawtypes")
    public static void  analysisJson(Object objJson,SortedMap<Object,Object> parameters ){
        //如果obj为json数组
        if(objJson instanceof JSONArray){
            JSONArray objArray = (JSONArray)objJson;
            for (int i = 0; i < objArray.length(); i++) {
                analysisJson(objArray.get(i),parameters);
            }
        }
        //如果为json对象
        if(objJson instanceof JSONObject){
            JSONObject jsonObject = (JSONObject)objJson;
            Iterator it = jsonObject.keys();
            while(it.hasNext()){
                String key = it.next().toString();
                Object object = jsonObject.get(key);
                //如果得到的是数组
                if(object instanceof JSONArray){
                    JSONArray objArray = (JSONArray)object;
                    analysisJson(objArray,parameters);
                }
                //如果key中是一个json对象
                else if(object instanceof JSONObject){
                    analysisJson((JSONObject)object,parameters);
                }
                //如果key中是其他
                else{
                    System.out.println("["+key+"]:"+object.toString()+" ");
                    parameters.put(key,object.toString());
                }
            }
        }
    }


}
