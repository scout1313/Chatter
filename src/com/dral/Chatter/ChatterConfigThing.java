package com.dral.Chatter;

public class ChatterConfigThing {
    Chatter Chatter;
    Boolean hasChanged = false;

    public ChatterConfigThing(Chatter Chatter) {
        this.Chatter = Chatter;
    }

    protected void loadConfig() {
        Configuration config = Chatter.config;
        config.load();

        Chatter.censorWords = config.getStringList("censor-list", Chatter.censorWords);
        Chatter.chatFormat = config.getString("message-format", Chatter.chatFormat);
        Chatter.meFormat = config.getString("me-format", Chatter.meFormat);
        Chatter.nameFormat = config.getString("name-format", Chatter.nameFormat);
        Chatter.dateFormat = config.getString("date-format", Chatter.nameFormat);
        Chatter.textwrapping = config.getBoolean("text-wrapping", Chatter.textwrapping);
        Chatter.nether_name = config.getString("nether-name", Chatter.nether_name);
        Chatter.logEverything = config.getBoolean("log-everything", Chatter.logEverything);
    }

    protected void defaultConfig() {
        Configuration config = Chatter.config;
        config.save();

        config.setProperty("name-format", Chatter.nameFormat);
        config.setProperty("text-wrapping", Chatter.textwrapping);
        config.setProperty("censor-list", Chatter.censorWords);
        config.setProperty("first-message-format", Chatter.chatFormat);
        config.setProperty("date-format", Chatter.dateFormat);
        config.setProperty("me-format", Chatter.meFormat);
        config.setProperty("nether-name", Chatter.nether_name);
        config.setProperty("log-everything", Chatter.logEverything);
        config.save();
    }

    protected void checkConfig() {
        Configuration config = Chatter.config;
        config.load();

        if (config.getProperty("name-format") == null) {
            config.setProperty("name-format", Chatter.nameFormat);
            hasChanged = true;
        }

        if (config.getProperty("text-wrapping") == null) {
            config.setProperty("text-wrapping", Chatter.textwrapping);
            hasChanged = true;
        }

        if (config.getProperty("censor-list") == null) {
            config.setProperty("censor-list", Chatter.censorWords);
            hasChanged = true;
        }


        if (config.getProperty("date-format") == null) {
            config.setProperty("date-format", Chatter.dateFormat);
            hasChanged = true;
        }

        if (config.getProperty("message-format") == null) {
            config.setProperty("message-format", Chatter.chatFormat);
            hasChanged = true;
        }

        if (config.getProperty("first-message-format") != null) {
            config.setProperty("message-format", config.getProperty("first-message-format"));
            config.removeProperty("first-message-format");
            hasChanged = true;
        }

        if (config.getProperty("message-format") == null) {
            config.setProperty("message-format", Chatter.chatFormat);
            hasChanged = true;
        }

        if (config.getProperty("nether-name") == null) {
            config.setProperty("nether-name", Chatter.nether_name);
            hasChanged = true;
        }

        if (config.getProperty("log-everything") == null) {
            config.setProperty("log-everything", Chatter.logEverything);
            hasChanged = true;
        }

        if (config.getProperty("factions-support") != null) {
            config.removeProperty("factions-support");
            hasChanged = true;
        }

        if (hasChanged) {
            Chatter.logIt("the config has been updated :D");
            config.save();
        }
    }
}