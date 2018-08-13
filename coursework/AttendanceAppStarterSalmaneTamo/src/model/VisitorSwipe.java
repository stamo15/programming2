package model;

import java.util.Calendar;

/**
 *
 * @author mga
 */
public class VisitorSwipe extends Swipe {
    
    private String visitorName;

    private String visitorCompany;
    
    static final char EOLN='\n';       
    static final String QUOTE="\"";
    

    /**
     *
     */
    public VisitorSwipe() {
        super();
        this.visitorName = "Unknown";
        this.visitorCompany = "Unknown";        
    }

    /**
     *
     * @param cardId
     * @param room
     * @param visitorName
     * @param visitorCompany
     */
    public VisitorSwipe(String cardId, String room, String visitorName, String visitorCompany) {
        super(cardId, room);
        this.visitorName = visitorName;
        this.visitorCompany = visitorCompany;
    }

    /**
     *
     * @param swipeId
     * @param cardId
     * @param room
     * @param swipeDateTime
     * @param visitorName
     * @param visitorCompany
     */
    public VisitorSwipe(int swipeId, String cardId, String room, Calendar swipeDateTime, String visitorName, String visitorCompany) {
        super(swipeId, cardId, room, swipeDateTime);
        this.visitorName = visitorName;
        this.visitorCompany = visitorCompany;
    }
    
    // Methods required: getters, setters  
    /**
     * @return the visitorName
     */
    public String getVisitorName() {
        return visitorName;
    }

    /**
     * @param visitorName the visitorName to set
     */
    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    /**
     * @return the visitorCompany
     */
    public String getVisitorCompany() {
        return visitorCompany;
    }

    /**
     * @param visitorCompany the visitorCompany to set
     */
    public void setVisitorCompany(String visitorCompany) {
        this.visitorCompany = visitorCompany;
    }
    
    
    @Override
    public String toString() {
        return super.toString() + " - Name: " + this.getVisitorName() +  " - Company: " + this.getVisitorCompany();
    }
    
    @Override
    public String toString(char delimiter) {
        return super.toString(delimiter) +delimiter+ this.QUOTE+this.getVisitorName()+ this.QUOTE+ delimiter + this.QUOTE+this.getVisitorCompany()+ this.QUOTE;
    }
}
