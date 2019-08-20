package me.allen.rtss.objects.reminder;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.allen.rtss.SelfStudyBot;
import me.allen.rtss.database.SSMongo;
import me.allen.rtss.type.StudyType;
import me.allen.rtss.util.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
@Data
public class Reminder {

    private static List<Reminder> reminders = new CopyOnWriteArrayList<>();

    private final String uuid;

    private final String content;

    private final long time;

    private final StudyType type = StudyType.REMINDER;

    public boolean canRemind() {
        return System.currentTimeMillis() >= this.time;
    }

    public static Set<Reminder> getReminders() {
        return new HashSet<>(reminders);
    }

    public void addToCache() {
        if (reminders.stream().anyMatch(read -> read.getUuid().equalsIgnoreCase(this.uuid))) return;

        reminders.add(this);
    }

    public void notifyWithBot(boolean remind) {
        SelfStudyBot.getInstance()
                .getReminderChannel()
                .sendMessage(
                        new EmbedBuilder()
                                .setAuthor("TheTsundereAllen")
                                .setColor(Color.CYAN)
                                .setTitle(remind ? "You have an upcoming reminder" : "The information of this reminder has shown below")
                                .addField("UUID", this.uuid, true)
                                .addField("Content", this.content, true)
                                .build()
                ).queue();
    }
    public void save() {
        SSMongo.getInstance().addReminder(this);
    }

    public void delete() {
        reminders.remove(this);
        SSMongo.getInstance().removeReminder(this);
    }

}
