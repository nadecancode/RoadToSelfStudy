package me.allen.rtss.command.notify;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.allen.rtss.constants.MessageConstants;
import me.allen.rtss.objects.homework.Homework;
import me.allen.rtss.objects.read.ReadEveryday;
import me.allen.rtss.objects.reminder.Reminder;
import me.allen.rtss.objects.selfstudy.SelfStudy;
import me.allen.rtss.type.StudyType;
import me.allen.rtss.util.CommandUtil;
import me.allen.rtss.util.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class NotifyCommand extends Command {

    public NotifyCommand() {
        this.name = "notify";
        this.help = "Notify the type of assignments that I have do";
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        if (!CommandUtil.preprocessCommand(commandEvent)) return;

        StudyType studyType = StudyType.matchType(commandEvent.getArgs().split("//s+")[0]);
        EmbedBuilder embedBuilder = new EmbedBuilder();

        if (studyType == StudyType.HOMEWORK) {
            embedBuilder.setTitle("Homework Information");
            embedBuilder.setDescription("Here is your homework which are not due / completed");

            List<Homework> filteredHomework = Homework.getHomeworks()
                    .stream()
                    .filter(homework -> !homework.isDue() || !homework.isFinished())
                    .sorted(Comparator.comparing(Homework::getSubject))
                    .collect(Collectors.toList());

                    filteredHomework.forEach(homework -> embedBuilder.addField("**" + (filteredHomework.indexOf(homework) + 1) + ".)** " + homework.getTitle() + " (" + homework.getSubject() + ")", homework.getContent(), false));
        } else if (studyType == StudyType.READ_EVERYDAY) {
            embedBuilder.setTitle("Read Everyday");
            embedBuilder.setDescription("Here are your readings which you need to complete everyday");

            List<ReadEveryday> filteredReadings = ReadEveryday.getReadings()
                    .stream()
                    .filter(reading -> !reading.isCompleted())
                    .sorted(Comparator.comparing(ReadEveryday::getTitle))
                    .collect(Collectors.toList());

            filteredReadings.forEach(reading -> embedBuilder.addField("**" + (filteredReadings.indexOf(reading) + 1) + ".)** " + reading.getTitle(), reading.getContent() + " *(" + TimeUtil.millisToRoundedTime(reading.getReadDuration()) + ")*", false));
        } else if (studyType == StudyType.SELF_STUDY) {
            embedBuilder.setTitle("Self Study");
            embedBuilder.setDescription("Here is your self study, please study!");

            List<SelfStudy> filteredStudies = SelfStudy.getSelfStudies()
                    .stream()
                    .filter(study -> !study.isCompleted())
                    .sorted(Comparator.comparing(SelfStudy::getTitle))
                    .collect(Collectors.toList());

            filteredStudies.forEach(study -> embedBuilder.addField("**" + (filteredStudies.indexOf(study) + 1) + ".)** " + study.getTitle(), study.getContent(), false));
        } else if (studyType == StudyType.REMINDER) {
            embedBuilder.setTitle("Reminders");
            embedBuilder.setDescription("Here are your reminders which you need to remember as events!");

            List<Reminder> filteredReminders = Reminder.getReminders()
                    .stream()
                    .filter(reminder -> !reminder.canRemind())
                    .sorted(Comparator.comparing(Reminder::getTime))
                    .collect(Collectors.toList());

            filteredReminders.forEach(reminder -> embedBuilder.addField("**" + (filteredReminders.indexOf(reminder) + 1) + ".)** " + TimeUtil.dateToString(new Date(reminder.getTime()), ""), reminder.getContent(), false));
        }



        commandEvent.reply(embedBuilder.build());
    }
}
