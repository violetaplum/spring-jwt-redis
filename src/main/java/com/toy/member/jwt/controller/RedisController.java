package com.toy.member.jwt.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.HashMap;


@RestController
// 로그찍는애
@Slf4j
public class RedisController {
    @Value("${redis.host}")
    private static String ip;

    @GetMapping("/redis-test")
    public Map<String, String> uid(HttpSession session) {
        UUID uid = Optional.ofNullable(UUID.class.cast(session.getAttribute("uid")))
                .orElse(UUID.randomUUID());
        session.setAttribute("uid", uid);

        Map<String,String> m = new HashMap<>();
        m.put("instance_ip", ip);
        m.put("uuid", uid.toString());
        return m;
    }
}
