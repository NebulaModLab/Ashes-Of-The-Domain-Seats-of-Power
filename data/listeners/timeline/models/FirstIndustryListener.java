package data.listeners.timeline.models;

import data.scripts.managers.AoTDFactionManager;
import data.scripts.timelineevents.templates.FirstIndustryEvent;

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
        AoTDFactionManager.getMarketsUnderPlayer().stream()
                .filter(x->x.hasIndustry(industryID))
                .findFirst()
                .ifPresent(x -> {
                    if(this.event==null){
                        event = new FirstIndustryEvent(industryID,x.getPrimaryEntity().getId());
                    }
                    else{
                        event.initEntityMemory(x.getPrimaryEntity().getId());
                    }
                    AoTDFactionManager.getInstance().addEventToTimeline(event);
                    finish(event);
                });
    }
}
