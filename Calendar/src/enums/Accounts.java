package enums;

public enum Accounts {
    STAFF,
    MANAGER,
    GUEST;

    public String toString(){
        return name().toLowerCase();
    }
}
