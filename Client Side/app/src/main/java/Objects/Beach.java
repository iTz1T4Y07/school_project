package Objects;

/**
 * Represents a beach
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */

public class Beach {

    private String name;
    private int id;

    /**
     * Creates a beach with the specified name and id
     * @param name The name of the beach.
     * @param id The id of the beach.
     */
    public Beach (String name, int id){
        this.name = name;
        this.id = id;
    }

    /**
     * Sets the beach name
     * @param newName A String contains the beach name.
     */
    public void setName(String newName){
        this.name = newName;
    }
    /**
     * Sets the beach ID
     * @param newID An int representing the beach id.
     */
    public void setId(int newID){
        this.id = newID;
    }

    /**
     * Gets the name of the beach.
     * @return A string representing the name of the beach.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the ID of the beach
     * @return An int representing the ID of the beach.
     */
    public int getId(){
        return this.id;
    }
}
