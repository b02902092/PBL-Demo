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
        int maxResult = 5; //Youtubeに返事した人気動画の数です。デフォルトで5にしておきます。
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
            json = YoutubeApiClient.getVideos(youtubeGetVideosUrl);

            ObjectMapper mapper = new ObjectMapper();
            youtubeApiJson = mapper.readValue(json, YoutubeApiJson.class);

            System.out.println(youtubeApiJson.toString());
        } catch (MalformedURLException | JsonProcessingException e) {
            e.printStackTrace();
            throw new UncheckedIOException(e);
        }

        SlackApiClient slackApiClient = new SlackApiClient();

        String incomeWebhookUrl = args[0];
        json = "{\"text\":\"今日のＨＯＴ動画は\n";
        for (int i = 0; i < maxResult; i++) {
            json += "https://youtube.com/watch?v=" + youtubeApiJson.getItems()[i].getId() + "\n";
        }
        json += "\"}";

        System.out.println(incomeWebhookUrl + '\n' + json);
        System.out.println(slackApiClient.postMessage(json, incomeWebhookUrl));
    }
}