package data.scripts.models;

public class BaseFactionTimelineEvent {
    public int cycle;
    public int day;
    public int month;
    public String idOfEvent;
    public BaseFactionTimelineEvent(String idOfEvent, int cycle, int day, int month) {
        this.idOfEvent = idOfEvent;
        this.cycle = cycle;
        this.day = day;
        this.month = month;
    }

    public int getCycle() {
        return cycle;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

}
