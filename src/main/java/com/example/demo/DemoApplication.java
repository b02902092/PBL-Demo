package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        YoutubeApiClient youtubeApiClient = new YoutubeApiClient();
        String youtubeApiUrl = "https://www.googleapis.com/youtube/v3/videos";
        String param = "?part=id" +
                "&key=" + args[1] +
                "&chart=mostPopular" +
                "&maxResults=1";
        URL youtubeGetVideosUrl = null;
        String json = "";

        try {
            youtubeGetVideosUrl = new URL(youtubeApiUrl + param);
            System.out.println(youtubeGetVideosUrl.toString());
            json = youtubeApiClient.getVideos(youtubeGetVideosUrl);
            System.out.println("Youtube return:" + json);
        } catch (MalformedURLException e) {
            throw new RuntimeException(); //ここは何を投げればいいですか？
        }

        SlackApiClient slackApiClient = new SlackApiClient();

        String incomeWebhookUrl = args[0];
        json = "{\"text\":\"Test Message\"}";

        System.out.println(incomeWebhookUrl + '\n' + json);
        System.out.println(slackApiClient.postMessage(json, incomeWebhookUrl));

/*
        String path = "https://slack.com/api/chat.postMessage";
        String token = "";
        String user = "lai.ting.wei";
        String json = "{" +
                "\"token\"=\"" + token + "\"," +
                "\"channel\"=\"@" + user + "\"," +
                "\"text\"=\"" + "Hello World!" + "\"," +
                "\"as_user\"=\"1\"" +
                "}";*/

        System.out.println("Finish");
    }
}