package enums;

public enum HelpMsg {
    REGISTER ("register - registers a new account"),
    ACCOUNTS ("accounts - lists all registered accounts"),
    CREATE ("create - creates a new event"),
    EVENTS ("events - list all events of an account"),
    INVITE ("invite - invites an user to an event"),
    RESPONSE ("response - response to an invitation"),
    EVENT ("event - shows detailed information of an event"),
    TOPICS ("topics - shows all events that cover a list of topics"),
    HELP ("help - shows the available commands"),
    EXIT ("exit - terminates the execution of the program");

    private String s;
    HelpMsg(String s){
        this.s = s;
    }

    public String getString(){
        return s;
    }


}
