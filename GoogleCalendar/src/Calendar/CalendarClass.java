package Calendar;

import enums.Accounts;
import enums.Priorities;
import exceptions.*;

import java.time.LocalDateTime;
import java.util.*;


public class CalendarClass implements Calendar {
    private SortedSet<Account> usersSorted;
    private List<Account> users;

    public CalendarClass(){
        usersSorted = new TreeSet<>();
        users = new ArrayList<>();
    }

    @Override
    public void addEvent(String email, String eventName, String eventPriority, LocalDateTime date, String topics) throws AccountNotFoundException,
            UnknownEventPriorityException, GuestAccountEventCreationException, StaffAccountEventCreationException,
            EventAlreadyExitsException, AccountIsBusyException {

        Account acc = new GuestClass(email);
        if (!usersSorted.contains(acc)){
            throw new AccountNotFoundException();
        }

        Account user = users.get(users.indexOf(acc));
        Priorities prio;
        try {
             prio = Priorities.valueOf(eventPriority);
        } catch (IllegalArgumentException e){
            throw new UnknownEventPriorityException();
        }

        if (user instanceof GuestClass){
            throw new GuestAccountEventCreationException();
        }

        if (user instanceof StaffClass && prio.equals(Priorities.HIGH)){
            throw new StaffAccountEventCreationException();
        }

        Event e = new EventClass(eventName, user, topics, prio, date);
        if (user.contains(e)){
            throw new EventAlreadyExitsException();
        }

        // Ver na lista de convidados dos eventos se existe um evento no qual este user já aceitou
        // throw new AccountIsBusyException

        user.addEvent(e);
    }

    @Override
    public void removeEvent() {

    }

    @Override
    public void addAccount(String email, String accountType) throws DuplicateAccountException, UnknownAccountTypeException {
        try {
            Accounts type = Accounts.valueOf(accountType.toUpperCase());
            Account acc = whichType(email, type);

            if (usersSorted.contains(acc)){
                throw new DuplicateAccountException();
            }
            usersSorted.add(acc);
            users.add(acc);
        } catch (IllegalArgumentException e){
            throw new UnknownAccountTypeException();
        }
    }

    private Account whichType(String email, Accounts type){
        Account acc = switch(type) {
            case GUEST -> new GuestClass(email);
            case STAFF -> new StaffClass(email);
            case MANAGER -> new ManagerClass(email);
        };
        return acc;
    }

    @Override
    public void removeAccount() {

    }

    @Override
    public void respondInvitation() {

    }

    @Override
    public void invite() {

    }

    @Override
    public Iterator accountIterator() {
        return usersSorted.iterator();
    }
}
