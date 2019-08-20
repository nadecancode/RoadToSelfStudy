package me.allen.rtss.util;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.allen.rtss.constants.MessageConstants;
import me.allen.rtss.type.StudyType;

public class CommandUtil {

    public static boolean preprocessCommand(CommandEvent commandEvent) {
        if (commandEvent.getArgs().isEmpty()) {
            commandEvent.reply(MessageConstants.NO_STUDY_TYPE_FOUND);
            return false;
        }

        String type = commandEvent.getArgs().split("\\s+")[0];
        StudyType studyType = StudyType.matchType(type);

        if (studyType == null) {
            commandEvent.reply(MessageConstants.NO_STUDY_TYPE_FOUND);
            return false;
        }

        return true;
    }

}
