package me.allen.rtss.runnable;

import lombok.SneakyThrows;
import me.allen.rtss.objects.homework.Homework;

public class SSRunnable implements Runnable {

    @Override
    @SneakyThrows
    public void run() {
        Homework.getHomeworks()
                .forEach(homework -> {
                    if (homework.canNotify()) {

                    }
                });
    }

}
