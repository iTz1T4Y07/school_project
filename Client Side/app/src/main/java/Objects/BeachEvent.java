package Objects;

/**
 * Represents an event of beach - A day in a beach
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeachEvent {

    private int id;
    private Date date;
    private int morningCounter, afternoonCounter, eveningCounter;
    private boolean morning, afternoon, evening;

    /**
     * Creates an event with the specified id, date, count of people going in the morning,
     * afternoon, evening and is the current user participates in the morning, afternoon and evening.
     * @param id The id of the beachEvent.
     * @param date The date of the beachEvent.
     * @param morningCounter The count of people going in the morning.
     * @param afternoonCounter The count of people going in the afternoon.
     * @param eveningCounter The count of people going in the evening.
     * @param morning Is the user participate in the morning.
     * @param afternoon Is the user participate in the afternoon.
     * @param evening Is the user participate in the evening.
     */
    public BeachEvent(int id, Date date, int morningCounter,
                      int afternoonCounter, int eveningCounter, boolean morning, boolean afternoon, boolean evening){
        this.id = id;
        this.date = date;
        this.morningCounter = morningCounter;
        this.afternoonCounter = afternoonCounter;
        this.eveningCounter = eveningCounter;
        this.morning = morning;
        this.afternoon = afternoon;
        this.evening = evening;
    }

    /**
     * Gets the ID of the event
     * @return An int representing the ID of the event.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the event
     * @param id An int representing the event id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the date of the event
     * @return A date represent the date of the event.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the event
     * @param date A date representing the date of the event.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the name of the day in the week which the event occurs on.
     * @return A string represents the name of the day in the week.
     */
    public String getDay() {
        return new SimpleDateFormat("EEEE").format(date);
    }

    /**
     * Gets the count of people participate in the morning.
     * @return An int representing the number of people participate in the morning.
     */
    public int getMorningCounter() {
        return morningCounter;
    }

    /**
     * Sets the count of people participate in the morning.
     * @param morningCounter An int representing the number of people participate in the morning.
     */
    public void setMorningCounter(int morningCounter) {
        this.morningCounter = morningCounter;
    }

    /**
     * Gets the count of people participate in the afternoon.
     * @return An int representing the number of people participate in the afternoon.
     */
    public int getAfternoonCounter() {
        return afternoonCounter;
    }

    /**
     * Sets the count of people participate in the afternoon.
     * @param afternoonCounter An int representing the number of people participate in the afternoon.
     */
    public void setAfternoonCounter(int afternoonCounter) {
        this.afternoonCounter = afternoonCounter;
    }

    /**
     * Gets the count of people participate in the evening.
     * @return An int representing the number of people participate in the evening.
     */
    public int getEveningCounter() {
        return eveningCounter;
    }

    /**
     * Sets the count of people participate in the evening.
     * @param eveningCounter An int representing the number of people participate in the evening.
     */
    public void setEveningCounter(int eveningCounter) {
        this.eveningCounter = eveningCounter;
    }

    /**
     * Gets if the user is participating in the morning.
     * @return A boolean represents if the user is participating in the morning.
     */
    public boolean isMorning() {
        return morning;
    }

    /**
     * Sets if the user is participating in the morning.
     * @param morning A boolean represents if the user is participating in the morning.
     */
    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    /**
     * Gets if the user is participating in the afternoon.
     * @return A boolean represents if the user is participating in the afternoon.
     */
    public boolean isAfternoon() {
        return afternoon;
    }

    /**
     * Sets if the user is participating in the afternoon.
     * @param afternoon A boolean represents if the user is participating in the afternoon.
     */
    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    /**
     * Gets if the user is participating in the evening.
     * @return A boolean represents if the user is participating in the evening.
     */
    public boolean isEvening() {
        return evening;
    }

    /**
     * Sets if the user is participating in the evening.
     * @param evening A boolean represents if the user is participating in the evening.
     */
    public void setEvening(boolean evening) {
        this.evening = evening;
    }
}
