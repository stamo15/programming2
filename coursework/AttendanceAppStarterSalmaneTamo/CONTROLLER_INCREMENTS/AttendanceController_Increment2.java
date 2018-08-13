package controllers;

import java.util.Calendar;

import helpers.InputHelper;
import model.Swipe;
import model.VisitorSwipe;
import repositories.Repository;


/**
 *
 * @author mga
 */
public class AttendanceController {
    private final Repository repository;
    
    /**
     *
     */
        
    public AttendanceController() {
        InputHelper inputHelper = new InputHelper();
        char c = inputHelper.readCharacter("Load an already existing Swipes File (Y/N)?");
        if (c == 'Y' || c == 'y') {
            String fileName = inputHelper.readString("Enter filename");               
            this.repository = new Repository(fileName);
        }
        else {
            this.repository = new Repository();
        }
    }
   
    /**
     *
     */
    public void run() {
        boolean finished = false;
        
        do {
            char choice = displayAttendanceMenu();
            switch (choice) {
                case 'A': 
                    addSwipe();
                    break;
                case 'B':  
                    listSwipes();
                    break;
                case 'C': 
                    listSwipesByCardIdOrderedByDateTime(); // 
                    break;                    
                case 'D': 
                    listSwipeStatistics(); //
                    break;
                case 'Q': 
                    finished = true;
            }
        } while (!finished);
    }
    
    private char displayAttendanceMenu() {
        InputHelper inputHelper = new InputHelper();
        System.out.print("\nA. Add Swipe");
        System.out.print("\tB. List Swipes");        
        System.out.print("\tC. List Swipes In Date Time Order");
        System.out.print("\tD. List Swipes Which Match Card Id");       
        System.out.print("\tQ. Quit\n");         
        return inputHelper.readCharacter("Enter choice", "ABCDQ");
    }    
    
    private void addSwipe() {
        System.out.format("\033[31m%s\033[0m%n", "Add Swipe");
        System.out.format("\033[31m%s\033[0m%n", "=========");
        
        InputHelper inputHelper = new InputHelper();
        char c = inputHelper.readCharacter("Is it a Visitor Swipe (Y/N)?");
        Swipe newSwipe;
        if (c == 'Y' || c == 'y') {
            int id = inputHelper.readInt("Enter Swipe id");
            String cardId = inputHelper.readString("Enter card id");
            String room = inputHelper.readString("Enter room");
            String visitorName = inputHelper.readString("Enter Visitor name");
            String visitorCompany = inputHelper.readString("Enter Visitor company");
            Calendar swipeDateTime = Calendar.getInstance();
            newSwipe = new VisitorSwipe(id, cardId, room, swipeDateTime,visitorName,visitorCompany);
        } else {
            int id = inputHelper.readInt("Enter Swipe id");
            String cardId = inputHelper.readString("Enter card id");
            String room = inputHelper.readString("Enter room");
            Calendar swipeDateTime = Calendar.getInstance();
            newSwipe = new Swipe(id, cardId, room, swipeDateTime);
        }
        
        
        for(Swipe swipe : repository.getItems()){
            if (swipe.getId() == newSwipe.getId()){
                System.out.println("Swipe already exists");
                return;
            }
        }
        repository.add(newSwipe);
    }
    
    private void listSwipes() {        
        System.out.format("\033[31m%s\033[0m%n", "Swipes");
        System.out.format("\033[31m%s\033[0m%n", "======");
        for(Swipe swipe : this.repository.getItems()){
            System.out.println(swipe);
        }
    }      
      

    private void listSwipesByCardIdOrderedByDateTime() {        
        System.out.format("\033[31m%s\033[0m%n", "Swipes By Card Id");
        System.out.format("\033[31m%s\033[0m%n", "=================");
    }    
    
    private void listSwipeStatistics() {
        System.out.format("\033[31m%s\033[0m%n", "Swipe Statistics for room");
        System.out.format("\033[31m%s\033[0m%n", "========================="); 
    }
}
