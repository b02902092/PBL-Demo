package com.example.demo;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class YoutubeApiClient {

    private static final String youtubeApiUrl = "https://www.googleapis.com/youtube/v3/videos";
    public static String getVideos (String param) {

        URL url = null;
        try {
            url = new URL(youtubeApiUrl + param);
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
