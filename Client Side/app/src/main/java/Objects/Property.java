package Objects;

/**
 * Represents a property of a user
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */


public class Property {

    private String name;
    private String value;

    /**
     * Creates a property with the specified name and value
     * @param name The name of the property.
     * @param value The value of the property.
     */
    public Property(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**
     * Gets the name of the property.
     * @return A String representing the name of the property.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the property.
     * @param name A String contains the name of the property.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of the property.
     * @return A string representing the value of the property.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the property.
     * @param value A String contains the value of the property.
     */
    public void setValue(String value) {
        this.value = value;
    }

}
