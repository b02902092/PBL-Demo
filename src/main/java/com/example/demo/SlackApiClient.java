package com.example.demo;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class SlackApiClient {
    public String postMessage(String json, String path) {
        HttpsURLConnection uc;
        try {
            URL url = new URL(path);
            uc = (HttpsURLConnection) url.openConnection();
            uc.setRequestMethod("POST");
            uc.setUseCaches(false);
            uc.setDoOutput(true);
            uc.setRequestProperty("Content-Type", "application/json");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        try (OutputStreamWriter out = new OutputStreamWriter(
                new BufferedOutputStream(uc.getOutputStream()))) {
            out.write(json);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()))) {
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
