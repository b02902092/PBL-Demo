package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        YoutubeApiClient y = new YoutubeApiClient();
        String path = "https://www.googleapis.com/youtube/v3/videos";
        String param = "?part=id" +
                "&key=AIzaSyA1JJGm6zCWngsMkwmIUZsOHpO8oTRfnzc" +
                "&chart=mostPopular" +
                "&maxResults=1";

        System.out.println(path+param);
        String json = y.youtubeApiClient(param, path);
        System.out.println("Youtube return:" + json);

        SlackPostJson p = new SlackPostJson();
        path = "https://hooks.slack.com/services/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";
        json = "{\"text\":\"Test Message\"}";
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

        System.out.println(path + '\n' + json);
        System.out.println(p.slackPostJson(json, path));

    }
}