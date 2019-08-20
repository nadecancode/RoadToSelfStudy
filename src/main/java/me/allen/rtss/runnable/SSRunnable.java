package me.allen.rtss.runnable;

import lombok.SneakyThrows;
import me.allen.rtss.objects.homework.Homework;
import me.allen.rtss.objects.read.ReadEveryday;
import me.allen.rtss.objects.reminder.Reminder;
import me.allen.rtss.objects.selfstudy.SelfStudy;

import java.util.concurrent.TimeUnit;

public class SSRunnable implements Runnable {

    @Override
    @SneakyThrows
    public void run() {
        Homework.getHomeworks()
                .forEach(homework -> {
                    if (!homework.isFinished()) {
                        if (homework.canNotify()) {
                            if (homework.getNotifyLaterWhenNotCompleted() != -1L) {
                                homework.notifyWithBot(true);
                                homework.setNotifyLaterWhenNotCompleted(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10));
                            } else {
                                homework.notifyWithBot(true);
                                homework.setNotified(true);
                                homework.setNotifyLaterWhenNotCompleted(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30));
                            }
                        }
                    } else {
                        homework.delete();
                    }
                });

        SelfStudy.getSelfStudies()
                .forEach(study -> {
                    if (study.canNotify()) {
                        study.notifyWithBot(true);
                    }
                });

        ReadEveryday.getReadings()
                .forEach(reading -> {
                    if (reading.canNotify()){
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
