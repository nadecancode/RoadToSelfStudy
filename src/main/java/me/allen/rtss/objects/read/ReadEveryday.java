package me.allen.rtss.objects.read;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@Data
@RequiredArgsConstructor
public class ReadEveryday {

    private static List<ReadEveryday> reads = new CopyOnWriteArrayList<>();

    private final String title, content;

    private List<String> externalUrls = new ArrayList<>();

    private long readDuration = TimeUnit.MINUTES.toMillis(30); //Normally 30 Minutes Reading

    public static HashSet<ReadEveryday> getReadings() {
        return new HashSet<>(reads);
    }

}
