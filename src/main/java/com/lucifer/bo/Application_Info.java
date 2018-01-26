package com.lucifer.bo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by lucifer on 2018/1/9.
 */
public class Application_Info {

    private String application_id;


    public static void main(String[] args) {

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse("{\"versionid\":\"1.0\",\"status\":\"NOTICE\",\"sequence\":\"68fe41983cea4367be399df410882a5c\",\"timestamp\":1515490923119,\"application_info\":{\"application_id\":\"xiax544c\",\"application_name\":\"open_XiaoTaiGuanJia\",\"application_version\":\"10000\"},\"session\":{\"is_new\":true,\"session_id\":\"c42feb72c19a4ef0bca990fd2855297b\",\"attributes\":{\"deviceName\":\"卧室窗帘\",\"opType\":\"turnOn\",\"type\":\"turnOnOp\"}},\"user\":{\"user_id\":\"ce7fe73040be49c691d4dd0b0275a882\",\"attributes\":{}},\"input_text\":\"让小泰管家帮我打开卧室窗帘。\",\"notice_type\":\"DEV_SERVICE_RESP_PACK_ERROR\",\"extend\":{}}\n");

        System.out.println(jsonObject.get("sequence").toString());
    }

    public static JsonObject convert2OBJ (JsonElement jsonElement){
        return (JsonObject) jsonElement;
    }


}
