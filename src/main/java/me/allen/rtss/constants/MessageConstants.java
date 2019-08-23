package me.allen.rtss.constants;

import me.allen.rtss.objects.selfstudy.SelfStudy;
import me.allen.rtss.type.StudyType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageConstants {

    public static final MessageEmbed NO_STUDY_TYPE_FOUND = new EmbedBuilder()
            .setTitle("Study type not found")
            .setColor(Color.RED)
            .setDescription("**Your study type input is not defined. Available types are: " + String.join(", ", Stream.of(StudyType.values()).map(StudyType::getReadable).collect(Collectors.toSet())))
            .build();

    public static final MessageEmbed HOMEWORK_NOT_FOUND = new EmbedBuilder()
            .setTitle("Homework not found")
            .setColor(Color.RED)
            .setDescription("**Your specified homework is not defined in database, please check your name**")
            .build();

    public static final MessageEmbed SELF_STUDY_NOT_FOUND = new EmbedBuilder()
            .setTitle("Self study not found")
            .setColor(Color.RED)
            .setDescription("**Your specified self study is not defined in database, please check your name**")
            .build();

    public static final MessageEmbed READING_NOT_FOUND = new EmbedBuilder()
            .setTitle("Self study not found")
            .setColor(Color.RED)
            .setDescription("**Your specified reading subject is not defined in database, please check your name**")
            .build();

    public static final MessageEmbed NO_ARGUMENT_PROVIDED = new EmbedBuilder()
            .setTitle("Argument not found")
            .setColor(Color.RED)
            .setDescription("**You have to provide a valid argument**")
            .build();

    public static final MessageEmbed DUPLICATED_OBJECTS = new EmbedBuilder()
            .setTitle("Duplicated Objects")
            .setColor(Color.RED)
            .setDescription("**Objects are duplicated, maybe by the title?**")
            .build();

    public static final MessageEmbed INVALID_DURATION = new EmbedBuilder()
            .setTitle("Invalid Due Date")
            .setColor(Color.RED)
            .setDescription("**Please provide a valid duration for the assignment**")
            .build();

}
