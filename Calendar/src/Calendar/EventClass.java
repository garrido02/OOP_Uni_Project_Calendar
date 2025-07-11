/**
 * Event Class
 * Authors: Francisco Correia 67264 && Sérgio Garrido 67202
 */

package Calendar;

import enums.Priorities;
import enums.Responses;

import java.time.LocalDateTime;
import java.util.*;

/**
 * EventClass that implements the methods of the Event interface
 */
public class EventClass implements Event{
    /**
     * Instance variables
     */
    private static final int NULL = 0;
    private static final String BLANK_SPACE = " ";
    private String name;
    private int commonTopics;
    private List<String> topics;
    private Account creator;
    private Priorities priorityLevel;
    private LocalDateTime date;
    private List<Invite> invites;

    /**
     * Constructor
     * @param name - The name of the event
     * @param creator - The user who created the event
     * @param topic - The topics of the event
     * @param priorityLevel - The priority level of the event
     * @param date - The date of the event
     */
    public EventClass(String name, Account creator, String topic, Priorities priorityLevel, LocalDateTime date){
        this.name = name;
        this.creator = creator;
        this.priorityLevel = priorityLevel;
        this.date = date;
        this.invites = new ArrayList<>();
        this.commonTopics = NULL;
        addTopics(topic);
    }

    /**
     * Adds a list of topics to the event topics
     * @param topic
     */
    private void addTopics(String topic){
        StringTokenizer tokens = new StringTokenizer(topic);
        this.topics = new ArrayList<>(tokens.countTokens());
        while (tokens.hasMoreTokens()){
            this.topics.add(tokens.nextToken());
        }
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
    public String getTopics() {
        String topics = "";
        Iterator<String> topicsIterator = topicsIterator();
        while (topicsIterator.hasNext()){
            topics += topicsIterator.next() + BLANK_SPACE;
        }
        return topics.trim();
    }

    @Override
    public int compareByTopics(Event e2) {
        if (this.commonTopics == e2.getCommonTopics()){
            return compareAlphabetically(e2);
        } else if (this.commonTopics < e2.getCommonTopics()){
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Compares two events alphabetically. Firstly by event name. Afterward, by creator name
     * @param e2 - The event to be compared
     * @return 1 if this event is higher alphabetically than e2. -1 if this event is lower alphabetically than e2.
     * Otherwise, 0
     */
    private int compareAlphabetically(Event e2) {
        int nameCompare = this.getName().compareTo(e2.getName());
        if (nameCompare == NULL){
            return this.getCreatorEmail().compareTo(e2.getCreatorEmail());
        } else {
            return nameCompare;
        }
    }


    @Override
    public void commonTopics(String[] t) {
        int common = 0;
        for (int i = 0; i < topics.size(); i++){
            for (int j = 0; j < t.length; j++){
                if (topics.get(i).equals(t[j])){
                    common++;
                }
            }
        }
        this.commonTopics = common;
    }

    @Override
    public int getCommonTopics() {
        return commonTopics;
    }

    @Override
    public Account getCreator() {
        return creator;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public void invite(Account user) {
        Invite inv = new InviteClass(user);
        invites.add(inv);
        user.addEvent(this);
    }

    @Override
    public void setResponse(Account user, Responses response) {
        Invite i = new InviteClass(user);
        invites.get(invites.indexOf(i)).setResponse(response);
    }

    @Override
    public Iterator<Invite> inviteIterator() {
        return invites.iterator();
    }

    /**
     * Returns an iterator of topics
     * @return an iterator of topics
     */
    private Iterator<String> topicsIterator() {
        return topics.iterator();
    }

    @Override
    public int getInvitationsNr(String type) {
        Responses response = Responses.valueOf(type);
        int result = 0;
        Iterator<Invite> inviteIterator = inviteIterator();
        while (inviteIterator.hasNext()){
            Invite i = inviteIterator.next();
            if (response == Responses.ALL){
                result++;
            } else if (i.getResponse().equals(response)){
                result++;
            }
        }
        return result;
    }

    @Override
    public boolean isUserInvited(Account invitee) {
        boolean result = false;
        Iterator<Invite> ite = invites.listIterator();
        while (ite.hasNext() && !result) {
            Invite i = ite.next();
            if (i.getInvitee().equals(invitee)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean userAlreadyReplied(Account invitee) {
        boolean result = false;
        Iterator<Invite> ite = invites.listIterator();
        while (ite.hasNext() && !result) {
            Invite i = ite.next();
            if (i.getInvitee().equals(invitee) && !i.getResponse().equals(Responses.NO_ANSWER)) {
                result = true;
            }
        }
        return result;
    }
    
    @Override
    public boolean hasUserRejected(Account invitee) {
        boolean result = false;
        Iterator<Invite> ite = invites.listIterator();
        while (ite.hasNext() && !result) {
            Invite i = ite.next();
            if (i.getInvitee().equals(invitee) && i.getResponse().equals(Responses.REJECT)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public String getCreatorEmail() {
        return this.creator.getEmail();
    }

}
