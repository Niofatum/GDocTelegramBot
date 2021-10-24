package com.niofatum.yellowshop.employeesbot.commands;

import com.annimon.tgbotsmodule.commands.*;
import com.annimon.tgbotsmodule.commands.authority.For;
import com.annimon.tgbotsmodule.commands.context.CallbackQueryContext;
import com.annimon.tgbotsmodule.commands.context.MessageContext;
import com.niofatum.yellowshop.employeesbot.handlers.YellowShopEmployeesBot;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StartCommand implements CommandBundle<For> {

    @Override
    public void register(@NotNull CommandRegistry<For> registry) {
        registry.splitCallbackCommandByColon();
        registry.register(new SimpleCallbackQueryCommand("Доброе утро", this::hellowMessages));
        registry.register(new SimpleCallbackQueryCommand("Работу завершил(а)", this::goodbyMessages));
        registry.register(new SimpleCommand("/start", this::startSendMessages));
    }

    private void hellowMessages(CallbackQueryContext ctx) {
        YellowShopEmployeesBot bot = YellowShopEmployeesBot.getBot();
        String username = ctx.user().getUserName();
        Long chatId = ctx.message().getChatId();
        ctx.editMessage("Удачного дня, " + username).callAsync(ctx.sender);
        bot.sendMessageToEmployeers(username + ", Приступил к работе");
        bot.getEmployees().put(chatId, username);
    }

    private void goodbyMessages(CallbackQueryContext ctx) {
        YellowShopEmployeesBot bot = YellowShopEmployeesBot.getBot();
        String username = ctx.user().getUserName();
        Long chatId = ctx.message().getChatId();
        ctx.editMessage("До свидания, " + username).callAsync(ctx.sender);
        bot.sendMessageToEmployeers(username + ", Работу завершил(а)");
        bot.getEmployees().put(chatId, username);
    }


    private void startSendMessages(MessageContext ctx) {
        Timer timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            @Override
            public void run() {
            }
        };
        timer.schedule(hourlyTask, 0l, 1000);


        final var keyboard = new ArrayList<List<InlineKeyboardButton>>(2);
        for (var lang : List.of("Доброе утро", "Работу завершил(а)")) {
            var languageName = lang;
            var btn = InlineKeyboardButton.builder()
                    .text(languageName)
                    .callbackData(lang)
                    .build();
            keyboard.add(List.of(btn));
        }

        ctx.reply("Кнопки")
                .setReplyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboard).build())
                .callAsync(ctx.sender);
    }
}

