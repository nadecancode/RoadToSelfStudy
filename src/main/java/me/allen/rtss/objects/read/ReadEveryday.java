package me.allen.rtss.objects.read;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.allen.rtss.SelfStudyBot;
import me.allen.rtss.database.SSMongo;
import me.allen.rtss.util.DateUtil;
import me.allen.rtss.util.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@Data
@RequiredArgsConstructor
public class ReadEveryday {

    private static List<ReadEveryday> reads = new CopyOnWriteArrayList<>();

    private final String title, content;

    private boolean completed = false;

    private String titleLower = this.title.toLowerCase();

    private List<String> externalUrls = new ArrayList<>();

    private long readDuration = TimeUnit.MINUTES.toMillis(30); //Normally 30 Minutes Reading

    public static HashSet<ReadEveryday> getReadings() {
        return new HashSet<>(reads);
    }

    public void addToCache() {
        if (reads.stream().anyMatch(read -> read.getTitle().equalsIgnoreCase(this.title))) return;

        reads.add(this);
    }

    public boolean canNotify() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        return simpleDateFormat.format(date).equalsIgnoreCase("20:00");
    }

    public void notifyWithBot(boolean remind) {
        SelfStudyBot.getInstance()
                .getReadingChannel()
                .sendMessage(
                        new EmbedBuilder()
                                .setAuthor("TheTsundereAllen")
                                .setColor(Color.CYAN)
                                .setTitle(remind ? "You should start reading right now!" : "The information of this reading has shown below")
                                .addField("Name", this.title, true)
                                .addField("Content", this.content, true)
                                .addField("Duration", TimeUtil.millisToRoundedTime(this.readDuration), false)
                                .addBlankField(true)
                                .addField("External Links", String.join("\n", this.externalUrls), false)
                                .build()
                ).queue();
    }

    public void save() {
        SSMongo.getInstance().addReading(this);
    }

    public void delete() {
        reads.remove(this);
        SSMongo.getInstance().removeReading(this);
    }

}
