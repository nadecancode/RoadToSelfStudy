package me.allen.rtss.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum StudyType {

    HOMEWORK("homework", "hw"),
    SELF_STUDY("self-study", "ss"),
    READ_EVERYDAY("read-everyday", "read"),
    REMINDER("reminder", "rm");

    private final String readable, alternative;

    public static StudyType matchType(String input) {
        return Stream.of(StudyType.values())
                .filter(studyType -> studyType.getReadable().toLowerCase().contains(input.toLowerCase()) || studyType.getAlternative().toLowerCase().contains(input.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}
