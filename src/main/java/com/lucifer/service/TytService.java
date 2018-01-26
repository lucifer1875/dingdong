package com.lucifer.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lucifer.util.SocketUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by lucifer on 2018/1/9.
 */
@Service
public class TytService {

    private static Logger logger4J = Logger.getLogger(TytService.class.getSimpleName());

    private static String serverUrl = "39.106.166.24";
    private static int part = 8001;

    /**
     * 处理叮咚音箱的请求数据并且响应
     * @param info
     * @return
     */
    public String tytProcess(String info){
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(info);

        String status  = jsonObject.get("status").getAsString();
        if (!"END".equals(status)){
            reqProcess(jsonObject);
        }

        return resProcess(info);
    }

    /**
     * 分析请求数据并且处理
     * @param jsonObject
     */
    public void reqProcess(JsonObject jsonObject){

        //获取设备
        String device = convert2OBJ(convert2OBJ(jsonObject.get("session")).get("attributes")).get("deviceName").getAsString();

        //获取指令
        String order = convert2OBJ(convert2OBJ(jsonObject.get("session")).get("attributes")).get("opType").getAsString();

        JsonObject orderObj = new JsonObject();
        orderObj.addProperty("device", device);
        orderObj.addProperty("order", order);

        logger4J.info("info from dingdong is : " + orderObj.toString());

        String resInfo = SocketUtil.send(orderObj.toString());
        logger4J.info("info from tytserver is : " + resInfo);
    }


    /**
     * 处理返回报文
     * @param reqInfo
     * @return
     */
    public String resProcess(String reqInfo){

        JsonParser jsonParser = new JsonParser();
        JsonObject reqObj = (JsonObject) jsonParser.parse(reqInfo);
        String status  = reqObj.get("status").getAsString();
        logger4J.info("get dingDong status is : " + status);

        String resContent = "好的，已经通知小泰管家。";
        boolean is_end = true;
        String sequence = reqObj.get("sequence").getAsString();

        if ("END".equals(status)){
            logger4J.info("get dingDong ended_reason is : " + reqObj.get("ended_reason").getAsString());
            resContent = "当前回话已经结束。";
            is_end = true;
        }

        JsonObject directive_item = new JsonObject();
        directive_item.addProperty("content", resContent);
        directive_item.addProperty("type", "1");

        JsonArray directive_items = new JsonArray();
        directive_items.add(directive_item);

        JsonObject directive = new JsonObject();
        directive.add("directive_items", directive_items);

        JsonObject extend = new JsonObject();
        extend.addProperty("NO_REC", "0");

        JsonObject obj = new JsonObject();
        obj.add("directive", directive);
        obj.add("extend", extend);
        obj.addProperty("is_end", is_end);
        obj.addProperty("sequence", sequence);
        obj.addProperty("timestamp", System.currentTimeMillis());
        obj.addProperty("versionid", "1.0");

        return obj.toString();
    }

    public static JsonObject convert2OBJ (JsonElement jsonElement){
        return (JsonObject) jsonElement;
    }


//    public static void main(String[] args) {
//        JsonObject orderObj = new JsonObject();
//        orderObj.addProperty("device", "窗帘");
//        orderObj.addProperty("order", "turnOn");
//        System.out.println(orderObj.toString());
//    }
}
