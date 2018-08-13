package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

/**
 *
 * @author mga
 */
public class Swipe implements Comparable<Swipe>{

    protected int id;

    protected String cardId;

    protected String room;

    protected Calendar swipeDateTime;
    
    private static int lastSwipeIdUsed = 0;
    private static final char EOLN='\n';       
    private static final String QUOTE="\"";
    
    public static Comparator<Swipe>  SwipeDateTimeComparator = (Swipe swipe1, Swipe swipe2) -> {
        Calendar swipeDateTime1 = swipe1.getSwipeDateTime();
        Calendar swipeDateTime2 = swipe2.getSwipeDateTime();
        
        return swipeDateTime1.compareTo(swipeDateTime2);
    };
    
    /**
     *
     */
    public Swipe() {
        this.id = ++lastSwipeIdUsed;
        this.cardId = "Unknown";
        this.room = "Unknown";
        this.swipeDateTime = getNow();
    }
    
    /**
     *
     * @param cardId
     * @param room
     */
    public Swipe(String cardId, String room) {
        this.id = ++lastSwipeIdUsed;
        this.cardId = cardId;
        this.room = room;        
        this.swipeDateTime = getNow();
    }    
    
    /**
     *
     * @param swipeId
     * @param cardId
     * @param room
     * @param swipeDateTime
     */
    public Swipe(int swipeId, String cardId, String room, Calendar swipeDateTime) {
        this.id = swipeId;
        this.cardId = cardId;
        this.room = room;
        this.swipeDateTime = swipeDateTime;
        if (swipeId > Swipe.lastSwipeIdUsed)
            Swipe.lastSwipeIdUsed = swipeId;          
    }     
    
    private Calendar getNow() {
        Calendar now = Calendar.getInstance();  
        return now;
    }    

    

    // Methods required: getters, setters, hashCode, equals, compareTo, comparator
    
    /**
     * @return the id
     */
    public int getId() {
        return this.id;
    }
    

    /**
     * @return the cardId
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * @param cardId the cardId to set
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * @return the room
     */
    public String getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * @return the swipeDateTime
     */
    public Calendar getSwipeDateTime() {
        return swipeDateTime;
    }
    
    /**
     *
     * @param calendar
     * @return formatted date as String
     */
        
    public static String formatSwipeDateTime(Calendar calendar) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar now = Calendar.getInstance();  
        return dateFormat.format(calendar.getTime());
    }    

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "\nSwipe Id: " + this.getId() + " - Card Id: " + this.getCardId() +            
                " - Room: " + this.getRoom() + " - Swiped: " + formatSwipeDateTime(this.getSwipeDateTime());
    }
    
    /**
     *
     * @return
     */
    public String toString(char delimiter) {
        return Character.toString(this.EOLN)+Integer.toString(this.getId()) +delimiter + this.QUOTE+this.getCardId() + this.QUOTE+delimiter+ this.QUOTE+this.getRoom() + this.QUOTE+ delimiter + this.QUOTE+formatSwipeDateTime(this.getSwipeDateTime())+ this.QUOTE;
    }

    @Override
    public int hashCode(){
        return this.getId() * 78 + this.getRoom().hashCode() * 78 + this.getCardId().hashCode() * 78 + this.getSwipeDateTime().hashCode() * 78;
    }
    
    @Override
    public boolean equals(Object o){
        if (o instanceof Swipe) {
            Swipe swipe = (Swipe)o;
            return  swipe.getId() == this.getId() &&
                    swipe.getCardId().equals(this.getCardId()) &&
                    swipe.getRoom().equals(this.getRoom()) && 
                    swipe.getSwipeDateTime().equals(this.getSwipeDateTime());
        } else {
            return false;
        }
    }
    
    
        
    @Override
    public int compareTo(Swipe compareSwipe){
        Calendar swipeDateTime = ((Swipe) compareSwipe).getSwipeDateTime();
        return this.getSwipeDateTime().compareTo(swipeDateTime) * -1;
    }
    
}
