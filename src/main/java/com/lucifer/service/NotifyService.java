package com.lucifer.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by lucifer on 2018/1/11.
 */
@Service
public class NotifyService {

    private static Logger logger4J = Logger.getLogger(NotifyService.class.getSimpleName());
    /**
     * 处理叮咚音箱通知报文
     * @param reqInfo
     * @return
     */
    public String notifyProcess(String reqInfo){

        JsonParser jsonParser = new JsonParser();
        JsonObject reqObj = (JsonObject) jsonParser.parse(reqInfo);

        logger4J.info("get dingDong status is : " + reqObj.get("status").getAsString());
        logger4J.info("get dingDong ended_reason is : " + reqObj.get("ended_reason").getAsString());

        JsonObject directive_item = new JsonObject();
        directive_item.addProperty("content", "本次会话已经结束");
        directive_item.addProperty("type", "1");

        JsonArray directive_items = new JsonArray();
        directive_items.add(directive_item);

        JsonObject directive = new JsonObject();
        directive.add("directive_items", directive_items);

        JsonObject extend = new JsonObject();
        extend.addProperty("NO_REC", "0");

        JsonObject resObj = new JsonObject();
        resObj.add("directive", directive);
        resObj.add("extend", extend);
        resObj.addProperty("is_end", true);
        resObj.addProperty("sequence", reqObj.get("sequence").getAsString());
        resObj.addProperty("timestamp", System.currentTimeMillis());
        resObj.addProperty("versionid", "1.0");

        return resObj.toString();
    }

    public static JsonObject convert2OBJ (JsonElement jsonElement){
        return (JsonObject) jsonElement;
    }
}
