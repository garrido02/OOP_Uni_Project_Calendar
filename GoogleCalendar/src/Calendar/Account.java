package Calendar;

public interface Account {
    String getType();
    boolean canCreateHighPrio();
    boolean canCreateMidPrio();
    String getEmail();
    int compareTo(Account other);
    boolean contains(Event e);
    void addEvent(Event e);
}
