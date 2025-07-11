package exceptions;

public class AccountNotFoundException extends Exception {
    private String name;
    public AccountNotFoundException(){
        super();
    }

    public AccountNotFoundException(String name){
        super();
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
