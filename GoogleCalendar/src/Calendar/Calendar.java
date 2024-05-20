package Calendar;

import exceptions.*;

import java.time.LocalDateTime;
import java.util.Iterator;

public interface Calendar {
    void addEvent(String email, String eventName, String eventPriority, LocalDateTime date, String topics) throws AccountNotFoundException, UnknownEventPriorityException,
            GuestAccountEventCreationException, StaffAccountEventCreationException,
            EventAlreadyExitsException, AccountIsBusyException;

    void removeEvent();

    void addAccount(String email, String accountType) throws DuplicateAccountException,
            UnknownAccountTypeException;

    void removeAccount();

    void respondInvitation();

    void invite();

    Iterator accountIterator();

}
