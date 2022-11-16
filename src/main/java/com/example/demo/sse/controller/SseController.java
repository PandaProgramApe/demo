package com.example.demo.sse.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
@RestController
public class SseController {

    @RequestMapping(value = "/get", produces = "text/event-stream;charset=UTF-8")
    public void push(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");
        int i = 0;
        while (true) {
            try {
                Thread.sleep(1000);
                PrintWriter pw = response.getWriter();
                //注意返回数据必须以data:开头，"\n\n"结尾
                pw.write("data:xdm帅气值加" + i + "\n\n");
                pw.flush();
                if (pw.checkError()) {
                    log.error("客户端断开连接");
                    return;
                }
                System.out.println(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
    }

//    public void flush() {
//        try {
//            synchronized (lock) {
//                ensureOpen();
//                out.flush();
//            }
//        }
//        catch (IOException x) {
//            trouble = true;
//        }
//    }
}