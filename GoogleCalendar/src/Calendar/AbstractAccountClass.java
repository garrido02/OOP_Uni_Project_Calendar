package Calendar;

public abstract class AbstractAccountClass implements Account {
    private String type;

    public AbstractAccountClass(String type){
        this.type = type;
    }

    public boolean canCreateHighPrio() {
        return this instanceof ManagerClass;
    }
    public boolean canCreateMidPrio(){
        return this instanceof StaffClass;
    }

    @Override
    public String getType() {
        return type;
    }
}
