package data.scripts.models;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Industries;

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
    public String getImagePath(){
        return Global.getSettings().getIndustrySpec(Industries.POPULATION).getImageName();
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
