package me.allen.rtss.runnable;

import lombok.SneakyThrows;
import me.allen.rtss.objects.homework.Homework;
import me.allen.rtss.objects.read.ReadEveryday;
import me.allen.rtss.objects.reminder.Reminder;
import me.allen.rtss.objects.selfstudy.SelfStudy;

public class SSRunnable implements Runnable {

    @Override
    @SneakyThrows
    public void run() {
        Homework.getHomeworks()
                .forEach(homework -> {
                    if (homework.canNotify()) {
                        homework.notifyWithBot(true);
                    }

                    if (homework.isFinished()) {
                        homework.delete();
                    }
                });

        SelfStudy.getSelfStudies()
                .forEach(study -> {
                    if (study.canNotify()) {
                        study.notifyWithBot(true);
                    } else if (study.isCompleted()) {
                        study.delete();
                    }
                });

        ReadEveryday.getReadings()
                .forEach(reading -> {
                    if (reading.isCompleted()) {
                        reading.delete();
                    } else if (reading.canNotify()){
                        reading.notifyWithBot(true);
                    }
                });

        Reminder.getReminders()
                .forEach(reminder -> {
                    if (reminder.canRemind()) {
                        reminder.notifyWithBot(true);
                        reminder.delete();
                    }
                });
    }

}
