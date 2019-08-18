package me.allen.rtss.database;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.allen.rtss.SelfStudyBot;
import me.allen.rtss.objects.homework.Homework;
import me.allen.rtss.objects.read.ReadEveryday;
import me.allen.rtss.objects.reminder.Reminder;
import me.allen.rtss.objects.selfstudy.SelfStudy;
import org.mongojack.DBQuery;
import org.mongojack.JacksonMongoCollection;

@Getter
public class SSMongo {

    @Getter
    private static SSMongo instance;

    private MongoClient client;
    private MongoDatabase database;

    private JacksonMongoCollection<Homework> homeworks;
    private JacksonMongoCollection<ReadEveryday> readEveryday;
    private JacksonMongoCollection<Reminder> reminders;
    private JacksonMongoCollection<SelfStudy> selfStudies;

    public SSMongo() {
        this.client = new MongoClient(new ServerAddress("127.0.0.1", 27017));
        this.database = this.client.getDatabase("SelfStudy");

        this.homeworks = JacksonMongoCollection.<Homework>builder().build(this.database.getCollection("Homework"), Homework.class);
        this.readEveryday = JacksonMongoCollection.<ReadEveryday>builder().build(this.database.getCollection("Readings"), ReadEveryday.class);
        this.reminders = JacksonMongoCollection.<Reminder>builder().build(this.database.getCollection("Reminders"), Reminder.class);
        this.selfStudies = JacksonMongoCollection.<SelfStudy>builder().build(this.database.getCollection("Self-Studies"), SelfStudy.class);

        for (Homework homework : this.homeworks.find()) {
            homework.addToCache();
        }

        for (ReadEveryday read : this.readEveryday.find()) {
            read.addToCache();
        }

        for (SelfStudy selfStudy : this.selfStudies.find()) {
            selfStudy.addToCache();
        }

        for (Reminder reminder : this.reminders.find()) {
            reminder.addToCache();
        }
    }

    public boolean hasHomework(String title) {
        return this.homeworks.find(DBQuery.is("titleLower", title.toLowerCase())).first() != null;
    }

    public void addHomework(Homework homework) {
        this.homeworks.insert(homework);
    }

    public void removeHomework(Homework homework) {
        this.homeworks.findAndRemove(DBQuery.is("titleLower", homework.getTitle().toLowerCase()));
    }

    public boolean hasReading(String title) {
        return this.readEveryday.find(DBQuery.is("titleLower", title.toLowerCase())).first() != null;
    }

    public void addReading(ReadEveryday read) {
        this.readEveryday.insert(read);
    }

    public void removeReading(ReadEveryday read) {
        this.readEveryday.findAndRemove(DBQuery.is("titleLower", read.getTitle().toLowerCase()));
    }

    public boolean hasReminder(String uuid) {
        return this.reminders.find(DBQuery.is("uuid", uuid.toLowerCase())).first() != null;
    }

    public void addReminder(Reminder reminder) {
        this.reminders.insert(reminder);
    }

    public void removeReminder(Reminder reminder) {
        this.reminders.findAndRemove(DBQuery.is("uuid", reminder.getUuid().toLowerCase()));
    }

    public boolean hasSelfStudy(String title) {
        return this.selfStudies.find(DBQuery.is("titleLower", title.toLowerCase())).first() != null;
    }

    public void addSelfStudy(SelfStudy study) {
        this.selfStudies.insert(study);
    }

    public void removeSelfStudy(SelfStudy study) {
        this.selfStudies.findAndRemove(DBQuery.is("titleLower", study.getTitle().toLowerCase()));
    }

}

