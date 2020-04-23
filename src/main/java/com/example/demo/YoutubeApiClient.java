package com.example.demo;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class YoutubeApiClient {
    public static String getVideos (URL url) {
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
