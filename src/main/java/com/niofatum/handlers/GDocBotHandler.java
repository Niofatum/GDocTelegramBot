package com.niofatum.handlers;

import com.annimon.tgbotsmodule.BotHandler;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Timer;
import java.util.TimerTask;

public class GDocBotHandler extends BotHandler{

    private String chatId;

    public GDocBotHandler(){
        new CommandsHandler();
    }

    @Override
    public String getBotUsername() {
        return System.getenv("username");
    }

    @Override
    public String getBotToken() {
        return System.getenv("token");
    }

    @Override
    public BotApiMethod onUpdate(Update update) {
        if (!update.hasMessage()) {
            return null;
        }

        Message message = update.getMessage();
        if (!message.hasText()) {
            return null;
        }

        String text = message.getText();
        chatId = message.getChatId().toString();
        sendMessageTimer(text,this.chatId,10);

        return null;
    }

    private void sendMessageTimer(String message,String chatId,Integer period){
        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {
                SendMessage sm = new SendMessage();
                sm.setText(message);
                sm.setChatId(chatId);
                try {
                    execute(sm);
                } catch (TelegramApiException e) {
                }
            }
        };
        timer.schedule (hourlyTask, 0l, period);
    }
}
