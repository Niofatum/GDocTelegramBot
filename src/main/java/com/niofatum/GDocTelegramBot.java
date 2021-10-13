package com.niofatum;

import com.annimon.tgbotsmodule.BotHandler;
import com.annimon.tgbotsmodule.BotModule;
import com.annimon.tgbotsmodule.Runner;
import com.annimon.tgbotsmodule.beans.Config;
import com.niofatum.handlers.GDocBotHandler;

import java.util.List;

public class GDocTelegramBot implements BotModule {

    public static void main(String[] args) {
        final var profile = (args.length >= 1 && !args[0].isEmpty()) ? args[0] : "";
        Runner.run(profile, List.of(new GDocTelegramBot()));
    }

    @Override
    public BotHandler botHandler(Config config) {
        return new GDocBotHandler();
    }
}