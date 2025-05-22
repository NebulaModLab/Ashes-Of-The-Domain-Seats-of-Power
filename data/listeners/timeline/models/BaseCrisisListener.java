package data.listeners.timeline.models;

import data.scripts.managers.AoTDFactionManager;
import data.scripts.timelineevents.templates.DefeatMajorFactionCrisisEvent;

public class BaseCrisisListener extends BaseOneTimeListener{
    String factionID;
    public DefeatMajorFactionCrisisEvent eventTemplate;
    public BaseCrisisListener(String memoryFlagToCheck,DefeatMajorFactionCrisisEvent eventTemplate) {
        super(memoryFlagToCheck+"_"+eventTemplate.getEntityId());
        this.eventTemplate = eventTemplate;
    }
    @Override
    public void advanceImpl(float amount) {
        if(eventTemplate.checkForCondition()){
            AoTDFactionManager.getInstance().addEventToTimeline(eventTemplate);
            finish(eventTemplate);
        }
    }
}
