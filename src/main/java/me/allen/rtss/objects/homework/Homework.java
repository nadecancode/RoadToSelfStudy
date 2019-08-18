package me.allen.rtss.objects.homework;

import javafx.scene.effect.SepiaTone;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.allen.rtss.SelfStudyBot;
import me.allen.rtss.util.DateUtil;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Data
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Homework {
    private static List<Homework> homework = new CopyOnWriteArrayList<>();

    private final String title, subject, content;

    private boolean done = false;

    private final long assignDate, dueDate;

    private boolean notified = false;

    public boolean canNotify() {
        return !this.isNotified() && System.currentTimeMillis() > this.dueDate - TimeUnit.HOURS.toMillis(6);
    }

    public boolean isDue() {
        return System.currentTimeMillis() > this.dueDate;
    }

    public static Homework matchHomeworkByName(String title) {
        return homework.stream().filter(hw -> hw.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }

    public static List<Homework> matchHomeworkBySubject(String subject) {
        return homework.stream().filter(hw -> hw.getSubject().equalsIgnoreCase(subject)).collect(Collectors.toList());
    }

    public static List<Homework> matchHomeworkByDueDate(long dueDate) {
        return homework.stream().filter(hw -> hw.getDueDate() == dueDate).collect(Collectors.toList());
    }

    public static Set<Homework> getHomeworks() {
        return new HashSet<>(homework);
    }

    public void notifyWithBot() {
        SelfStudyBot.getInstance()
                .getHomeworkChannel()
                .sendMessage(
                        new EmbedBuilder()
                        .setAuthor("TheTsundereAllen")
                        .setColor(Color.CYAN)
                        .setTitle("You received a new homework notification since you didn't mark this as finished homework!")
                        .addField("Name", this.title, true)
                        .addField("Subject", this.subject, true)
                        .addField("Content", this.content, true)
                        .addField("Due Date", DateUtil.formatDueDate(this.dueDate), false)
                        .addField("Assign Date", DateUtil.formatDueDate(this.assignDate), false)
                        .build()
                ).queue();
    }

}
