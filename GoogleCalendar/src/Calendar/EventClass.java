package Calendar;

import java.time.LocalDateTime;

public class EventClass implements Event {
    private String name;
    private String topic;
    private Account creator;
    private String priorityLevel;
    private LocalDateTime date;

    public EventClass(String name, Account creator, String topic, String priorityLevel, LocalDateTime date){
        this.name = name;
        this.creator = creator;
        this.topic = topic;
        this.priorityLevel = priorityLevel;
        this.date = date;
    }

    @Override
    public String getPriorityLevel() {
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
