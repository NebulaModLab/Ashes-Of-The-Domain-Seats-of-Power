package data.scripts.models;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableStat;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.intel.EventOccuredIntel;
import data.scripts.managers.FactionManager;

import java.util.ArrayList;
import java.util.List;

public class BaseFactionTimelineEvent {
    public int cycle;
    public int day;
    public int month;
    public boolean gotReward = false;

    public void setDate(int cycle, int day, int month) {
        this.cycle = cycle;
        this.day = day;
        this.month = month;
    }

    public String getTitleOfEvent() {
        return "";
    }

    public String getImagePath() {
        return Global.getSettings().getIndustrySpec(Industries.POPULATION).getImageName();
    }

    public void grantReward() {
        if (!gotReward) {
            gotReward = true;
            executeReward();
        }
    }

    public void executeReward() {


    }

    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        tooltip.setTitleOrbitronLarge();
        tooltip.addTitle(getTitleOfEvent());
        tooltip.addPara("Event type : %s", 5f, Misc.getGrayColor(), FactionManager.getColorForEvent(getEventType()).brighter(), FactionManager.getStringType(getEventType()));

    }

    public void createPointSection(TooltipMakerAPI tooltip) {
        if (getEventType() == TimelineEventType.UNIQUE) {
            tooltip.addPara("Gain %s points towards all faction goals!", 5f, FactionManager.getColorForEvent(getEventType()).brighter(), getPointsForGoal() + "");
        } else {
            tooltip.addPara("Gain %s points towards %s", 5f, FactionManager.getColorForEvent(getEventType()).brighter(), getPointsForGoal() + "", FactionManager.getStringType(getEventType()));

        }
    }

    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {

    }

    public ArrayList<MutableStat> getEventsAffected() {
        ArrayList<MutableStat> existing = new ArrayList<>();
        if (getEventType() == TimelineEventType.UNIQUE) {
            existing.addAll(FactionManager.getInstance().getGoalStats().values().stream().toList());
        }
        else{
            existing.add(FactionManager.getInstance().getGoalStat(getEventType()));
        }

        return existing;
    }
    public void applyEffects(ArrayList<MutableStat> goalStats){
        goalStats.forEach(x->x.modifyFlat(getID(),getPointsForGoal()));
    }
    public String getID(){
        return "";
    }

    public TimelineEventType getEventType() {
        return TimelineEventType.RESEARCH_AND_EXPLORATION;
    }

    public void createIntelEntryForUnlocking() {
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

    public void updateDataUponEntryOfUI() {
        //This func is called for example when you change market name or anything that requires name
    }

    public int getPointsForGoal() {
        return 10;
    }

}
