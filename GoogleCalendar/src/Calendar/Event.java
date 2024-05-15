package Calendar;

import java.time.LocalDateTime;

public interface Event {
    String getPriorityLevel();
    String getName();
    String getTopic();
    Account getCreator();
    LocalDateTime getDate();
}
