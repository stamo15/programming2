package repositories;

import model.Swipe;
import java.util.ArrayList;

public interface RepositoryInterface {

    /**
     *
     * @param item
     */
    public void add(Swipe item);

    /**
     *
     * @param id
     * @return
     */
    public Swipe getItem(int id);

    public ArrayList<Swipe> getItems();

    /**
     *
     * @param id
     */
    
    public void remove(int id);

    public void setItems(ArrayList<Swipe> items);

    /**
     *
     * @param filename
     */
    
    public void store(String filename);

    /**
     *
     * @return
     */
    @Override
    public String toString();
    
    
}
