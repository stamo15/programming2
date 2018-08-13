/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;

import model.Swipe;
import model.VisitorSwipe;
import repositories.Repository;

/**
 *
 * @author Student
 */
public class DAOImpl implements DAOInterface{     

    @Override
    public Repository load(String filename) {
        char DELIMITER = ','; 
        Repository repository = new Repository();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) { 
            String line = br.readLine();
            while(line != null){
                String[] lineAsArray = line.split(Character.toString(DELIMITER));
                Swipe swipe;
                //Distinguish between Swipe and VisitorSwipe
                
                if(lineAsArray.length == 4){
                    int id = Integer.parseInt(lineAsArray[0]);
                    String cardId = stripQuotes(lineAsArray[1]);
                    String room = stripQuotes(lineAsArray[2]);

                    //Create a calendar object using the string
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(stripQuotes(lineAsArray[3]));
                    Calendar swipeDateTime = Calendar.getInstance();
                    swipeDateTime.setTime(date);
                    
                    swipe = new Swipe(id, cardId, room, swipeDateTime);
                } else if((lineAsArray.length > 4)) {
                    int id = Integer.parseInt(lineAsArray[0]);
                    String cardId = stripQuotes(lineAsArray[1]);
                    String room = stripQuotes(lineAsArray[2]);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(stripQuotes(lineAsArray[3]));
                    Calendar swipeDateTime = Calendar.getInstance();
                    swipeDateTime.setTime(date);
                    String visitorName = stripQuotes(lineAsArray[4]);
                    String visitorCompany = stripQuotes(lineAsArray[5]);
                    swipe = new VisitorSwipe(id, cardId, room, swipeDateTime,visitorName, visitorCompany);
                } else {
                    line = br.readLine();
                    continue;
                }
                
                //Add swipe to repository
                repository.add(swipe);                
                line = br.readLine();                
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }
        return repository;
    }
    
    @Override
    public void store(String filename, Repository repository){
        char DELIMITER = ','; 
        try (PrintWriter outputFile = new PrintWriter(filename)) {
            outputFile.print(repository.toString(DELIMITER));
            outputFile.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private String stripQuotes(String str){
        return str.substring(1, str.length()-1);
    }
}
