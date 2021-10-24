package com.niofatum.yellowshop.employeesbot.handlers;

import com.annimon.tgbotsmodule.BotHandler;
import com.annimon.tgbotsmodule.commands.CommandRegistry;
import com.annimon.tgbotsmodule.commands.authority.For;
import com.annimon.tgbotsmodule.commands.authority.SimpleAuthority;
import com.niofatum.yellowshop.employeesbot.commands.GoodbyCommand;
import com.niofatum.yellowshop.employeesbot.commands.HellowCommand;
import com.niofatum.yellowshop.employeesbot.commands.StartCommand;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class YellowShopEmployeesBot extends BotHandler {

    private final CommandRegistry<For> commands;

    private HashMap<Long, String> employees;
    private static YellowShopEmployeesBot bot;

    public YellowShopEmployeesBot() {
        bot = this;
        final var authority = new SimpleAuthority(this, getCreatorId());
        commands = new CommandRegistry<>(this, authority);
        commands.registerBundle(new StartCommand());
        commands.registerBundle(new HellowCommand());
        commands.registerBundle(new GoodbyCommand());
        employees = new HashMap<>();
    }

    @Override
    public String getBotUsername() {
        return System.getenv("username");
    }

    @Override
    public String getBotToken() {
        return System.getenv("token");
    }

    public Long getCreatorId() {
        return Long.valueOf(System.getenv("creatorid"));
    }

    @Override
    protected BotApiMethod<?> onUpdate(@NotNull Update update) {
        if (commands.handleUpdate(update)) {
            return null;
        }

        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        User user = msg.getFrom();
        String userName = user.getUserName();

        sendSignMessage(msg, userName, chatId);

        return null;
    }

    public void sendMessage(String message, Long chatId) {
        SendMessage answer = new SendMessage();
        answer.setText(message);
        answer.setChatId(chatId.toString());

        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToEmployeers(String message) {
        getEmployees().forEach((chatId, userName) -> {
            sendMessage(message, chatId);
        });
    }

    public void sendSignMessage(Message msg, String userName, Long chatId) {
        switch (msg.getText()) {

            case "Доброе утро":
                sendMessage("Удачного дня, " + userName, chatId);
                sendMessageToEmployeers(userName + ", Приступил(a) к работе");
                getEmployees().put(chatId, userName);
                break;
            case "Работу завершил":
            case "Работу завершила":
                sendMessage("До свидания, " + userName, chatId);
                getEmployees().remove(chatId);
                sendMessageToEmployeers(userName + ", Работу завершил(а)");
                break;
            default:
                sendMessage("Моя не понимать что ты хотеть 0_о", chatId);
                break;
        }
    }

    public static YellowShopEmployeesBot getBot() {
        return bot;
    }

    public HashMap<Long, String> getEmployees() {
        return employees;
    }
}
