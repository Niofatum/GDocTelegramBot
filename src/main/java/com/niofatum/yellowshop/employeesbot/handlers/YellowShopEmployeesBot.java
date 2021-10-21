package com.niofatum.yellowshop.employeesbot.handlers;

import com.annimon.tgbotsmodule.BotHandler;
import com.annimon.tgbotsmodule.commands.CommandRegistry;
import com.annimon.tgbotsmodule.commands.authority.For;
import com.annimon.tgbotsmodule.commands.authority.SimpleAuthority;
import com.niofatum.yellowshop.employeesbot.Employees;
import com.niofatum.yellowshop.employeesbot.commands.StartCommand;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class YellowShopEmployeesBot extends BotHandler {

    private final CommandRegistry<For> commands;

    private Employees employees;
    private static YellowShopEmployeesBot bot;

    public YellowShopEmployeesBot() {
        bot = this;
        final var authority = new SimpleAuthority(this, 123);
        commands = new CommandRegistry<>(this, authority);
        commands.registerBundle(new StartCommand());
        employees = new Employees();
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
        /*
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                try {
                    System.out.println("exec" + chatId);
                    sendInlineKeyBoardMessage(chatId);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {

            sendMessage(update.getCallbackQuery().getData(), update.getCallbackQuery().getMessage().getChatId());
        }
     */

        String chat_id = update.getMessage().getChatId().toString();
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chat_id);
        message.setText("buttons" + "\n");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton inlbutton = new InlineKeyboardButton();
        inlbutton.setText("choose");
        inlbutton.setCallbackData("choose");
        rowInline.add(inlbutton);
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        System.out.println(update.getCallbackQuery());

        if (msg.getText().equalsIgnoreCase("Доброе утро")) {
            sendMessage("Удачного дня, " + userName, chatId);
            employees.sendMessages(userName + ", Приступил к работе");
        }




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

    public void sendInlineKeyBoardMessage(Long chatId) throws TelegramApiException {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Тык");
        inlineKeyboardButton1.setCallbackData("update_msg_text");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage answer = new SendMessage();
        answer.setText("Text");
        answer.setChatId(chatId.toString());
        answer.setReplyMarkup(inlineKeyboardMarkup);

        System.out.println("answ" + chatId);
        execute(answer);
    }

    public static YellowShopEmployeesBot getBot() {
        return bot;
    }

    public Employees getEmployees() {
        return employees;
    }
}
