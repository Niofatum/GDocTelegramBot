package com.niofatum.yellowshop.employeesbot.commands;

import com.annimon.tgbotsmodule.commands.CommandBundle;
import com.annimon.tgbotsmodule.commands.CommandRegistry;
import com.annimon.tgbotsmodule.commands.SimpleCallbackQueryCommand;
import com.annimon.tgbotsmodule.commands.SimpleCommand;
import com.annimon.tgbotsmodule.commands.authority.For;
import com.annimon.tgbotsmodule.commands.context.CallbackQueryContext;
import com.annimon.tgbotsmodule.commands.context.MessageContext;
import com.niofatum.yellowshop.employeesbot.handlers.YellowShopEmployeesBot;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class GoodbyCommand implements CommandBundle<For> {

    @Override
    public void register(@NotNull CommandRegistry<For> registry) {
        registry.splitCallbackCommandByColon();
        registry.register(new SimpleCallbackQueryCommand("goodby", this::hellowMessages));
        registry.register(new SimpleCommand("/goodby", this::startSendMessages));
    }

    private void hellowMessages(CallbackQueryContext ctx) {
        YellowShopEmployeesBot bot = YellowShopEmployeesBot.getBot();
        String username = ctx.user().getUserName();
        Long chatId = ctx.message().getChatId();
        ctx.editMessage("До свидания, " + username).callAsync(ctx.sender);
        bot.sendMessageToEmployeers(username + ", Работу завершил(а)");
        bot.getEmployees().put(chatId, username);
    }

    private void startSendMessages(MessageContext ctx) {
        final var keyboard = new ArrayList<List<InlineKeyboardButton>>();

        var btn = InlineKeyboardButton.builder()
                .text("Работу завершил(а)")
                .callbackData("goodby")
                .build();
        keyboard.add(List.of(btn));

        ctx.reply("Кнопки")
                .setReplyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboard).build())
                .callAsync(ctx.sender);
    }
}