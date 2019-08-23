package me.allen.rtss.command.create;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.allen.rtss.constants.MessageConstants;
import me.allen.rtss.objects.homework.Homework;
import me.allen.rtss.type.StudyType;
import me.allen.rtss.util.CommandUtil;
import me.allen.rtss.util.DateUtil;
import me.allen.rtss.util.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class CreateCommand extends Command {

    @Override
    protected void execute(CommandEvent commandEvent) {
        if (!CommandUtil.preprocessCommand(commandEvent)) return;

        String[] args = commandEvent.getArgs().split("\\s+");

        if (args.length < 2) {
            commandEvent.reply(MessageConstants.NO_ARGUMENT_PROVIDED);
            return;
        }

        StudyType studyType = StudyType.matchType(args[0]);
        if (studyType == StudyType.HOMEWORK) {
            if (args.length < 5) {
                commandEvent.reply(MessageConstants.NO_ARGUMENT_PROVIDED);
                return;
            }

            String name = args[2];

            if (Homework.matchHomeworkByName(name) != null) {
                commandEvent.reply(MessageConstants.DUPLICATED_OBJECTS);
                return;
            }

            String subject = args[3];
            String content = args[4];
            String duration = args[5];

            long dueDate = TimeUtil.parseTime(duration);

            if (dueDate == -1L) {
                commandEvent.reply(MessageConstants.INVALID_DURATION);
                return;
            }

            Homework homework = new Homework(name, subject, content, System.currentTimeMillis(), dueDate);
            homework.save();
        }
    }

}
