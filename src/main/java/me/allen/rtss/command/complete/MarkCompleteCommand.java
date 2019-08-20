package me.allen.rtss.command.complete;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.allen.rtss.constants.MessageConstants;
import me.allen.rtss.objects.homework.Homework;
import me.allen.rtss.objects.read.ReadEveryday;
import me.allen.rtss.objects.selfstudy.SelfStudy;
import me.allen.rtss.type.StudyType;
import me.allen.rtss.util.CommandUtil;
import me.allen.rtss.util.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class MarkCompleteCommand extends Command {

    public MarkCompleteCommand() {
        this.name = "mark";
        this.help = "Mark the assignment as completed";
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        if (!CommandUtil.preprocessCommand(commandEvent)) return;

        String[] args = commandEvent.getArgs().split("\\s+");

        if (args.length < 2) {
            commandEvent.reply(
                    new EmbedBuilder()
                    .setColor(Color.RED)
                    .setAuthor("RoadToSelfStudy")
                    .setTitle("Invalid Argument")
                    .setDescription("You have to provide a name / title to the assignment that you wish to mark as completed.")
                    .build()
            );
            return;
        }

        StudyType studyType = StudyType.matchType(args[0]);

        String name = args[1];

        if (studyType == StudyType.REMINDER) {
            commandEvent.reply(
                    new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle("Invalid study type")
                    .setDescription("Reminder does not support for mark as completion")
                    .build()
            );
            return;
        }

        if (studyType == StudyType.HOMEWORK) {
            Homework matchHomework = Homework.matchHomeworkByName(name);
            if (matchHomework == null) {
                commandEvent.reply(MessageConstants.HOMEWORK_NOT_FOUND);
                return;
            }

            if (matchHomework.isFinished()) {
                commandEvent.reply(
                        new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Homework was already completed")
                        .setDescription("Wait hold on.. Your specified homework was already completed :D")
                        .build()
                );

                return;
            }

            matchHomework.setFinished(true);
            commandEvent.reply(
                    new EmbedBuilder()
                    .setColor(Color.CYAN)
                    .setTitle("You finished your specified homework!")
                    .setDescription("You just finished your homework (Marked as finished as least..)")
                    .addField("Name", matchHomework.getTitle(), true)
                    .addField("Subject", matchHomework.getSubject(), true)
                    .addField("Content", matchHomework.getContent(), true)
                    .build()
            );
        } else if (studyType == StudyType.SELF_STUDY) {
            SelfStudy matchStudy = SelfStudy.matchStudyByName(name);
            if (matchStudy == null) {
                commandEvent.reply(MessageConstants.SELF_STUDY_NOT_FOUND);
                return;
            }

            if (matchStudy.isCompleted()) {
                commandEvent.reply(
                        new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle("Self study was already completed")
                                .setDescription("Wait hold on.. Your specified self study was already completed :D")
                                .build()
                );

                return;
            }

            matchStudy.setCompleted(true);
            commandEvent.reply(
                    new EmbedBuilder()
                            .setColor(Color.CYAN)
                            .setTitle("You finished your specified self study!")
                            .setDescription("You just finished your self study (Marked as finished as least..)")
                            .addField("Name", matchStudy.getTitle(), true)
                            .addField("Content", matchStudy.getContent(), true)
                            .build()
            );
        } else if (studyType == StudyType.READ_EVERYDAY) {
            ReadEveryday matchedReading = ReadEveryday.matchReadByName(name);

            if (matchedReading == null) {
                commandEvent.reply(MessageConstants.READING_NOT_FOUND);
                return;
            }

            if (matchedReading.isCompleted()) {
                commandEvent.reply(
                        new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle("Read was already completed")
                                .setDescription("Wait hold on.. Your specified reading was already completed :D")
                                .build()
                );

                return;
            }

            matchedReading.setCompleted(true);
            commandEvent.reply(
                    new EmbedBuilder()
                            .setColor(Color.CYAN)
                            .setTitle("You finished your specified self study!")
                            .setDescription("You just finished your self study (Marked as finished as least..)")
                            .addField("Name", matchedReading.getTitle(), true)
                            .addField("Content", matchedReading.getContent(), true)
                            .addField("Duration", TimeUtil.millisToRoundedTime(matchedReading.getReadDuration()), true)
                            .build()
            );
        }
    }
}
