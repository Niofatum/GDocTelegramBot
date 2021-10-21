package com.niofatum.yellowshop.employeesbot.commands;

import com.annimon.tgbotsmodule.commands.CommandBundle;
import com.annimon.tgbotsmodule.commands.CommandRegistry;
import com.annimon.tgbotsmodule.commands.SimpleRegexCommand;
import com.annimon.tgbotsmodule.commands.authority.For;
import com.annimon.tgbotsmodule.commands.context.MessageContext;
import com.niofatum.yellowshop.employeesbot.handlers.YellowShopEmployeesBot;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public class StartCommand implements CommandBundle<For> {

    @Override
    public void register(@NotNull CommandRegistry<For> registry) {
        registry.register(new SimpleRegexCommand("/start", this::startSendMessages));
    }

    private void startSendMessages(MessageContext ctx) {
        YellowShopEmployeesBot bot = YellowShopEmployeesBot.getBot();
        bot.getEmployees().addChatId(ctx.chatId(), ctx.user());

        Timer timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            @Override
            public void run() {
                //ctx.reply("Its Work").callAsync(ctx.sender);
            }
        };
        timer.schedule(hourlyTask, 0l, 1000);
    }
}

