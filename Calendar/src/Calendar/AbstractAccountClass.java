/**
 * Abstract Account Class
 * Authors: Francisco Correia 67264 && Sérgio Garrido 67202
 */

package Calendar;


import enums.Accounts;
import enums.Priorities;
import enums.Responses;

import java.util.*;

public abstract class AbstractAccountClass implements Account, Comparable<Account> {
    private boolean isAttendingHighPrioEvent;
    private Accounts type;
    private String email;
    private Map<Account, List<Event>> events;
    private List<Event> rejectedEvents;
    private List<Event> invitedEvents;

    //Constructor
    protected AbstractAccountClass(String email, String type){
        this.email = email;
        this.type = Accounts.valueOf(type.toUpperCase());
        this.events = new HashMap<>();
        this.rejectedEvents = new ArrayList<>();
        this.invitedEvents = new ArrayList<>();

        this.isAttendingHighPrioEvent = false;
        
    }

    @Override
    public Accounts getType() {
        return type;
    }

    @Override
    public void addRejectedEvent(Event e) {
        rejectedEvents.add(e);
    }

    @Override
    public Iterator<Event> rejectEventsIterator() {
        Iterator<Event> ite = rejectedEvents.iterator();
        return ite;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public int compareTo(Account other) {
        return this.email.compareTo(other.getEmail());
    }

    @Override
    public boolean contains(Event e) {
            ListIterator<Event> ite = invitedEvents.listIterator();
            while (ite.hasNext()){
                Event ev = ite.next();
                if (ev.getName().equals(e.getName())) {
                    return true;
                }
            }
        return false;
    }

    @Override
    public void addEvent(Event e) {
        if (e.getPriorityLevel().equals(Priorities.HIGH)){
            isAttendingHighPrioEvent = true;
        }
        List<Event> currentEvents = events.get(e.getCreator());
        if (currentEvents == null){
            currentEvents = new ArrayList<>();
            currentEvents.add(e);

        } else {
            currentEvents.add(e);
        }
        invitedEvents.add(e);
        events.put(e.getCreator(), currentEvents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        AbstractAccountClass that = (AbstractAccountClass) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public boolean isBusy(Event e) {
        for (List<Event> event: events.values()){
            Iterator<Event> ite = event.iterator();
            while (ite.hasNext()){
                Event ev = ite.next();
                Iterator<Invite> invIterator = ev.inviteIterator();
                while (invIterator.hasNext()){
                    Invite invite = invIterator.next();
                    if (checkIfDatesCollide(e, ev, invite)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkIfDatesCollide(Event e1, Event e2, Invite i){
        return e1.getDate().equals(e2.getDate()) && i.getInvitee().equals(this) && i.getResponse().equals(Responses.ACCEPT);
    }

    @Override
    public Iterator<Event> eventIterator() {
        return invitedEvents.iterator();
    }

    @Override
    public void invite(Account inviteeAcc, Event event) {
        event.invite(inviteeAcc);
    }

    @Override
    public Event getEvent(String eventName) {
        Event result = null;
        Iterator<Event> iterator = eventIterator();
        while (iterator.hasNext() && result == null){
            Event e = iterator.next();
            if (e.getName().equals(eventName)){
                result = e;
            }
        }
        return result;
    }

    @Override
    public void removeEvent(Event e) {
        if (e.getPriorityLevel().equals(Priorities.HIGH)){
            isAttendingHighPrioEvent = false;
        }
        Account creator = e.getCreator();
        List<Event> list = events.get(creator);
        list.remove(e);
        invitedEvents.remove(e);
    }

    @Override
    public void respond(Event e, Responses response) {
        e.setResponse(this, response);
    }

    @Override
    public void clearRejectedEvents() {
        rejectedEvents.clear();;
    }

    @Override
    public boolean isAttendingHighPrioEvent() {
        return isAttendingHighPrioEvent;
    }
}
