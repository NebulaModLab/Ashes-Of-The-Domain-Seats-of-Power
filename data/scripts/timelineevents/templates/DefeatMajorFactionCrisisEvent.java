package data.scripts.timelineevents.templates;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

public class DefeatMajorFactionCrisisEvent extends BaseFactionTimelineEvent {

    public DefeatMajorFactionCrisisEvent(String factionId){
        this.entityId = factionId;
    }
    @Override
    public String getID() {
        return super.getID()+entityId;
    }

    @Override
    public int getPointsForGoal() {
        return 50;
    }

    @Override
    public String getImagePath() {
        return Global.getSector().getFaction(entityId).getLogo();
    }

    @Override
    public void updateDataUponEntryOfUI() {
        lastSavedName = Global.getSector().getFaction(entityId).getDisplayNameLong();
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.MILITARY;
    }
    public boolean checkForCondition(){
        return false;
    }

}
