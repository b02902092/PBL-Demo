package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        PostJson p = new PostJson();
        String path = "https://hooks.slack.com/services/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";
        String json = "{\"text\":\"Test Message\"}";
/*
        String path = "https://slack.com/api/chat.postMessage";
        String token = "xoxb-2451879749-1066005771747-mXYRM6xNKqJUXizAieKqAA79";
        String user = "lai.ting.wei";
        String json = "{" +
                "\"token\"=\"" + token + "\"," +
                "\"channel\"=\"@" + user + "\"," +
                "\"text\"=\"" + "Hello World!" + "\"," +
                "\"as_user\"=\"1\"" +
                "}";*/

        System.out.println(json + "\n" + path);
        System.out.println(p.postJson(json, path));
    }
}