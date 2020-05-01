package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class DemoApplication {
    public static void main(String[] args) {
        int maxResult = 5; //Youtubeに返事した人気動画の数です。デフォルトで5にしておきます。

        String json = "";

        YoutubeApiJson youtubeApiJson = null;
        try {
            json = YoutubeApiClient.getVideos(args[1], maxResult, "JP");

            ObjectMapper mapper = new ObjectMapper();
            youtubeApiJson = mapper.readValue(json, YoutubeApiJson.class);

            System.out.println(youtubeApiJson.toString());
        } catch (JsonProcessingException e) {
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