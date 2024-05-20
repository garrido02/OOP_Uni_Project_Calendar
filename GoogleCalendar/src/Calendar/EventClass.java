package Calendar;

import enums.Priorities;

import java.time.LocalDateTime;

public class EventClass implements Event {
    private String name;
    private String topic;
    private Account creator;
    private Priorities priorityLevel;
    private LocalDateTime date;

    public EventClass(String name, Account creator, String topic, Priorities priorityLevel, LocalDateTime date){
        this.name = name;
        this.creator = creator;
        this.topic = topic;
        this.priorityLevel = priorityLevel;
        this.date = date;
    }

    @Override
    public Priorities getPriorityLevel() {
        return priorityLevel;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public Account getCreator() {
        return creator;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }
}
