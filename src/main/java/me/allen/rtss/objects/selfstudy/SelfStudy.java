package me.allen.rtss.objects.selfstudy;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.allen.rtss.SelfStudyBot;
import me.allen.rtss.database.SSMongo;
import me.allen.rtss.objects.homework.Homework;
import me.allen.rtss.objects.reminder.Reminder;
import me.allen.rtss.type.StudyType;
import me.allen.rtss.util.DateUtil;
import me.allen.rtss.util.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@RequiredArgsConstructor
public class SelfStudy {

    private static List<SelfStudy> selfStudies = new CopyOnWriteArrayList<>();

    private final String title, content;

    private String titleLower = this.title.toLowerCase();

    private List<String> externalUrls = new ArrayList<>();

    private final long assignDate;

    private boolean completed = false;

    private long wishCompletionDate;

    private final StudyType type = StudyType.REMINDER;

    public static Set<SelfStudy> getSelfStudies() {
        return new HashSet<>(selfStudies);
    }

    public void addToCache() {
        if (selfStudies.stream().anyMatch(study -> study.getTitle().equalsIgnoreCase(this.title))) return;

        selfStudies.add(this);
    }

    public boolean canNotify() {
        return !completed && System.currentTimeMillis() > this.wishCompletionDate;
    }

    public void notifyWithBot(boolean remind) {
        SelfStudyBot.getInstance()
                .getSelfStudyChannel()
                .sendMessage(
                        new EmbedBuilder()
                                .setAuthor("TheTsundereAllen")
                                .setColor(Color.CYAN)
                                .setTitle(remind ? "You should complete this self study subject as soon as possible!" : "Information of this self study has shown below")
                                .addField("Name", this.title, true)
                                .addField("Content", this.content, true)
                                .addField("Wish Completion Date", DateUtil.formatDueDate(this.wishCompletionDate), false)
                                .addBlankField(true)
                                .addField("External Links", String.join("\n", this.externalUrls), false)
                                .build()
                ).queue();
    }

    public void save() {
        SSMongo.getInstance().addSelfStudy(this);
    }

    public void delete() {
        selfStudies.remove(this);
        SSMongo.getInstance().removeSelfStudy(this);
    }

    public static SelfStudy matchStudyByName(String title) {
        return selfStudies.stream().filter(study -> study.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }
}
