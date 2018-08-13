package controllers;

import java.util.Calendar;
import java.util.ArrayList;

import helpers.InputHelper;
import java.util.Collections;
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
            
            String dateFormat = "yyyy/MM/dd HH:mm:ss";
            Calendar swipeDateTime = inputHelper.readDate("Enter the date of the swipe using the following format " + dateFormat +" ", dateFormat);
            
            newSwipe = new VisitorSwipe(id, cardId, room, swipeDateTime,visitorName,visitorCompany);
        } else {
            int id = inputHelper.readInt("Enter Swipe id");
            String cardId = inputHelper.readString("Enter card id");
            String room = inputHelper.readString("Enter room");
            
            String dateFormat = "yyyy/MM/dd HH:mm:ss";
            Calendar swipeDateTime = inputHelper.readDate("Enter the date of the swipe using the following format " + dateFormat , dateFormat);
            
            newSwipe = new Swipe(id, cardId, room, swipeDateTime);
        }
        
        //Check if swipe already exists
        for(Swipe swipe : repository.getItems()){
            if (swipe.getId() == newSwipe.getId()){
                System.out.println("Swipe already exists");
                return;
            }
        }
        repository.add(newSwipe);
        char ch = inputHelper.readCharacter("Do you want to save this swipe (Y/N)?");
        if (ch == 'Y' || ch == 'y'){         
            String filename = inputHelper.readString("Enter File name");
            this.repository.store(filename);
            System.out.println("New swipe and existing swipes are stored in: "+filename);
        }        
        
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
        InputHelper inputHelper = new InputHelper();
        String cardId = inputHelper.readString("Enter the card Id:");
        
        ArrayList<Swipe> listOfswipes = new ArrayList<>();
        
        //Only add swipes which match card id
        for(Swipe swipe : this.repository.getItems()){
            if(swipe.getCardId().equals(cardId)){
                listOfswipes.add(swipe);
            }
        }
        if(listOfswipes.isEmpty()){
            System.out.println("Card id does not exist");
            return;
        }
        Collections.sort(listOfswipes);
        for(Swipe swipe : listOfswipes){
            System.out.println(swipe);
        }
    }    
    
    private void listSwipeStatistics() {
        System.out.format("\033[31m%s\033[0m%n", "Swipe Statistics for room");
        System.out.format("\033[31m%s\033[0m%n", "========================="); 
        
        InputHelper inputHelper = new InputHelper();
        String room = inputHelper.readString("Enter the room:");
        
        ArrayList<Swipe> listOfswipes = new ArrayList<>();
        //Only add swipes which match room
        for(Swipe swipe : this.repository.getItems()){
            if(swipe.getRoom().equals(room)){
                listOfswipes.add(swipe);
            }
        }
        if(listOfswipes.isEmpty()){
            System.out.println("No existing swipe matches that room");
            return;
        }
        Collections.sort(listOfswipes);
        System.out.println("Number of swipes for room "+room+": " +listOfswipes.size());
        System.out.println("Date-time of last swipe: " + Swipe.formatSwipeDateTime(listOfswipes.get(0).getSwipeDateTime()));
    }
}
