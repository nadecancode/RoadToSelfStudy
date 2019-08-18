package me.allen.rtss;

import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import me.allen.rtss.runnable.SSRunnable;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Data
public class SelfStudyBot {

    private Guild server;

    @Getter
    private static SelfStudyBot instance;

    private String token = "";

    private JDA bot;

    private TextChannel reminderChannel, selfStudyChannel, homeworkChannel, readingChannel;

    @SneakyThrows
    public SelfStudyBot() {
        instance = this;

        this.loadConfig();

        this.bot = new JDABuilder()
                .setToken(this.token)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("Everyone to Learn"))
                .setAutoReconnect(true)
                .build();

        this.server = this.bot.getGuildById("612520734668881920");

        if (this.server == null) {
            System.out.println("Server not found! Quitting");
            System.exit(-1);
            return;
        }

        this.selfStudyChannel = (TextChannel) this.server.getGuildChannelById("612521254154665994");
        this.homeworkChannel = (TextChannel) this.server.getGuildChannelById("612521010301763605");
        this.readingChannel = (TextChannel) this.server.getGuildChannelById("612521057861107740");
        this.reminderChannel = (TextChannel) this.server.getGuildChannelById("612520982824878083");

        this.registerTasks();
    }

    private ScheduledExecutorService TASK_EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    @SneakyThrows
    private void loadConfig() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String configPath = rootPath + "configuration.properties";

        Properties botProps = new Properties();
        botProps.load(new FileInputStream(configPath));
        this.token = botProps.getProperty("token");
    }

    private void registerTasks() {
        TASK_EXECUTOR.scheduleAtFixedRate(new SSRunnable(), 0L, 1L, TimeUnit.MILLISECONDS);
    }
}
