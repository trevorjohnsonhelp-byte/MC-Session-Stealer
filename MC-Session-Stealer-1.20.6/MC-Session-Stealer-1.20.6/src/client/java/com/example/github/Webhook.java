package com.example.github;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;

public class Webhook {

    public static void sendWebhook(String webhookUrl, String message) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Java-Minecraft-Client");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            JsonObject payload = new JsonObject();
            payload.addProperty("content", message);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(payload.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode >= 400) {
                System.err.println("Discord webhook failed: " + responseCode);
            } else {
                System.out.println("Webhook sent successfully!");
            }

            connection.getInputStream().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}