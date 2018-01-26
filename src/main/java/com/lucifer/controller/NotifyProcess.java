package com.lucifer.controller;

import com.lucifer.service.NotifyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lucifer on 2018/1/11.
 */
@Controller
public class NotifyProcess {

    @Autowired
    private NotifyService notifyService;

    private static Logger logger4J = Logger.getLogger(NotifyProcess.class.getSimpleName());

    @ResponseBody
    @RequestMapping(value = "/notify")
    public void call(HttpServletRequest request, HttpServletResponse response) {


        try {
            InputStream inputStream = request.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String info = null;
            StringBuffer buffer = new StringBuffer();
            while (null != (info = bufferedReader.readLine())){
                logger4J.info("dingDong notify info is : " + info);
                buffer.append(info);
            }

            String resInfo = notifyService.notifyProcess(info);
            logger4J.info("dingDongServer reply info is : " + resInfo);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(resInfo);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
