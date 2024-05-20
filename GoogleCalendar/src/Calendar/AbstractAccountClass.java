package Calendar;


public abstract class AbstractAccountClass implements Account, Comparable<Account> {
    private String type;
    private String email;


    protected AbstractAccountClass(String email, String type){
        this.email = email;
        this.type = type;
    }

    @Override
    public boolean canCreateHighPrio() {
        return this instanceof ManagerClass;
    }

    @Override
    public boolean canCreateMidPrio(){
        return this instanceof StaffClass;
    }

    @Override
    public String getType() {
        return type;
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
        return false;
    }

    @Override
    public void addEvent(Event e) {
    }
}
