package com.dral.Chatter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ChatterPhoneHome implements Runnable {
    private Chatter plugin;
    private int pub = 1;

    public ChatterPhoneHome(Chatter plugin) {
        this.plugin = plugin;

    }


    public void run() {
        try {
            if (postUrl().contains("Success")) return;
        } catch (Exception ignored) {
        }
    }

    private String postUrl() throws Exception {
        String url = String.format("http://games.detora.nl/minecraft/updateList.php?name=%s&build=%s&plugin=%s&port=%s&public=%s",
                plugin.getServer().getName(),
                plugin.getDescription().getVersion(),
                plugin.getDescription().getName(),
                plugin.getServer().getPort(),
                pub);
        URL oracle = new URL(url);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;
        String result = "";
        while ((inputLine = in.readLine()) != null)
            result += inputLine;
        return result;
    }
}