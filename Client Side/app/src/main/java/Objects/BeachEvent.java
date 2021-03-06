package Objects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeachEvent {

    private int id;
    private Date date;
    private int morningCounter, afternoonCounter, eveningCounter;
    private boolean morning, afternoon, evening;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDay() {
        return new SimpleDateFormat("EEEE").format(date);
    }

    public int getMorningCounter() {
        return morningCounter;
    }

    public void setMorningCounter(int morningCounter) {
        this.morningCounter = morningCounter;
    }

    public int getAfternoonCounter() {
        return afternoonCounter;
    }

    public void setAfternoonCounter(int afternoonCounter) {
        this.afternoonCounter = afternoonCounter;
    }

    public int getEveningCounter() {
        return eveningCounter;
    }

    public void setEveningCounter(int eveningCounter) {
        this.eveningCounter = eveningCounter;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isAfternoon() {
        return afternoon;
    }

    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    public boolean isEvening() {
        return evening;
    }

    public void setEvening(boolean evening) {
        this.evening = evening;
    }
}
