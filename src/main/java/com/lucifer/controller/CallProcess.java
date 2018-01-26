package com.lucifer.controller;

import com.lucifer.service.TytService;
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
 * Created by lucifer on 2018/1/8.
 */
@Controller
public class CallProcess {
    @Autowired
    private TytService tytService;

    private static Logger logger4J = Logger.getLogger(CallProcess.class.getSimpleName());

    @ResponseBody
    @RequestMapping(value = "/call")
    public void call(HttpServletRequest request, HttpServletResponse response) {


        try {
            InputStream inputStream = request.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String info = null;
            StringBuffer buffer = new StringBuffer();
            while (null != (info = bufferedReader.readLine())){
                logger4J.info("dingDong call info is : " + info);
                buffer.append(info);
            }
            String resInfo = tytService.tytProcess(buffer.toString());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(resInfo);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
