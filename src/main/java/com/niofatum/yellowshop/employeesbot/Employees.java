package com.niofatum.yellowshop.employeesbot;

import com.niofatum.yellowshop.employeesbot.handlers.YellowShopEmployeesBot;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;

public class Employees {

    private HashMap<Long, User> employees;

    public Employees() {
        employees = new HashMap<>();
    }

    public void addChatId(Long id, User user) {
        employees.put(id, user);
    }

    public void removeEmployees(Long id) {
        employees.remove(id);
    }

    public void sendMessages(String message) {
        YellowShopEmployeesBot bot = YellowShopEmployeesBot.getBot();
        employees.forEach((chatId, user) -> {
            bot.sendMessage(message, chatId);
        });
    }

    public String getNameByChatId(Long id) {
        return employees.get(id).getUserName();
    }

}
