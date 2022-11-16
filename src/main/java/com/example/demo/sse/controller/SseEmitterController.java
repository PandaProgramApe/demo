package com.example.demo.sse.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/sse")
@Slf4j
public class SseEmitterController {


    private static Map<String, SseEmitter> cache = new ConcurrentHashMap<>();

    String clientId;
    int sseId;

    @GetMapping("/create")
    public SseEmitter create(@RequestParam(name = "clientId", required = false) String clientId) {

        // 设置超时时间，0表示不过期。默认30秒
        SseEmitter sseEmitter = new SseEmitter(0L);
        // 是否需要给客户端推送ID
        if (Strings.isBlank(clientId)) {
            clientId = UUID.randomUUID().toString();
        }

        this.clientId = clientId;
        cache.put(clientId, sseEmitter);
        log.info("sse连接，当前客户端：{}", clientId);
        return sseEmitter;
    }

    @Scheduled(cron = "0/3 * *  * * ? ")
    public void pushMessage() {
        try {
            sseId++;
            SseEmitter sseEmitter = cache.get(clientId);
            sseEmitter.send(
                    SseEmitter
                            .event()
                            .data("帅气值暴增" + sseId)
                            .id("" + sseId)
                            .reconnectTime(3000)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            sseId--;
        }
    }

    @GetMapping("/close")
    public void close(String clientId) {
        SseEmitter sseEmitter = cache.get(clientId);
        if (sseEmitter != null) {
            sseEmitter.complete();
            cache.remove(clientId);
        }
    }
}
