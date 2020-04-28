package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        int maxResult = 5;
        String youtubeApiUrl = "https://www.googleapis.com/youtube/v3/videos";
        String param = "?part=id" +
                "&key=" + args[1] +
                "&chart=mostPopular" +
                "&maxResults=" + maxResult +
                "&regionCode=JP";
        URL youtubeGetVideosUrl = null;
        String json = "";

        YoutubeApiJson youtubeApiJson = null;
        try {
            youtubeGetVideosUrl = new URL(youtubeApiUrl + param);
            //System.out.println(youtubeGetVideosUrl.toString());
            json = youtubeApiClient.getVideos(youtubeGetVideosUrl);
            //System.out.println("Youtube return:" + json);

            ObjectMapper mapper = new ObjectMapper();
            youtubeApiJson = mapper.readValue(json, YoutubeApiJson.class);
            //System.out.println("YoutubeApiJson: " + youtubeApiJson.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(); //ここは何を投げればいいですか？
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        SlackApiClient slackApiClient = new SlackApiClient();

        String incomeWebhookUrl = args[0];
        //json = "{\"text\":\"Test Message\"}";
        json = "{\"text\":\"今日のＨＯＴ動画は\n";
        for (int i = 0; i < maxResult; i++) {
            json += "https://youtube.com/watch?v=" + youtubeApiJson.getYoutubeVideoJsons()[i].getId() + "\n";
        }
        json += "\"}";

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