import enums.*;
import Calendar.*;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    private static final String EXIT = "exit";
    private static final String BYE = "Bye!";


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        startApp(in);
    }


    /**
     * DONE
     * @param in
     */
    private static void startApp(Scanner in){
        String command;
        Calendar calendar = new CalendarClass();
        do {
            StringTokenizer tokens = new StringTokenizer(in.nextLine());
            command = tokens.nextToken().toUpperCase();
            switch (Commands.valueOf(command)){
                case REGISTER -> processRegister(calendar, tokens);
                case ACCOUNTS -> processAccounts(calendar, tokens);
                case CREATE -> processCreate(calendar, tokens);
                case EVENTS -> processListEvents(calendar, tokens);
                case INVITE -> processInvite(calendar, tokens);
                case RESPONSE -> processResponse(calendar, tokens);
                case EVENT -> processSingleEvent(calendar, tokens);
                case TOPICS -> processTopics(calendar, tokens);
                case HELP -> processHelp();
                case EXIT -> processExit();
            }
        } while (!Commands.valueOf(command).equals(EXIT));
    }


    private static void processRegister(Calendar calendar, StringTokenizer tokens){

    }



    private static void processAccounts(Calendar calendar, StringTokenizer tokens){

    }



    private static void processCreate(Calendar calendar, StringTokenizer tokens){

    }



    private static void processListEvents(Calendar calendar, StringTokenizer tokens){

    }



    private static void processInvite(Calendar calendar, StringTokenizer tokens){

    }



    private static void processResponse(Calendar calendar, StringTokenizer tokens){

    }



    private static void processSingleEvent(Calendar calendar, StringTokenizer tokens){

    }



    private static void processTopics(Calendar calendar, StringTokenizer tokens){

    }


    /**
     * DONE
     */
    private static void processHelp(){
        System.out.println("Available commands:");
        for (HelpMsg s: HelpMsg.values()){
            System.out.println(s.getString());
        }
    }


    /**
     * DONE
     */
    private static void processExit(){
        System.out.println(BYE);
    }
}