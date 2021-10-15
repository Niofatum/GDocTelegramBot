package com.niofatum.handlers;


import com.niofatum.commands.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandsHandler extends TelegramLongPollingCommandBot {

    public CommandsHandler() {
        super();
        register(new StartCommand("start", "Старт"));;
    }

    @Override
    public String getBotToken() {
        return System.getenv("token");
    }

    @Override
    public String getBotUsername() {
        return System.getenv("username");
    }

    @Override
    public void processNonCommandUpdate(Update update) {
    }

}