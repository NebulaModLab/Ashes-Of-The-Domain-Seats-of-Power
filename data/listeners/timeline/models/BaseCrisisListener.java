package data.listeners.timeline.models;

import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.intel.events.HegemonyHostileActivityFactor;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
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
            FactionManager.getInstance().addEventToTimeline(eventTemplate);
            finish(eventTemplate);
        }
    }
}
