package com.example.demo.sse.config;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * 线程安全封装
 */
public class SSEThreadSafeWrapper extends SseEmitter {
    public SSEThreadSafeWrapper() {
        super();
    }

    public SSEThreadSafeWrapper(long l) {
        super(l);
    }

    @Override
    public void send(SseEventBuilder arg0) throws IOException {
        // 线程安全
        synchronized (this) {
            super.send(arg0);
        }
    }

    @Override
    public void send(Object object) throws IOException {
        // 线程安全
        synchronized (this) {
            super.send(object);
        }
    }

    @Override
    public void send(Object object, MediaType mediaType) throws IOException {
        // 线程安全
        synchronized (this) {
            super.send(object, mediaType);
        }
    }
}
