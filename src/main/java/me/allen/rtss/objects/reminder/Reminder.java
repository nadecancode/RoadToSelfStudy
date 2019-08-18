package me.allen.rtss.objects.reminder;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
@Data
public class Reminder {

    private static List<Reminder> reminders = new CopyOnWriteArrayList<>();

    private final String content;

    private final long time;

    public boolean canRemind() {
        return System.currentTimeMillis() >= this.time;
    }

    public static Set<Reminder> getReminders() {
        return new HashSet<>(reminders);
    }
}
