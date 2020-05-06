package com.example.demo;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class YoutubeApiClient {

    private static final String YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/";
    public static String getVideos (String key, int maxResult, String regionCode) {
        if (maxResult <= 0 | maxResult > 50) {
            System.out.println("maxResult should be 1-50.");
            throw new IllegalArgumentException();
        }
        
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
            return body;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
