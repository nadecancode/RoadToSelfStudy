package me.allen.rtss.command.complete;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class MarkCompleteCommand extends Command {

    public MarkCompleteCommand() {
        this.name = "mark";
        this.help = "Mark the assignment as completed";
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent commandEvent) {

    }
}
