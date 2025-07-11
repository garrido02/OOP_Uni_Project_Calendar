package enums;

public enum Responses {
    ALL,
    ACCEPT,
    REJECT,
    NO_ANSWER;

    public String toString(){
        return name().toLowerCase();
    }
}
