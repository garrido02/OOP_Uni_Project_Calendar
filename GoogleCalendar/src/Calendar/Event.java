package Calendar;

import enums.Priorities;

import java.time.LocalDateTime;

public interface Event {
    Priorities getPriorityLevel();
    String getName();
    String getTopic();
    Account getCreator();
    LocalDateTime getDate();
}
