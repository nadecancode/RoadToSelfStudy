package me.allen.rtss.objects.selfstudy;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.allen.rtss.objects.reminder.Reminder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@RequiredArgsConstructor
public class SelfStudy {

    private static List<SelfStudy> selfStudies = new CopyOnWriteArrayList<>();

    private final String title, content;

    private List<String> externalUrls = new ArrayList<>();

    private final long assignDate;

    private long wishCompletionDate;

    public static Set<SelfStudy> getSelfStudies() {
        return new HashSet<>(selfStudies);
    }
}
