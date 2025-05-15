package data.scripts.models;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.intel.EventOccuredIntel;

public class BaseFactionTimelineEvent {
    public int cycle;
    public int day;
    public int month;
    public boolean gotReward= false;
    public void setDate(int cycle, int day, int month) {
        this.cycle = cycle;
        this.day = day;
        this.month = month;
    }
    public String getTitleOfEvent(){
        return "";
    }
    public String getImagePath(){
        return Global.getSettings().getIndustrySpec(Industries.POPULATION).getImageName();
    }
    public void grantReward(){
        if(!gotReward){
            gotReward = true;
            executeReward();
        }
    }
    public void executeReward(){


    }
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip){
        tooltip.setTitleOrbitronLarge();
        tooltip.addTitle(getTitleOfEvent());
    }
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip){

    }
    public TimelineEventType getEventType(){
        return TimelineEventType.RESEARCH_AND_EXPLORATION;
    }
    public void createIntelEntryForUnlocking(){
        EventOccuredIntel intel = new EventOccuredIntel(this);
        Global.getSector().getIntelManager().addIntel(intel);
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
    public void updateDataUponEntryOfUI(){
        //This func is called for example when you change market name or anything that requires name
    }

}
