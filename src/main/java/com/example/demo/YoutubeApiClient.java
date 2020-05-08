package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class YoutubeApiClient {

    private static final String YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/";
    public static List<String> getVideos (String key, int maxResult, String regionCode) {
        URL url = null;
        String param = "videos?part=id" +
                "&key=" + key +
                "&chart=mostPopular" +
                "&maxResults=" + maxResult +
                "&regionCode=" + regionCode;

        try {
            url = new URL(YOUTUBE_API_URL + param);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new UncheckedIOException(e);
        }

        HttpsURLConnection uc;
        try {
            uc = (HttpsURLConnection) url.openConnection();
            uc.setRequestMethod("GET");
            uc.setUseCaches(false);
            uc.setDoOutput(true);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        
        try(BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()))) {
            String line = in.readLine();
            String body = "";
            while (line != null) {
                body = body + line;
                line = in.readLine();
            }
            uc.disconnect();

            ObjectMapper mapper = new ObjectMapper();
            YoutubeApiJson youtubeApiJson = mapper.readValue(body, YoutubeApiJson.class);

            List<String> videos = new ArrayList<String>();
            String youtubeUrl = "https://youtube.com/watch?v=";

            for(int i = 0; i < maxResult; i++) {
                videos.add(youtubeUrl + youtubeApiJson.getItems()[i].getId());
            }

            return videos;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
