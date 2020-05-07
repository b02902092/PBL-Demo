package com.example.demo;

public class DemoApplication {
    public static void main(String[] args) {
        int maxResult = 5; //Youtubeに返事した人気動画の数です。デフォルトで5にしておきます。

        String[] videos = YoutubeApiClient.getVideos(args[1], maxResult, "JP");

        SlackApiClient slackApiClient = new SlackApiClient();

        String incomeWebhookUrl = args[0];
        String json = "{\"text\":\"今日のＨＯＴ動画は\n";
        for (int i = 0; i < maxResult; i++) {
            json += videos[i];
        }
        json += "\"}";

        System.out.println(incomeWebhookUrl + '\n' + json);
        System.out.println(slackApiClient.postMessage(json, incomeWebhookUrl));
    }
}