package data.listeners.timeline.models;

import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.timelineevents.templates.FirstIndustryEvent;
import data.scripts.timelineevents.templates.FirstSizeReach;

public class FirstIndustryListener extends BaseOneTimeListener {
    String industryID;
    int romanNumeral;
    FirstIndustryEvent event;
    public FirstIndustryListener(String memoryFlagToCheck, String industryID,int numberForTitle) {
        super(memoryFlagToCheck+"_"+industryID);
        this.industryID = industryID;
        this.romanNumeral = numberForTitle;
    }
    public FirstIndustryListener(String memoryFlagToCheck, String industryID) {
        super(memoryFlagToCheck+"_"+industryID);
        this.industryID = industryID;
        this.romanNumeral = 0;
    }
    public FirstIndustryListener(String memoryFlagToCheck,FirstIndustryEvent event) {
        super(memoryFlagToCheck+"_"+event.getIndustryId());
        this.industryID = event.getIndustryId();
        this.event = event;
        this.romanNumeral = 0;
    }
    @Override
    public void advanceImpl(float amount) {
        FactionManager.getMarketsUnderPlayer().stream()
                .filter(x->x.hasIndustry(industryID))
                .findFirst()
                .ifPresent(x -> {
                    if(this.event==null){
                        event = new FirstIndustryEvent(industryID,x.getPrimaryEntity().getId());
                    }
                    else{
                        event.initEntityMemory(x.getPrimaryEntity().getId());
                    }
                    FactionManager.getInstance().addEventToTimeline(event);
                    finish(event);
                });
    }
}
